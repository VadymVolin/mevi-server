package mevi.com.repository.dao

import mevi.com.configs.DatabaseConfig
import mevi.com.repository.dao.tables.UserTable
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object UserDataProxy {

    fun findUserById(id: Long) {
        transaction(DatabaseConfig.database) {
            UserTable.
        }
    }

}