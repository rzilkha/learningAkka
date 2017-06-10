package communicate

import scala.language.postfixOps
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.duration._
import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import communicate.MusicController.{Play, Stop}
import communicate.MusicPlayer.{MusicStarted, StartMusic, StopMusic}

/**
  * Created by Roee Zilkha on 6/5/2017.
  */

object MusicController {
  sealed trait ControllerMsg
  case object Play extends ControllerMsg
  case object Stop extends ControllerMsg
}

class MusicController extends Actor{
  def receive() = {
    case Play => {
      println("I am playing music")
      sender() ! MusicStarted
    }
    case Stop => println("I am stopping")
  }
}

object MusicPlayer {
  sealed trait PlayMessage
  case object StopMusic extends PlayMessage
  case object StartMusic extends PlayMessage
  case object MusicStarted extends PlayMessage
}




class MusicPlayer(controllerActor:ActorRef) extends Actor {
  import scala.concurrent.ExecutionContext.Implicits.global
  implicit val timeout = Timeout(5 seconds)



  def receive() = {
    case StopMusic => println("not stopping")
    case StartMusic => {
      val controllerActor=context.actorOf(Props[MusicController],"musicController")
      controllerActor ? Play map{
        case MusicStarted => println("cool it started")
      }
    }
  }
}

class PartialMethodExample() {
  val testFunctionalPart : PartialFunction[Int, Unit] = {
    case 1 => println("this is partial function")
  }
}


object Communicate extends App{
//    val system = ActorSystem("music")
//    val controllerActor = system.actorOf(Props[MusicController],"player")
//    var musicPlayerActor = system.actorOf(Props(new MusicPlayer(controllerActor)),"musicplayer")
//    musicPlayerActor ! StartMusic

      val trysom = new PartialMethodExample();
      trysom.testFunctionalPart(1)

}
