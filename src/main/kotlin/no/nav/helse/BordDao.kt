package no.nav.helse

import kotliquery.queryOf
import kotliquery.sessionOf
import org.intellij.lang.annotations.Language
import org.slf4j.LoggerFactory
import javax.sql.DataSource

internal class BordDao(private val dataSource: DataSource) {
    private companion object {
        private val log = LoggerFactory.getLogger("tjenestekall")
    }
    fun leggInnStol(stol: String) {
        log.info("legger inn stol")
        @Language("PostgreSQL")
        val statement = "INSERT INTO bord (stol) VALUES (?)"
        sessionOf(dataSource).use { session ->
            session.run(queryOf(statement, stol).asUpdate)
        }
    }

    fun hentStoler(): Int? {
        @Language("PostgreSQL")
        val statement = "SELECT COUNT (1) FROM bord"
        return sessionOf(dataSource).use { session ->
            session.run(queryOf(statement).map {
                it.int("count")
            }.asSingle)
        }
    }


}
