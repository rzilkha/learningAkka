package changeBehavior

import akka.actor.{ActorSystem, FSM, Props, Stash}


sealed trait Data
case object EmptyData extends Data
sealed trait UserState

case object Connect extends UserState
case object Disconnect extends UserState

case class Operation(msg:String) extends UserState;

class FsmActor extends FSM[UserState,Data] with Stash{
  startWith(Disconnect,EmptyData)

  when(Disconnect){
    case Event(Disconnect,_)=>
      println("already disconnected")
      stay using EmptyData
    case Event(Connect,_)=>
      unstashAll()
      goto(Connect) using EmptyData
    case Event(_,_) =>
      println("disconnected stashing operation")
      stash()
      stay using EmptyData;
  }

  when(Connect){
    case Event(Connect,_)=>{
      println("already connect")
      stay using EmptyData
    }
    case Event(Disconnect,_)=> {
      println("disconnecting..")
      goto(Connect) using EmptyData
    }
    case Event(Operation(msg),_) =>{
      println(s"starting operation $msg")
      stay using EmptyData;
    }
  }

  initialize()
}
/**
  * Created by Roee Zilkha on 6/12/2017.
  */


object FsmStateExample extends App{

  val system = ActorSystem("states")
  val fsm=system.actorOf(Props[FsmActor],"fsm");
  fsm ! Disconnect;
  fsm ! Operation("yo yo yo whatsup man")
  fsm ! Operation("checking disconnect op")
  fsm ! Connect

}
