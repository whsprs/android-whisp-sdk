package ai.whsprs.sdk.be.util


import android.content.Context
import android.os.SystemClock
import android.text.format.DateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.ZonedDateTime
import java.util.Calendar
import java.util.Date
import kotlin.time.TimeMark
import kotlin.time.TimeSource

interface DateProvider {

    val currentDateTime: OffsetDateTime
    val currentDate: LocalDate
    val currentLocalDateTime: LocalDateTime
    val currentTime: LocalTime
    val currentZonedDateTime: ZonedDateTime
    val date: Date
    val calendar: Calendar

    val currentTimeMillis: Long
    val elapsedRealtime: Long

    val currentTimeMark: TimeMark

    val is24HourFormat: Boolean

    companion object {

        fun dateProvider(context: Context): DateProvider {
            return DateProviderImpl(context)
        }
    }
}

private class DateProviderImpl(
    private val context: Context
) : DateProvider {

    override val currentDateTime: OffsetDateTime
        get() = OffsetDateTime.now()

    override val currentDate: LocalDate
        get() = LocalDate.now()

    override val currentLocalDateTime: LocalDateTime
        get() = LocalDateTime.now()

    override val currentTime: LocalTime
        get() = LocalTime.now()

    override val currentZonedDateTime: ZonedDateTime
        get() = ZonedDateTime.now()

    override val date: Date
        get() = Date()

    override val calendar: Calendar
        get() = Calendar.getInstance()

    override val currentTimeMillis: Long
        get() = System.currentTimeMillis()

    override val elapsedRealtime: Long
        get() = SystemClock.elapsedRealtime()

    override val currentTimeMark: TimeMark
        get() = TimeSource.Monotonic.markNow()

    override val is24HourFormat: Boolean
        get() = DateFormat.is24HourFormat(context)
}