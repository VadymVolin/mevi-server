package mevi.com.repository.dao

import mevi.com.configs.DatabaseConfig
import mevi.com.repository.dao.models.UserEntity
import org.jetbrains.exposed.sql.transactions.transaction

object UserDataProxy {

    fun findUserById(id: Long): UserEntity? {
        return transaction(DatabaseConfig.database) {
            return@transaction UserEntity.findById(id)
        }
    }

}