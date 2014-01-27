package controllers

import play.api._
import play.api.mvc._
import play.api.data.Form
import play.api.data.Forms._
import securesocial.core.Identity

object Application extends Controller with securesocial.core.SecureSocial {
  val loginForm = Form(
    tuple(
      "email" -> text,
      "password" -> text
    ) verifying("Invalid email or password", result => result match{
      case(email, password) => check(email, password)
    })
  )

  def check(username: String, password: String) = {
    (username == "admin") && (password == "password")
  }

  def index = SecuredAction { implicit request =>
    Ok(views.html.index(request.user.toString))
  }
}

trait Secured {
  private def username(request: RequestHeader) = models.User.findByEmail(request.session.get(Security.username))

  private def onUnauthorized(request: RequestHeader) = Results.Redirect(routes.Application.index)

  def isAuthenticated(f: => models.User => Request[AnyContent] => Result) = Security.Authenticated(username, onUnauthorized) { user =>
    Action(request => f(user)(request))
  }

  def isMemberOf(party: models.Party)(f: => models.User => Request[AnyContent] => Result) = isAuthenticated { user => request =>
    if (party.isOwnedBy(user)) {
      f(user)(request)
    } else {
      Results.Forbidden
    }
  }
}