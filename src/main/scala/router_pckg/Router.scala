package router_pckg

import akka.actor.{Actor, Props}

import scala.util.Random

/**
  * Created by Roee Zilkha on 6/11/2017.
  */
class Router extends Actor {

    val routees = List.fill(5)(
      context.actorOf(Props[Worker])
    )

  override def receive = {
    case msg=>
      routees(Random.nextInt(routees.length)) forward msg
  }

}
