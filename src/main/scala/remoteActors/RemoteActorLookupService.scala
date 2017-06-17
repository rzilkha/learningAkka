package remoteActors

import akka.actor.{ActorSystem, Props}
import com.typesafe.config.ConfigFactory
import router_pckg.Worker

/**
  * Created by Roee Zilkha on 6/17/2017.
  */
object RemoteActorLookupService extends App{
  val config = ConfigFactory.load.getConfig("RemoteActorLookupService")
  val system = ActorSystem("RemoteActorLookupService",config)
 val worker = system.actorSelection("akka.tcp://RemoteActorService@127.0.0.1:2552/user/remoteWorker")
  worker ! "sending remote msg"

  val worker2 = system.actorSelection("akka.tcp://RemoteActorService@127.0.0.1:2552/user/remoteWorkerSecond")
  worker2 ! "sending remote msg to created call"
}
