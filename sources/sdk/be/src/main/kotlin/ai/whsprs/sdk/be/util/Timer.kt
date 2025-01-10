package ai.whsprs.sdk.be.util


import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import java.util.Date
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.seconds

data class Tick(
    val timestamp: Long,
    val since: Duration,
    val left: Duration,
)

class Timer private constructor(
    private val duration: Duration,
    private val reversed: Boolean,
    private val rate: Duration = 1.seconds
) {

    fun start(): Flow<Tick> {
        val (start, end) = if (reversed) {
            duration.inWholeSeconds.toInt() to 0
        } else {
            1 to duration.inWholeSeconds.toInt() + 1
        }

        val timeline = if (reversed) start downTo end else start..end

        return timeline.asSequence().asFlow()
            .map { tick ->
                Tick(
                    timestamp = Date().time,
                    since = tick.seconds.times(rate.inWholeSeconds.toInt()),
                    left = if (reversed) tick.seconds else end.seconds - tick.seconds
                )
            }
            .onEach { delay(rate) }
    }

    companion object {

        fun countDown(duration: Duration, rate: Duration = 1.seconds): Timer {
            return Timer(duration = duration, reversed = true, rate = rate)
        }

        fun default(duration: Duration, rate: Duration = 1.seconds): Timer {
            return Timer(duration = duration, reversed = false, rate = rate)
        }

        fun endless(rate: Duration = 1.seconds): Timer {
            return Timer(duration = 999.hours, reversed = false, rate = rate)
        }
    }
}