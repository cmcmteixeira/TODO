package client.action

import client.packet.Packet
import client.printer.{ListView, TaskListView}
import client.task.Task
import lib.dataManager.DataManager
import lib.logger.Logger
import scaldi.{Injectable, Injector}

protected abstract class Actions(implicit inj: Injector) extends Injectable{
  val dataManager = inject[DataManager[Task]]
  val logger = inject[Logger]
  val view = inject[ListView[Seq[Task],Task]]
}

class DeleteAction(packet: Packet)(implicit inj: Injector) extends Actions with Action {
  override def perform(): Unit = {
    logger.info(this.getClass.getName,"Delete",s"Removing task with index ${packet.index}")
    dataManager deleteResource new Task(packet.index)
    logger.info(this.getClass.getName,"Delete",s"Removed task with index ${packet.index}")
  }
}

class ReadAction(packet: Packet)(implicit inj: Injector) extends Actions with Action {
  override def perform(): Unit = {
    logger.info(this.getClass.getName,"List",s"Listing tasks ${packet.index}")
    val tasks : Seq[Task] = {
      dataManager readResource (_ => true)
    }
    view.update(tasks)
    logger.info(this.getClass.getName,"List",s"Listing ${tasks.length} tasks")
  }
}

class UpdateAction(packet: Packet)(implicit inj: Injector) extends Actions with Action{
  override def perform(): Unit = {
    logger.info(this.getClass.getName,"Update",s"Updating task with index ${packet.index}")
    dataManager updateResource (
      new Task(packet.index),
      new Task(packet.index,packet.description,packet.project)
    )
    logger.info(this.getClass.getName,"Update",s"Updating task with index ${packet.index}")
  }
}

class CreateAction(packet: Packet)(implicit inj: Injector) extends Actions with Action{
  override def perform(): Unit = {
    logger.info(this.getClass.getName,"Creating",s"Creating task with index ${packet.index}")
    val newId = dataManager
      .readResource(_ => true)
      .foldLeft(0)((currMax: Int,task:Task) => {
        currMax max task.id.get
      }) + 1
    val resource = dataManager.createResource(new Task(
      Some(newId),
      packet.description,
      packet.project
    ))
    logger.info(this.getClass.getName,"Created",s"Created task with index ${resource.get.id}")
  }
}
