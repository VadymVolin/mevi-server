package mevi.com.configs

import io.ktor.server.application.*
import mevi.com.repository.dao.factory.DatabaseFactory
import org.jetbrains.exposed.sql.Database

object DatabaseConfig {

    var application: Application? = null

    val database by lazy {
        application?.let {
            DatabaseFactory.init(it.environment.config)
        }
    }
}

fun Application.configureDatabase(application: Application) {
    DatabaseConfig.application = application
}