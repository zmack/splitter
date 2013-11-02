package models
import scala.collection.mutable.MutableList

case class Party(id: Long, user:User, title: String) {
  def members = {
    (0 to 10).map(n => User(n, s"Awesome guy $n from party $id", "some@email.com"))
  }

  def contributions = {
    members.map(member => new Contribution(0, this, member, 10.00))
  }

  def isOwnedBy(user: User) = {
    true
  }
}


object Party {
  val testUser = User(0, "Test guy", "test@email.com")
  val partyPersistence = MutableList[Party]()

  def findAll(count:Int) = {
    partyPersistence ++ createDynamic(count - partyPersistence.length)
  }

  def createDynamic(count:Int) = {
    (0 to count).map(n => new Party(n, testUser, s"This awesome party $n"))
  }

  def findById(id: Long) = {
    new Party(id, testUser, s"Awesome party $id")
  }

  def createFromForm(title: String) = {
    new Party(0, null, title)
  }

  def serializeToForm(party:Party) = {
    Some(party.title)
  }

  def save(party:Party) = {
    partyPersistence += party
  }
}
