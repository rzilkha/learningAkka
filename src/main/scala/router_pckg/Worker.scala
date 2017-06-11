package router_pckg

import akka.actor.Actor
import akka.actor.Actor.Receive

/**
  * Created by Roee Zilkha on 6/11/2017.
  */

object Worker {
  var serialId=0;
}
class Worker extends Actor {
  val id = Worker.serialId
  Worker.serialId=Worker.serialId+1
  override def receive: Receive = {
    case msg =>
      println(s"this msg $msg , i am worker $id")
  }
}
