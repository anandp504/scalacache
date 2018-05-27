package com.anand.database

import scalikejdbc._

object Database {

  Class.forName("org.h2.Driver")
  ConnectionPool.singleton("jdbc:h2:mem:content", "username", "password")

  implicit val session = AutoSession

  def createConentTable(): Unit = {
    sql"""
      create table content_definition (
      id serial not null primary key,
      content_id int,
      channel_id varchar(64),
      is_active boolean
    )
    """.execute.apply()

    val dataList = List(ContentDefinition(1000, "channel1", isActive = true),
      ContentDefinition(1001, "channel1", isActive = true),
      ContentDefinition(1002, "channel2", isActive = false))

    dataList foreach { content =>
      sql"insert into content_definition (content_id, channel_id, is_active) values (${content.contentId}, ${content.channelId}, ${content.isActive})".update.apply()
    }
  }

  def getContentDefinition(): List[ContentDefinition] = {
    println("Retrieving elements from the database...")
    sql"select * from content_definition".map(rs => ContentDefinition(rs)).list.apply()
  }

}
