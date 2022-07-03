package pl.org.seva.slideshow.main

import android.os.Build
import androidx.test.espresso.idling.CountingIdlingResource
import org.kodein.di.*
import pl.org.seva.slideshow.main.init.Bootstrap

inline fun <reified T : Any> instance(tag: Any? = null) = kodein.instance<T>(tag)

inline fun <reified A : Any, reified T : Any> instance(tag: Any? = null, arg: A) =
    kodein.instance<A, T>(tag, arg)

inline val <T> DIProperty<T>.value get() = provideDelegate(null, Build::ID).value

val kodein = DI {
    bind<Bootstrap>() with singleton { Bootstrap() }
    bind<CountingIdlingResource>() with singleton { CountingIdlingResource("espresso") }
}
