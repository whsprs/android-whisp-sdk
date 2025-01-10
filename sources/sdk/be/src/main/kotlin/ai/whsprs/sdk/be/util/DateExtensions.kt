package ai.whsprs.sdk.be.util

import android.icu.text.MessageFormat
import android.icu.util.ULocale
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.OffsetTime
import java.time.Period
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.time.format.TextStyle
import java.time.temporal.ChronoUnit
import java.time.temporal.WeekFields
import java.util.Locale
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.TimeMark


private const val SERVER_FORMAT_DATE_TIME = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
private const val SERVER_FORMAT_DATE_TIME_SHORT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
private const val SERVER_FORMAT_DATE = "yyyy-MM-dd"
private const val HOURS_FORMAT_TIME = "HH:mm"

fun java.time.Duration.toFormattedString(
    unitsLocalized: Triple<String, String, String>,
): String {
    val (dayLocalized, hourLocalized, minuteLocalized) = unitsLocalized
    var hours = if (seconds < 0) 0 else seconds / 3600
    val min = if (seconds < 0) 0 else (seconds % 3600) / 60
    val sec = if (seconds < 0) 0 else seconds - hours * 3600 - min * 60
    val days = if (seconds < 0) 0 else seconds / 3600 / 24

    if (days > 0) {
        hours -= days * 24
    }

    val dString = days.toString()
    val hString = if (days > 0 || hours > 9) hours.toString() else "0$hours"
    val mString = if (days > 0 || min > 9) min.toString() else "0$min"
    val sString = if (days > 0 || sec > 9) sec.toString() else "0$sec"

    return if (days > 0) {
        "$dString$dayLocalized $hString$hourLocalized $mString$minuteLocalized"
    } else {
        "$hString:$mString:$sString"
    }
}

fun kotlin.time.Duration.toLocalized(
    hoursUnitLocalized: String,
    minsUnitLocalized: String,
): String {
    with(this) {
        val (hours, mins) = toComponents { hours, minutes, _, _ -> Pair(hours, minutes) }
        return if (hours > 0L && mins > 0) {
            "$hours$hoursUnitLocalized $mins$minsUnitLocalized"
        } else {
            "$hours$hoursUnitLocalized"
        }
    }
}

fun kotlin.time.Duration?.secondsOrNull(): Int? {
    return this?.inWholeSeconds?.toInt()
}

fun LocalTime.toShortFormatString(use24Hours: Boolean): String {
    return if (minute > 0) {
        val formatter = DateTimeFormatter.ofPattern(if (use24Hours) "H:mm" else "h:mma", Locale.getDefault())
        format(formatter)
    } else {
        val formatter = DateTimeFormatter.ofPattern(if (use24Hours) "H" else "ha", Locale.getDefault())
        format(formatter)
    }
}

fun OffsetDateTime.withCurrentZoneAndOffset(): OffsetDateTime =
    withOffsetSameInstant(OffsetDateTime.now().offset)
        .atZoneSameInstant(ZoneId.systemDefault())
        .toOffsetDateTime()

fun OffsetDateTime?.toLocalInstant(): Instant? {
    return this?.toLocalDateTime()?.toInstant(ZoneOffset.UTC)
}

fun String.fromServerFormatUTCString(offsetSeconds: Int? = null): OffsetDateTime {
    val dateTime = try {
        val formatter = DateTimeFormatter.ofPattern(SERVER_FORMAT_DATE_TIME)
        LocalDateTime.parse(this, formatter)
    } catch (e: DateTimeParseException) {
        val formatter = DateTimeFormatter.ofPattern(SERVER_FORMAT_DATE_TIME_SHORT)
        LocalDateTime.parse(this, formatter)
    }

    return dateTime
        .atOffset(ZoneOffset.UTC)
        .withOffsetSameInstant(offsetSeconds?.let(ZoneOffset::ofTotalSeconds) ?: OffsetDateTime.now().offset)
}

fun String.fromServerFormatString(): LocalDateTime {
    return try {
        val formatter = DateTimeFormatter.ofPattern(SERVER_FORMAT_DATE_TIME)
        LocalDateTime.parse(this, formatter)
    } catch (e: DateTimeParseException) {
        val formatter = DateTimeFormatter.ofPattern(SERVER_FORMAT_DATE_TIME_SHORT)
        LocalDateTime.parse(this, formatter)
    }
}

