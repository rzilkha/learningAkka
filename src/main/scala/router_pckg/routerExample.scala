package router_pckg

import akka.actor.{ActorSystem, Props}

/**
  * Created by Roee Zilkha on 6/11/2017.
  */
object routerExample extends App{
  val system = ActorSystem("routers")
  val router = system.actorOf(Props[Router])
  router ! "hello"
  router ! "hello"
  router ! "hello"
  router ! "hello"
  router ! "hello"
}
