package persistence

import akka.actor.{ActorSystem, Props}
import akka.persistence.{PersistentActor, SnapshotOffer}
import akka.persistence.serialization.Snapshot
import persistence.Counter.{Cmd, Decrement, Evnt, Increment}

case class State(var count:Int);

object Counter{
  sealed trait Operation;
  case object Increment extends Operation;
  case object Decrement extends Operation;

  case class Cmd(op:Operation)
  case class Evnt(op:Operation)
}
class countActor extends PersistentActor{

  var state = State(0)

  override def receiveRecover = {
    case evnt:Evnt =>
      println("recover state")
      updateState(evnt)
    case SnapshotOffer(_,snapshot:State)=>
      println("loading snapshot")
      state = snapshot;


  }

  override def receiveCommand = {
    case cmd @ Cmd(op) =>
      println(s"got cmp $cmd")
      persist(Evnt(op)){
        evnt =>
          updateState(evnt)
      }

  }


  def updateState(evnt:Evnt): Unit ={
    evnt match {
      case Evnt(Increment) =>
        println("increment")
        state.count = state.count + 1
      case Evnt(Decrement) =>
        state.count = state.count - 1;
    }
    println(s"count is $state")
    snapshotIt
  }

  def snapshotIt = {
    if (state.count%3==0){
      saveSnapshot(state)
    }

  }
  override def persistenceId: String = "persist-example"
}
/**
  * Created by Roee Zilkha on 6/16/2017.
  */
object PersistenceActorExample extends App{
  val system = ActorSystem("system")
  val persist = system.actorOf(Props[countActor])
  println("dasda")
  persist ! Cmd(Increment)
}
