package mevi.com.repository.dao.tables

import org.jetbrains.exposed.sql.Table

object UserTable : Table() {
    val id = long("id").autoIncrement()
    val firstName = varchar("first_name", 16).nullable()
    val lastName = varchar("last_name", 16).nullable()
    val phoneNumber = varchar("phone_number", 13).nullable()
    val bio = varchar("bio", 128).nullable()
    val email = varchar("email", 128).nullable()
    val password = varchar("password", 16).nullable()
    val country = varchar("country", 30).nullable()
    val gender = varchar("gender", 20).nullable()
    val provider = varchar("provider", 20).nullable()
    override val primaryKey = PrimaryKey(id)
}

// TODO: need to create entity class for Exposed DAO API