package models

class Contribution(val id: Long, val party: Party, val user: User, val amount:BigDecimal) {
}
