package remoteActors

import akka.actor.{ActorSystem, Props}
import com.typesafe.config.ConfigFactory
import router_pckg.Worker

/**
  * Created by Roee Zilkha on 6/17/2017.
  */
object RemoteActorService extends App {
  val config = ConfigFactory.load.getConfig("RemoteActorService")
  println(config)
  val system = ActorSystem("RemoteActorService",config)
  val actor = system.actorOf(Props[Worker],"remoteWorker")
  println(actor)
  actor ! "check msg"

}
