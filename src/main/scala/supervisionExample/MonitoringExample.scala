package supervisionExample

import akka.actor.{Actor, ActorRef, ActorSystem, Props, Terminated}

class Ares(actorWatch:ActorRef) extends Actor{
  override def preStart(): Unit = {
    super.preStart()
  }

  override def receive = {
    case Terminated => println("athena terminated")
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
    val system = ActorSystem("monitoring")
  val athenaActor = system.actorOf(Props[Athena],"athena")
  val aresActor= system.actorOf(Props(new Ares(athenaActor)),"ares")

  athenaActor ! "yo man"
}
