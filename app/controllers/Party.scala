package controllers

import play.api.data.Form
import play.api.data.Forms.{mapping, longNumber, nonEmptyText}
import play.api.mvc._

object Party extends Controller with securesocial.core.SecureSocial {
  val partyForm: Form[models.Party] = Form(
    mapping(
      "title" -> nonEmptyText
    )(models.Party.createFromForm)(models.Party.serializeToForm)
  )

  def index = SecuredAction { implicit request =>
    val user = models.User.findByIdentity(request.user)
    val parties = models.Parties.findForUser(user)
    Ok("")
  }

  def show(id: Long) = SecuredAction { implicit request =>
    val party = models.Party.findById(id)
    Ok(views.html.party.show(party))
  }

  def update(id: Long) = Action {
    val party = models.Party.findById(1)
    Redirect(routes.Party.show(party.id))
  }

  def create = Action { implicit request =>
    val form = partyForm.bindFromRequest()

    form.fold(
      hasErrors = { form =>
        Redirect(routes.Party.new_party())
      },
      success = { newParty =>
        models.Party.save(newParty)
        Redirect(routes.Party.index())
      }
    )
  }

  def new_party = Action { implicit request =>
    val form = partyForm.bindFromRequest()
    Ok(views.html.party.new_party(form))
  }

}

