package models

import securesocial.core.Identity

case class User(id:Long, login:String, email: String, identity: Option[Identity] = None) {
  def getAvatar = {
    identity match {
      case Some(actual_identity) => actual_identity.avatarUrl.getOrElse("none.jpg")
      case _ => "none.jpg"
    }
  }
}

object User {
  def authenticate(login:String, password:String) = {
    User(1, login, password)
  }

  def findByEmail(email: Option[String]) = email match {
      case Some(mail) => Some(User(1, mail, "Whatever"))
      case _ => None
  }

  def findByIdentity(identity:Identity) = {
    User(1, identity.email.get, identity.fullName, Some(identity))
  }
}
