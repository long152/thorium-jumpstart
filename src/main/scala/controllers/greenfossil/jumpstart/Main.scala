package controllers.greenfossil.jumpstart

import com.greenfossil.thorium.{Action, Server}
import controllers.greenfossil.jumpstart.day1.JumpStartDay1Controller

import scala.concurrent.ExecutionContext

@main def start: Unit =
  implicit val ec: ExecutionContext = ExecutionContext.global
  Server()
    .addHttpService("/", Action(request => "Welcome to Thorium!"))
    .addServices(JumpStartDay1Controller)
    .addDocService("/docs")
    .start()
