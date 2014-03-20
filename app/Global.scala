import play.api.GlobalSettings
import play.api.mvc.{Filters, EssentialAction}
import scala.concurrent.duration._

class Global extends GlobalSettings {
  override def doFilter(next: EssentialAction): EssentialAction =
    Filters(super.doFilter(next), new ThrottleFilter(10, 10.seconds))
}
