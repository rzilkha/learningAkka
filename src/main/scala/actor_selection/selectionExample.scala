package actor_selection

import akka.actor.{Actor, ActorIdentity, ActorSelection, ActorSystem, Identify, Props, Terminated}


/**
  * Created by Roee Zilkha on 6/10/2017.
  */


class Counter extends Actor{
  override def receive = {
    case msg => {
      println("stopping")
      context.stop(self)
    }
  }
}
class Watcher(actionSelect: ActorSelection) extends Actor{
  actionSelect ! Identify(None)

  override def receive = {
    case ActorIdentity(_,Some(ref))=>{
      context.watch(ref)
    }
    case ActorIdentity(_,None)=>{
      println("no ref")
    }
    case Terminated(_) => {
      println("the watched stopped")
    }
  }
}
object selectionExample extends App{
  val system = ActorSystem("selection")

  val actorCounter = system.actorOf(Props[Counter],"count")
  val actorSelect = system.actorSelection("user/count");
  val watcher = system.actorOf(Props(classOf[Watcher],actorSelect),"watcher")

  actorSelect ! "dasd"



}
