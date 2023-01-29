package mevi.com.configs.db

import io.ktor.server.application.*
import mevi.com.configs.db.dao.DatabaseFactory

fun Application.configureDatabase() {
    DatabaseFactory.init(this@configureDatabase.environment.config)
}