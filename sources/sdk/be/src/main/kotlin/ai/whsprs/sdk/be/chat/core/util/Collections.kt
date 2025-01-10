package ai.whsprs.sdk.be.chat.core.util

internal inline fun <T : Any> List<T>.replaceAt(
    index: Int,
    replacement: (T) -> T
): List<T> {
    if (index < 0 || index > lastIndex) return this

    return this.mapIndexed { i, element ->
        if (i == index) replacement(element) else element
    }
}