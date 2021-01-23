package com.joel.akka.http
import kamon.Kamon

import scala.collection.mutable
import scala.concurrent.{ExecutionContextExecutor, Future}

class VehicleOperationsImpl(implicit executionContext: ExecutionContextExecutor) extends VehicleOperations {

  // a scala mutable map has unique keys
  val vehicleMap : mutable.Map[String, Vehicle] = scala.collection.mutable.Map[String, Vehicle]()

  override def insertVehicle(vehicle: Vehicle): Future[Vehicle] = Future {
    Kamon.counter("app.vehicle.insert").withoutTags().increment()
    val timer = Kamon.timer("app.vehicle.insert.latency").withoutTags().start()
    vehicleMap += (vehicle.vin -> vehicle)
    val map = vehicleMap(vehicle.vin)
    Thread.sleep((Math.random * 1000).toLong)
    timer.stop()
    map
  }

  override def updateVehicle(vehicle: Vehicle): Future[Vehicle] = Future {
    Kamon.counter("app.vehicle.update").withoutTags().increment()
    val timer = Kamon.timer("app.vehicle.update.latency").withoutTags().start()
    vehicleMap.update(vehicle.vin, vehicle)
    val map = vehicleMap(vehicle.vin)
    timer.stop()
    map
  }

  override def getVehicle(vin: String): Future[Vehicle] = Future {
    Kamon.counter("app.vehicle.get").withoutTags().increment()
    val timer = Kamon.timer("app.vehicle.update.latency").withoutTags().start()
    val map = vehicleMap(vin)
    timer.stop()
    map
  }

}
