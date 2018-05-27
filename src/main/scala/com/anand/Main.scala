package com.anand

import com.anand.cache.DatabaseCache
import com.anand.database.Database

object Main extends App {
  Database.createConentTable()
  // Cache is loaded before accessing the elements
  for (i <- 1 to 10) {
    println(s"ContentId: 1001, ChannelId: channel1, isActive: ${DatabaseCache.retrieveContentDefinition(1001, "channel1")}")
    println(s"ContentId: 1002, ChannelId: channel2, isActive: ${DatabaseCache.retrieveContentDefinition(1002, "channel2")}")
  }
}