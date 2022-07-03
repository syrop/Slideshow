package pl.org.seva.slideshow.main

import android.app.Application
import com.google.android.material.color.DynamicColors
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import pl.org.seva.slideshow.main.init.Bootstrap

@Suppress("unused")
class MyApplication : Application() {

    private val bootstrap by instance<Bootstrap>()

    override fun onCreate() {
        super.onCreate()
        GlobalScope.launch { bootstrap.boot(this@MyApplication) }
        DynamicColors.applyToActivitiesIfAvailable(this)
    }
}
