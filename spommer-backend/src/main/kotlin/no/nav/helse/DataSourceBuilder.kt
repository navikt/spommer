package no.nav.helse

import Konfig
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.flywaydb.core.Flyway
import java.time.Duration

class DataSourceBuilder(konfig: Konfig) {

    private val hikariConfig = HikariConfig().apply {
        jdbcUrl = konfig.jdbcUrl
        username = konfig.dbUsername
        password = konfig.dbPassword
        maximumPoolSize = konfig.dbMaximumPoolSize
        connectionTimeout = konfig.dbConnectionTimeout
        maxLifetime = konfig.dbMaxLifetime
        initializationFailTimeout = konfig.dbInitializationFailTimeout
    }

    private val hikariMigrationConfig = HikariConfig().apply {
        jdbcUrl = konfig.jdbcUrl
        username = konfig.dbUsername
        password = konfig.dbPassword
        initializationFailTimeout = Duration.ofMinutes(1).toMillis()
        connectionTimeout = Duration.ofMinutes(1).toMillis()
        maximumPoolSize = 2
    }

    private fun runMigration(dataSource: HikariDataSource, initSql: String = "") =
        Flyway.configure()
            .dataSource(dataSource)
            .baselineOnMigrate(true)
            .initSql(initSql)
            .load()
            .migrate()

    internal fun getDataSource(): HikariDataSource {
        return HikariDataSource(hikariConfig)
    }

    internal fun migrate() {
        HikariDataSource(hikariMigrationConfig).use { runMigration(it) }
    }
}