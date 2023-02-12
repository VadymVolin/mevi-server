package mevi.com.cache.dao

import mevi.com.cache.dao.models.Provider
import mevi.com.configs.DatabaseConfig
import mevi.com.cache.dao.models.UserEntity
import mevi.com.cache.dao.tables.UserTable
import mevi.com.constants.EMAIL_DELIMITER
import mevi.com.routes.api.web.auth.models.GoogleOAuth2User
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.Locale

object UserDbProxy {

    fun findUserById(id: Long): UserEntity? {
        return transaction(DatabaseConfig.database) {
            return@transaction UserEntity.findById(id)
        }
    }

    fun findUserByEmail(email: String): UserEntity? {
        return transaction(DatabaseConfig.database) {
            return@transaction UserEntity.find(UserTable.email eq email).firstOrNull()
        }
    }

    fun createGoogleUser(userInfo: GoogleOAuth2User): UserEntity {
        return transaction(DatabaseConfig.database) {
            return@transaction UserEntity.new {
                nickName = userInfo.email.substringBeforeLast(EMAIL_DELIMITER)
                firstName = userInfo.givenName
                lastName = userInfo.familyName
                email = userInfo.email
                country = Locale(userInfo.locale, userInfo.locale).displayCountry
                provider = Provider.GOOGLE.name
            }
        }
    }

}