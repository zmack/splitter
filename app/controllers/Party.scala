package controllers

import play.api.mvc._

object Party extends Controller {

  def index = Action {
    val parties = models.Party.findAll(10)
    Ok(views.html.party.index(parties))
  }

  def show(id: Long) = Action {
    val party = models.Party.findById(id)
    Ok(views.html.party.show(party))
  }

}