fun String.fromServerDateFormatString(): LocalDate {
    val formatter = DateTimeFormatter.ofPattern(SERVER_FORMAT_DATE)
    return LocalDate.parse(this, formatter)
}

fun OffsetDateTime.toServerFormatUTCString() = withOffsetSameInstant(ZoneOffset.UTC).toServerFormatString()

fun OffsetDateTime.toServerFormatString(): String {
    val formatter = DateTimeFormatter.ofPattern(SERVER_FORMAT_DATE_TIME)
    return formatter.format(withNano(0))
}

fun OffsetDateTime.toHoursFormatString(): String {
    val formatter = DateTimeFormatter.ofPattern(HOURS_FORMAT_TIME)
    return formatter.format(withNano(0))
}

fun LocalDate.toServerDateFormatString(): String {
    val formatter = DateTimeFormatter.ofPattern(SERVER_FORMAT_DATE)
    return formatter.format(this)
}

fun LocalDate.formatOrdinalDate(locale: Locale): String {
    val monthName = month.getDisplayName(TextStyle.FULL_STANDALONE, locale)
    val dayOfMonth = dayOfMonth
    val formatter = MessageFormat("{1} {0,ordinal}", ULocale.forLocale(locale))
    return formatter.format(arrayOf<Any>(dayOfMonth, monthName))
}

fun LocalDate.tomorrow(): LocalDate {
    return this.plusDays(1)
}

fun OffsetDateTime.toServerDateFormatString(): String {
    val formatter = DateTimeFormatter.ofPattern(SERVER_FORMAT_DATE)
    return formatter.format(this)
}

fun OffsetDateTime.generateServerId() = toServerFormatString()

fun OffsetDateTime.isSameDay(offsetDateTime: OffsetDateTime) =
    year == offsetDateTime.year && monthValue == offsetDateTime.monthValue && dayOfMonth == offsetDateTime.dayOfMonth

fun OffsetDateTime.isSameWeek(offsetDateTime: OffsetDateTime) =
    year == offsetDateTime.year && monthValue == offsetDateTime.monthValue && weekStart() == offsetDateTime.weekStart()

fun OffsetDateTime.formatWithCurrentZoneAndOffset(formatter: DateTimeFormatter): String {
    return formatter.format(withCurrentZoneAndOffset())
}

fun OffsetDateTime.dayStart(): OffsetDateTime = this
    .withHour(LocalTime.MIN.hour)
    .withMinute(LocalTime.MIN.minute)
    .withSecond(LocalTime.MIN.second)
    .withNano(LocalTime.MIN.nano)

fun OffsetDateTime.dayEnd(): OffsetDateTime = this
    .withHour(LocalTime.MAX.hour)
    .withMinute(LocalTime.MAX.minute)
    .withSecond(LocalTime.MAX.second)
    .withNano(LocalTime.MAX.nano)

fun ZonedDateTime.dayStart(): ZonedDateTime = this
    .withHour(LocalTime.MIN.hour)
    .withMinute(LocalTime.MIN.minute)
    .withSecond(LocalTime.MIN.second)
    .withNano(LocalTime.MIN.nano)

fun timeBetweenDateAndNow(date: String?): Period? {
    val birthdayDate = birthdayDate(date) ?: return null
    return Period.between(birthdayDate, LocalDate.now())
}

fun birthdayDate(birthday: String?): LocalDate? = birthday?.let {
    try {
        LocalDate.parse(birthday, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    } catch (e: Exception) {
        try {
            LocalDate.parse(birthday, DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.getDefault()))
        } catch (e: Exception) {
            null
        }
    }
}

fun OffsetDateTime.lastMonday(): OffsetDateTime {
    val dayOfWeek = WeekFields.of(DayOfWeek.MONDAY, 7).dayOfWeek()
    return this.with(dayOfWeek, 1).dayStart()
}

