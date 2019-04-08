package controllers

import javax.inject._
import org.opencv.core.Core
import org.opencv.core.CvType
import org.opencv.core.Mat
import play.api._
import play.api.mvc._
import play.libs.NativeLoader

@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  def index() = Action { implicit request: Request[AnyContent] =>
    NativeLoader.load(Core.NATIVE_LIBRARY_NAME)
    val mat = Mat.eye(3, 3, CvType.CV_8UC1)

    Ok(
      s"""|java.library.path=${System.getProperty("java.library.path")}
          |OpenCV output=$mat
      """.stripMargin
    )
  }
}
