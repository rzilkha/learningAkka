package supervisionExample

import akka.actor.{Actor, ActorRef, ActorSystem, Props, Terminated}

class Ares(actorWatch:ActorRef) extends Actor{
  override def preStart(): Unit = {
    context.watch(actorWatch)
//    super.preStart()
  }

  override def postStop() = {
    println("Ares postStop...")
  }

  override def receive = {
    case Terminated(_) => {
      println("ares received Stop from athena termination")
      context.stop(self)
    }
  }
}
class Athena extends Actor{

   override def receive()={
    case msg =>{
      println(s"stopping athena $msg")
      context.stop(self)
    }
  }
}
/**
  * Created by Roee Zilkha on 6/10/2017.
  */
object MonitoringExample extends App {
  // Create the 'monitoring' actor system
  val system = ActorSystem("monitoring")

  val athena = system.actorOf(Props[Athena], "athena")

  val ares = system.actorOf(Props(classOf[Ares], athena), "ares")

  athena ! "Hi"

  Thread.sleep(500)
}
