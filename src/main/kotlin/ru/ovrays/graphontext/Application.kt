package ru.ovrays.graphontext

import ru.tinkoff.kora.application.graph.KoraApplication
import ru.tinkoff.kora.common.KoraApp
import ru.tinkoff.kora.config.yaml.YamlConfigModule
import ru.tinkoff.kora.database.jdbc.JdbcDatabaseModule
import ru.tinkoff.kora.database.liquibase.LiquibaseJdbcDatabaseModule
import ru.tinkoff.kora.http.client.ok.OkHttpClientModule
import ru.tinkoff.kora.http.server.undertow.UndertowHttpServerModule
import ru.tinkoff.kora.json.module.JsonModule
import ru.tinkoff.kora.logging.logback.LogbackModule

@KoraApp
interface Application : UndertowHttpServerModule,
    OkHttpClientModule,
    LiquibaseJdbcDatabaseModule,
    JdbcDatabaseModule,
    YamlConfigModule,
    LogbackModule,
    JsonModule

fun main() {
    KoraApplication.run { ApplicationGraph.graph() }
}
