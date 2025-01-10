package ai.whsprs.di

import android.content.Context
import com.google.gson.Gson

interface CoreComponentDependencies : ComponentDependencies {

    val context: Context
    val gson: Gson
}