package router_pckg

import akka.actor.{ActorSystem, Props}
import akka.routing.RoundRobinGroup

/**
  * Created by Roee Zilkha on 6/11/2017.
  */


object nativeRoutesExample extends App {

  val system = ActorSystem("different_route")
  val worker1= system.actorOf(Props[Worker],"w1")
  val worker2= system.actorOf(Props[Worker],"w2")
  val worker3= system.actorOf(Props[Worker],"w3")

  val workerList = List("/user/w1","/user/w2","/user/w3")

  val routeGroup = system.actorOf(RoundRobinGroup(workerList).props(),"round_robin_group")

  routeGroup ! "hello mate"
  routeGroup ! "another write"
  routeGroup ! "third"
  routeGroup ! "fourth"

}
