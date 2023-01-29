package mevi.com.repository.tables

import org.jetbrains.exposed.sql.Table

object UserTable : Table() {
    val id = integer("id").autoIncrement()
    val firstName = varchar("first_name", 16)
    val lastName = varchar("last_name", 16)
    val phoneNumber = varchar("phone_number", 13)
    val bio = varchar("bio", 128)
    val email = varchar("email", 128)
    val password = varchar("password", 16)
    val country = varchar("country", 30)
    val gender = varchar("gender", 20)

    override val primaryKey = PrimaryKey(id)
}