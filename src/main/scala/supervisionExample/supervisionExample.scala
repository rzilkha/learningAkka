package supervisionExample

import akka.actor.SupervisorStrategy.{Escalate, Restart, Resume, Stop}
import akka.actor.{Actor, ActorRef, ActorSystem, OneForOneStrategy, Props}

import scala.concurrent.duration._
import Aphrodite._

object Aphrodite{

  case object RestartException extends Exception
  case object StopException extends Exception
  case object ResumeException extends Exception

  def props = Props[Aphrodite]

}

class Aphrodite extends Actor{

  override def postRestart(reason: Throwable): Unit = {
    println("postRestart")
    super.postRestart(reason)
  }

  override def preRestart(reason: Throwable, message: Option[Any]): Unit ={
    println("preRestart")
    super.preRestart(reason, message)
  }

  override def postStop(): Unit = {
    println("postStop")
    super.postStop()
  }

  override def preStart(): Unit = {
    println("preStart")
    super.preStart()
  }


  override def receive()={
    case "Resume"=>
      throw ResumeException
    case "Restart"=>
      throw RestartException
    case "Stop"=>
      throw StopException
    case _ =>
      throw new Exception

  }
}


class Hera() extends Actor{

  var childActor : ActorRef = _

  override def preStart(): Unit = {
    childActor = context.actorOf(Aphrodite.props,"aphrodie");
    super.preStart();
  }
  override val supervisorStrategy = OneForOneStrategy(maxNrOfRetries = 5,withinTimeRange = 5 seconds){
    case ResumeException => Resume
    case StopException =>{
      println("stopping aphrodite")
      Stop
    }
    case RestartException => {
      println("restart aphrodite")
      Restart
    }
    case _ =>{
      println("escalate")
      Escalate
    }
  }

  override val receive = {
    case msg => {
      println(s"received message $msg")
      childActor ! msg;
      Thread.sleep(100);
    }
  }
}


/**
  * Created by Roee Zilkha on 6/10/2017.
  */
object supervisionExample extends App {

  val system = ActorSystem("supervision")
  val aphroditeActor = system.actorOf(Props[Aphrodite],"aphrodie");
  val heraActor = system.actorOf(Props[Hera],"hera")
  heraActor ! "Stop"
}
