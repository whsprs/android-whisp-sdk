package ai.whsprs.di

interface ComponentProvider<T : Any> {

    fun component(): T
}