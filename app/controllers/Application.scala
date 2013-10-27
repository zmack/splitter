package controllers

import play.api._
import play.api.mvc._

object Application extends Controller {

  def index = Action {
    Ok(views.html.index("Your new_party application is ready."))
  }

  def login = Action {
    Ok(views.html.login())
  }
}