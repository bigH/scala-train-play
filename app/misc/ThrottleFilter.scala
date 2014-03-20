package misc

import play.api.mvc.{SimpleResult, RequestHeader, Filter}
import play.api.{Logger, Routes}
import scala.concurrent.Future
import play.api.libs.concurrent.Execution.Implicits._

object ThrottleFilter extends Filter {
  def now = System.currentTimeMillis

  def apply(nextFilter: (RequestHeader) => Future[SimpleResult])
           (requestHeader: RequestHeader): Future[SimpleResult] = {


//    nextFilter(requestHeader).map { result =>
//      val action = requestHeader.tags(Routes.ROUTE_CONTROLLER) +
//        "." + requestHeader.tags(Routes.ROUTE_ACTION_METHOD)
//      val endTime = System.currentTimeMillis
//      val requestTime = endTime - startTime
//      Logger.info(s"${action} took ${requestTime}ms" +
//        s" and returned ${result.header.status}")
//      result.withHeaders("Request-Time" -> requestTime.toString)
//    }
  }
}

