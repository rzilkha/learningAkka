package changeBehavior

import akka.actor.{Actor, ActorSystem, Props}
import changeBehavior.Werewolf.{Hello, Turn}


object Werewolf {

  case object Turn

  case class Hello(msg: String)

}

class Werewolf extends Actor {

  def Human: Receive = {
    case Hello(msg) => {
      println(s"hello $msg")
    }
    case Turn => {
      context.become(Wolf)
    }
  }

  def Wolf: Receive = {
    case Hello(msg) => {
      println(s"grrr!! $msg")
    }
    case Turn => {
      context.become(Human)
    }
  }

  override def receive = Human
}

/**
  * Created by Roee Zilkha on 6/11/2017.
  */
object behaviorChangeExample extends App {

  val system = ActorSystem("behavior")
  val werewolf = system.actorOf(Props[Werewolf], "wolf")
  werewolf ! Hello("hi")
  werewolf ! Turn
  werewolf ! Hello("hello man")
}
