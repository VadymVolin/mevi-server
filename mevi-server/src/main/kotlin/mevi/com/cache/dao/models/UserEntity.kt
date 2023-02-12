package mevi.com.cache.dao.models

import mevi.com.cache.dao.tables.UserTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class UserEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<UserEntity>(UserTable)

    var nickName by UserTable.nickName
    var firstName by UserTable.firstName
    var lastName by UserTable.lastName
    var phoneNumber by UserTable.phoneNumber
    var age by UserTable.age
    var bio by UserTable.bio
    var email by UserTable.email
    var password by UserTable.password
    var country by UserTable.country
    var gender by UserTable.gender
    var provider by UserTable.provider
}