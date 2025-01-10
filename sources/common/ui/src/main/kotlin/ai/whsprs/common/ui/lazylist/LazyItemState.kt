package ai.whsprs.common.ui.lazylist

interface LazyItemState {
    val key: String
    val contentType: String
        get() = this::class.java.simpleName
}