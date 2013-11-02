package controllers

import play.api.data.Form
import play.api.data.Forms.{mapping, longNumber, nonEmptyText}
import play.api.mvc._

object Party extends Controller with Secured {
  val partyForm: Form[models.Party] = Form(
    mapping(
      "title" -> nonEmptyText
    )(models.Party.createFromForm)(models.Party.serializeToForm)
  )

  def index = isAuthenticated { user => _ =>
    val parties = models.Party.findAll(10)
    Ok(views.html.party.index(parties, user))
  }

  def show(id: Long) = isAuthenticated { user => _ =>
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

