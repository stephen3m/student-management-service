package com.student.management.databases
import io.micronaut.context.annotation.Value
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.micronaut.context.annotation.Context
import java.sql.Connection

@Context
class DatabaseManager(
    @Value("\${datasource.default.url}") private val jdbcUrl: String,
    @Value("\${datasource.default.username}") private val username: String,
    @Value("\${datasource.default.password}") private val password: String
) {
    private val dataSource: HikariDataSource

    init {
        val config = HikariConfig()
        config.jdbcUrl = jdbcUrl
        config.username = username
        config.password = password
        config.driverClassName = "org.postgresql.Driver"
        dataSource = HikariDataSource(config)
    }

    fun getConnection(): Connection {
        return dataSource.connection
    }

    fun close() {
        dataSource.close()
    }
}
