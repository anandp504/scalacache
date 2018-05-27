package com.anand.cache

import com.anand.database.{ContentDefinitionCompositeKey, Database}
import com.github.blemale.scaffeine.{LoadingCache, Scaffeine}

import scala.concurrent.duration._

object DatabaseCache {

  /**
    * Function which loads the initial elements in the cache and then loads each element
    * after the expiry.
    * @return
    */
  def buildCache = {
    val cache: LoadingCache[ContentDefinitionCompositeKey, Boolean] =
      Scaffeine()
        .recordStats()
        .expireAfterAccess(1.hour)
        .build((key: ContentDefinitionCompositeKey) => loadContentDefinition(key))

    cache.putAll(Database.getContentDefinition().map {
      contentDefinition =>
        ContentDefinitionCompositeKey(contentDefinition.contentId, contentDefinition.channelId) -> contentDefinition.isActive
    }.toMap)
    cache
  }

  def loadContentDefinition(key: ContentDefinitionCompositeKey): Boolean = {
    Database.getContentDefinition().filter(c => c.contentId == key.contentId && c.channelId == key.channelId).head.isActive
  }

  private lazy val cache = buildCache

  def retrieveContentDefinition(contentId: Int, channelId: String) = {
    cache.get(ContentDefinitionCompositeKey(contentId, channelId))
  }

}
