import java.lang.String

import akka.actor.{Actor, ActorSystem, Props}
import akka.actor.Actor.Receive

/**
  * Created by Roee Zilkha on 6/2/2017.
  */

case class WhoToGreet(who: String){

}

class Greeter extends Actor{
  def receive = {
    case WhoToGreet(who) => println(s"hello $who");
  }
}
object HelloWorld extends App{
  val system = ActorSystem("greetActorSystem")
  val greeter = system.actorOf(Props[Greeter],"greet");
  greeter ! WhoToGreet("dude")
}
