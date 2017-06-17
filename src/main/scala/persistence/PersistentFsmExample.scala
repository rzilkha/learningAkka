package persistence

import akka.actor.{ActorSystem, Props}
import akka.persistence.fsm.PersistentFSM
import akka.persistence.fsm.PersistentFSM.FSMState
import scala.reflect._
import akka.persistence._
import akka.persistence.fsm._
import akka.persistence.fsm.PersistentFSM.FSMState
import scala.reflect.ClassTag


/**
  * Created by Roee Zilkha on 6/17/2017.
  */

sealed trait Data{
  var amount:Float
}

sealed trait TransactionType;

case object Increment extends TransactionType
case object Decrement extends TransactionType

sealed trait EventDomain

case class AcceptedTransaction(amount:Float,transactionType: TransactionType) extends EventDomain

case class RejectedTransaction(reason:String) extends EventDomain



sealed trait AccountState extends FSMState

case object Empty extends AccountState {
  override def identifier: String = "EMPTY"
}
case object Active extends AccountState {
  override def identifier: String = "ACTIVE"
};


case class Balance(  override var amount: Float) extends Data;
case object ZeroBalance extends Data {
  override var amount: Float = 0
};

case class Operation(amount:Float,transactionType: TransactionType);


class AccountMgr extends PersistentFSM[AccountState,Data,EventDomain]{

  override def domainEventClassTag: ClassTag[EventDomain] = classTag[EventDomain]

  override def applyEvent(domainEvent: EventDomain, currentData: Data)={
    domainEvent match {
      case AcceptedTransaction(amount,Increment) =>
        println(s"current balance ${currentData.amount}")
        Balance(amount + currentData.amount)
      case AcceptedTransaction(amount,Decrement) =>
        Balance(amount + currentData.amount)
      case RejectedTransaction(reason) =>
        println(s"rejected because $reason")
        currentData
    }
  }

  override def persistenceId: String = "fsmTest"

  startWith(Empty,ZeroBalance)

  when(Empty){
    case Event(Operation(amount,Increment),_) =>
      goto(Active) applying AcceptedTransaction(amount,Increment)
    case Event(Operation(_,Decrement),_) =>
      stay applying RejectedTransaction("you don't have any cash")

  }

  when(Active){
    case Event(Operation(amount,Increment),_) =>
      stay applying AcceptedTransaction(amount,Increment)
    case Event(Operation(amount,Decrement),balance) =>
      var newBalance = balance.amount - amount;
      if(newBalance < 0){
        goto(Empty) applying RejectedTransaction("you don't have any cash, moving to empty")
      }else{
        stay applying AcceptedTransaction(amount,Decrement)
      }
  }


}
object PersistentFsmExample extends App {
  val system = ActorSystem("fsmPersist")
  val persistentFsm = system.actorOf(Props[AccountMgr])
  persistentFsm ! Operation(5,Increment)
}
