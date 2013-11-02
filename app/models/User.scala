package models

case class User(id:Long, login:String, email: String)

object User {
  def authenticate(login:String, password:String) = {
    User(1, login, password)
  }

  def findByEmail(email: Option[String]) = email match {
      case Some(mail) => Some(User(1, mail, "Whatever"))
      case _ => None
  }
}
