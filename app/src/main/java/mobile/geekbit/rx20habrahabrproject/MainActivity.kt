package mobile.geekbit.rx20habrahabrproject

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.activity_main.*
import mobile.geekbit.rx20habrahabrproject.`null`.NullPointerActivity
import mobile.geekbit.rx20habrahabrproject.backpressure.BackpressureExampleActivity
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        RxView.clicks(backpressure)
                .debounce(200, TimeUnit.MILLISECONDS)
                .subscribe (
                        { openNewScreen(BackpressureExampleActivity::class.java) }, Throwable::printStackTrace)

        RxView.clicks(nullpointer)
                .debounce(200, TimeUnit.MILLISECONDS)
                .subscribe ({ openNewScreen(NullPointerActivity::class.java) }, Throwable::printStackTrace)
    }
}