fun Long.toOffsetDateTime(): OffsetDateTime {
    val offset = OffsetDateTime.now().offset
    val localDateTime = LocalDateTime.ofEpochSecond(this, 0, offset)
    return localDateTime.atOffset(offset)
}

fun LocalDateTime.dayStart(): LocalDateTime = this
    .withHour(LocalTime.MIN.hour)
    .withMinute(LocalTime.MIN.minute)
    .withSecond(LocalTime.MIN.second)
    .withNano(LocalTime.MIN.nano)

fun OffsetDateTime.weekStart(): OffsetDateTime {
    val dayOfWeek = WeekFields.of(Locale.getDefault()).dayOfWeek()
    return with(dayOfWeek, 1).dayStart()
}

val OffsetDateTime.timestamp: Long
    get() = this.toInstant().toEpochMilli()

fun LocalDateTime.weekStart(): LocalDateTime {
    val dayOfWeek = WeekFields.of(Locale.getDefault()).dayOfWeek()
    return with(dayOfWeek, 1).dayStart()
}

fun LocalDate.weekStart(): LocalDate {
    val dayOfWeek = WeekFields.of(Locale.getDefault()).dayOfWeek()
    return with(dayOfWeek, 1)
}

fun OffsetDateTime.weekEnd(): OffsetDateTime {
    return weekStart().plusDays(7)
}

fun LocalDateTime.weekEnd(): LocalDateTime {
    return weekStart().plusDays(7)
}

fun LocalDate.weekEnd(): LocalDate {
    return weekStart().plusDays(6)
}

fun LocalDate.monthStart(): LocalDate {
    return withDayOfMonth(1)
}

fun LocalDate.monthEnd(): LocalDate {
    return withDayOfMonth(lengthOfMonth())
}

fun OffsetDateTime.withTime(time: LocalTime) = this
    .withHour(time.hour)
    .withMinute(time.minute)
    .withSecond(time.second)
    .withNano(time.nano)

fun OffsetDateTime.withDate(date: LocalDate) = this
    .withYear(date.year)
    .withDayOfYear(date.dayOfYear)

fun LocalDate.toSeconds() = atTime(LocalTime.MIN).toEpochSecond(OffsetTime.now().offset)

fun LocalDate.toMilliseconds() = atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()

fun DayOfWeek.toServerId() = if (value == 6) 7 else (value + 1) % 7

fun DayOfWeek.yesterday() = minus(1)

fun DayOfWeek.tommorow() = plus(1)

fun OffsetDateTime.toEpochDays() = toEpochSecond() / 60 / 60 / 24

fun TimeMark?.elapsedSecondsOrNull(): Int? {
    return this?.elapsedNow()?.inWholeSeconds?.toInt()
}

/**
 * Returns the [KotlinDuration] between receiver date and other date
 */
operator fun OffsetDateTime.minus(other: OffsetDateTime): Duration {
    return (toInstant().toEpochMilli() - other.toInstant().toEpochMilli()).milliseconds
}

fun OffsetDateTime.formatWithPattern(pattern: String, locale: Locale): String {
    return toLocalDate().formatWithPattern(pattern, locale)
}

fun OffsetDateTime.tomorrow(): OffsetDateTime = this.plusDays(1)

fun LocalDate.toOffsetDateTime(offset: ZoneOffset): OffsetDateTime {
    return atStartOfDay().atOffset(offset)
}

fun LocalDate.formatWithPattern(pattern: String, locale: Locale): String {
    return DateTimeFormatter.ofPattern(pattern, locale).format(this)
}

fun OffsetDateTime.isSameYear(offsetDateTime: OffsetDateTime) =
    year == offsetDateTime.year

fun OffsetDateTime.isYesterday(): Boolean {
    val now = OffsetDateTime.now()
    val daysDifference = ChronoUnit.DAYS.between(this.toLocalDate(), now.toLocalDate())
    return daysDifference == 1L
}

fun String.toLocalTime(dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ISO_TIME): LocalTime {
    return LocalTime.parse(this, dateTimeFormatter)
}

fun String.toLocalDateTime(): LocalDateTime? {
    return try {
        LocalDateTime.parse(this)
    } catch (e: DateTimeParseException) {
        null
    }
}
