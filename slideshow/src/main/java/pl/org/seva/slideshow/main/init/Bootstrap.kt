package pl.org.seva.slideshow.main.init

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import pl.org.seva.slideshow.main.createNotificationChannels


class Bootstrap {

    fun boot(ctx: Context) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        createNotificationChannels(ctx)
    }
}
