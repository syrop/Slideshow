package pl.org.seva.slideshow.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.TargetBasedAnimation
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.Fragment
import com.google.accompanist.coil.rememberCoilPainter
import com.google.android.material.composethemeadapter.MdcTheme
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import pl.org.seva.slideshow.databinding.FrMainBinding
import pl.org.seva.slideshow.main.view.SegmentedProgressbar

class MainFragment : Fragment() {

    private lateinit var binding: FrMainBinding

    private val slides = listOf(
        "https://upload.wikimedia.org/wikipedia/commons/3/31/Michael_Jackson_in_1988.jpg",
        "https://upload.wikimedia.org/wikipedia/commons/8/8a/Jackson_5_tv_special_1972.JPG",
        "https://upload.wikimedia.org/wikipedia/commons/e/e4/Jackson_siblings_1977.jpg",
    )

    private val animation =  TargetBasedAnimation(tween(5000, easing = LinearEasing), Int.VectorConverter, 0, 1000)

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View {
        binding = FrMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.compose.setContent {
            MdcTheme {
                val timer = remember {
                    flow {
                        while (true) {
                            repeat(slides.size) {
                                emit(it)
                                delay(5_000L)
                            }
                        }
                    }
                }.collectAsState(initial = 0).value
                Column {
                    repeat(slides.size) {
                        val slide = slides[it]
                        AnimatedVisibility(visible = timer == it) {
                            val fraction = remember(it) {
                                flow {
                                    val beginning = System.nanoTime()
                                    while (true) {
                                        emit(
                                            animation.getValueFromNanos(System.nanoTime() - beginning)
                                                .toFloat() / 1000f
                                        )
                                        delay(10L)
                                    }
                                }
                            }.collectAsState(initial = 0f).value
                            Box(modifier = Modifier.padding(16.dp)) {
                                Image(
                                    painter = rememberCoilPainter(
                                        request = slide,
                                    ),
                                    contentDescription = null,
                                    contentScale = ContentScale.FillWidth,
                                    modifier = Modifier.fillMaxWidth(),
                                )
                                key(fraction) {
                                    AndroidView(
                                        factory = { context ->
                                            SegmentedProgressbar(
                                                context,
                                                null,
                                            ).apply {
                                                this.fraction = fraction
                                                numberOfSegments = slides.size
                                                progress = it
                                            }
                                        },
                                        modifier = Modifier
                                            .padding(bottom = 24.dp)
                                            .align(Alignment.BottomCenter)
                                            .width(120.dp)
                                            .height(6.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
