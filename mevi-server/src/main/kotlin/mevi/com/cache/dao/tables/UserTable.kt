package mevi.com.cache.dao.tables

import org.jetbrains.exposed.dao.id.LongIdTable

internal object UserTable : LongIdTable() {
    val nickName = varchar("nick_name", 16).nullable()
    val firstName = varchar("first_name", 16).nullable()
    val lastName = varchar("last_name", 16).nullable()
    val phoneNumber = varchar("phone_number", 13).nullable()
    val age = long("age").nullable()
    val bio = varchar("bio", 128).nullable()
    val email = varchar("email", 128).nullable()
    val password = varchar("password", 16).nullable()
    val country = varchar("country", 30).nullable()
    val gender = varchar("gender", 20).nullable()
    val provider = varchar("provider", 20).nullable()
}