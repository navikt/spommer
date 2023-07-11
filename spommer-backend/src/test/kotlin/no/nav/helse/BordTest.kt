package no.nav.helse

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.flywaydb.core.Flyway
import org.junit.jupiter.api.*
import org.testcontainers.containers.PostgreSQLContainer
import javax.sql.DataSource
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BordTest {

    private val postgres = PostgreSQLContainer<Nothing>("postgres:13")
    protected lateinit var dataSource: DataSource

    @BeforeEach
    fun setup() {
        Flyway.configure()
            .dataSource(dataSource)
            .cleanDisabled(false)
            .load()
            .also { it.clean() }
            .migrate()
    }

    @BeforeAll
    fun `start postgres`() {
        postgres.start()
        dataSource = HikariDataSource(HikariConfig().apply {
            jdbcUrl = postgres.jdbcUrl
            username = postgres.username
            password = postgres.password
            maximumPoolSize = 3
            minimumIdle = 1
            initializationFailTimeout = 5000
            idleTimeout = 10001
            connectionTimeout = 1000
            maxLifetime = 30001
        })
    }

    @AfterAll
    fun `stop postgres`() {
        postgres.stop()
    }

    @Test
    @Disabled
    fun `legger inn stol`() {
        val bordDao = BordDao(dataSource)
        bordDao.leggInnStol("stol")
        val stoler = bordDao.hentStoler()
        assertEquals(1, stoler)
    }
}