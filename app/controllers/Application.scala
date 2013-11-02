package controllers

import play.api._
import play.api.mvc._
import play.api.data.Form
import play.api.data.Forms._

object Application extends Controller {
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

  def index = Action {
    Ok(views.html.index("Your new_party application is ready."))
  }

  def login = Action { implicit request =>
    Ok(views.html.login(loginForm))
  }

  def logout = Action {
    Redirect(routes.Application.login).withNewSession.flashing(
      "success" -> "You've been logged out"
    )
  }

  def authenticate = Action { implicit request =>
    loginForm.bindFromRequest.fold(
      formWithErrors => BadRequest(views.html.login(formWithErrors)),
      user => Redirect(routes.Application.index).withSession(Security.username -> user._1)
    )
  }
}

trait Secured {
  private def username(request: RequestHeader) = models.User.findByEmail(request.session.get("email"))

  private def onUnauthorized(request: RequestHeader) = Results.Redirect(routes.Application.login)

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