package ru.ovrays.graphontext.repository.mapper

import ru.ovrays.graphontext.model.user.UserRole
import ru.tinkoff.kora.common.Component
import ru.tinkoff.kora.database.jdbc.mapper.result.JdbcResultColumnMapper
import java.sql.ResultSet

@Component
class UserRoleMapper : JdbcResultColumnMapper<UserRole> {
    override fun apply(row: ResultSet, index: Int): UserRole {
        val value = row.getString(index)
            .uppercase()

        return UserRole.valueOf(value)
    }
}
