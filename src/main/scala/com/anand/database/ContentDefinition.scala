package com.anand.database

import scalikejdbc.WrappedResultSet
import scalikejdbc._

case class ContentDefinition(contentId: Int, channelId: String, isActive: Boolean)

object ContentDefinition extends SQLSyntaxSupport[ContentDefinition] {
  override val tableName = "content_definition"
  def apply(rs: WrappedResultSet) = new ContentDefinition (
    rs.int("content_id"), rs.string("channel_id"), rs.boolean("is_active"))
}

case class ContentDefinitionCompositeKey(contentId: Int, channelId: String)
