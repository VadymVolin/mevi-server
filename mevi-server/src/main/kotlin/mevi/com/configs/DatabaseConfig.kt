package mevi.com.configs

import io.ktor.server.application.*
import mevi.com.cache.dao.factory.DatabaseFactory

object DatabaseConfig {

    var application: Application? = null

    val database by lazy {
        application?.let {
            DatabaseFactory.init(it.environment.config)
        }
    }
}

fun configureDatabase(application: Application) {
    DatabaseConfig.application = application
}