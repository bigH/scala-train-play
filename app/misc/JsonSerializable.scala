/*
 * Copyright © 2012 Typesafe, Inc. All rights reserved.
 */

package misc

import scala.util.parsing.json.{ JSONObject => JsonObject }

trait JsonSerializable[A] {

  def fromJson(json: JsonObject)(implicit format: JsonFormat[A]): Option[A] =
    format fromJson json

  implicit class JsonOps[A](a: A) {
    def toJson(implicit format: JsonFormat[A]): JsonObject =
      format toJson a
  }
}

trait JsonFormat[A] {

  def toJson(a: A): JsonObject

  def fromJson(json: JsonObject): Option[A]
}
