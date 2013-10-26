package models

class Party(val id: Long, val user:User, val title: String) {
  def members = {
    (0 to 10).map(n => User(n, s"Awesome guy $n from party $id", "some@email.com"))
  }

  def contributions = {
    members.map(member => new Contribution(0, this, member, 10.00))
  }
}


object Party {
  val testUser = User(0, "Test guy", "test@email.com")

  def findAll(count:Int) = {
    (0 to count).map(n => new Party(n, testUser, s"This awesome party $n"))
  }

  def findById(id: Long) = {
    new Party(id, testUser, s"Awesome party $id")
  }
}
