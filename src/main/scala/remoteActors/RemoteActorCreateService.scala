package remoteActors

import akka.actor.{ActorSystem, Props}
import com.typesafe.config.ConfigFactory
import router_pckg.Worker

/**
  * Created by Roee Zilkha on 6/17/2017.
  */
object RemoteActorCreateService extends App {
  val config = ConfigFactory.load.getConfig("RemoteActorCreateService")
  val system = ActorSystem("RemoteActorCreateService",config)
  println(config)

  // creates the worker not in local port of the actor
  val actor = system.actorOf(Props[Worker],"remoteWorkerSecond")
  actor ! "testing remote create"
  println(s"actor is at ${actor.path}")
}
