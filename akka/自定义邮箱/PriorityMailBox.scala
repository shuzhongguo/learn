package com.packt.chapter1
import akka.actor.{Actor, ActorSystem, Props}
import com.typesafe.config.Config
import akka.dispatch.{PriorityGenerator, UnboundedPriorityMailbox}

class MyPriorityActor extends Actor {
  def receive: PartialFunction[Any, Unit] = {
    // Int Messages
    case x: Int => println(x)
    //String Messages
    case x: String => println(x)
    // Long Messages
    case x: Long => println(x)
    //other Messages
    case x => println(x)
  }
}

class MyPriorityActorMailbox(setting: ActorSystem.Settings,config: Config)
  extends UnboundedPriorityMailbox(
    //Create a new PriorityGenerator, lower prio means more important
    PriorityGenerator{
      //Int Messages
      case x: Int => 1
      //String Messages
      case x: String => 0
      //Long Messages
      case x: Long => 2
      //other Messages
      case _ => 3
    }
  )

object PriorityMailBoxApp extends App{
  val actorSystem = ActorSystem("HelloAkka");
  val myPriorityActor = actorSystem.actorOf(Props[MyPriorityActor].withDispatcher("prio-dispatcher"));
  myPriorityActor ! 6.0;
  myPriorityActor ! 1;
  myPriorityActor ! 5.0;
  myPriorityActor ! 3;
  myPriorityActor ! "Hello";
  myPriorityActor ! 5;
  myPriorityActor ! "I am priority actor";
  myPriorityActor ! "I process string messages first,then integer, long adn others";
}
