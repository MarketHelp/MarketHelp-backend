package ru.ovrays.graphontext.service.util

import ru.tinkoff.kora.common.Component
import java.time.Clock
import java.time.OffsetDateTime

@Component
class ClockService {
    private val clock: Clock = Clock.systemDefaultZone()

    fun now(): OffsetDateTime {
        return OffsetDateTime.now(clock)
    }
}
