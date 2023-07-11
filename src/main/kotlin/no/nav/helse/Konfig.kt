import java.time.Duration

class Konfig(
    val appNavn: String,
    val jdbcUrl: String,
    val dbUsername: String,
    val dbPassword: String,
    val dbMaximumPoolSize: Int,
    val dbConnectionTimeout: Long,
    val dbMaxLifetime: Long,
    val dbInitializationFailTimeout: Long,
) {
    companion object {
        fun fromEnv(): Konfig {
            val appNavn = System.getenv("NAIS_APP_NAME")
            return Konfig(
                appNavn,
                String.format(
                    "jdbc:postgresql://%s:%s/%s",
                    System.getenv("DATABASE_HOST"),
                    System.getenv("DATABASE_PORT"),
                    System.getenv("DATABASE_DATABASE")
                ),
                System.getenv("DATABASE_USERNAME") ?: "",
                System.getenv("DATABASE_PASSWORD") ?: "",
                1,
                Duration.ofSeconds(30).toMillis(),
                Duration.ofMinutes(30).toMillis(),
                Duration.ofMinutes(1).toMillis(),
            )
        }
    }
}