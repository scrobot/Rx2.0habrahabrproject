package mobile.geekbit.rx20habrahabrproject.backpressure

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_backpressure.*
import mobile.geekbit.rx20habrahabrproject.R

/**
 * Created by aleksejskrobot on 01.09.17.
 */

class BackpressureExampleActivity : AppCompatActivity() {

    private val n = 10000000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_backpressure)

        /*val source = PublishProcessor.create<Int>()

        source
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::addToIntListAdapter, Throwable::printStackTrace, this::onComplete)

        for(i in 0..n) {
            source.onNext(i)
            if(i == n) {
                source.onComplete()
            }
        }*/

        Flowable.range(1, n)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::addToIntListAdapter, Throwable::printStackTrace, this::onComplete)

//        Thread.sleep(10000)

    }

    private fun addToIntListAdapter(number: Int?) {
        Log.d("number", number.toString())

        // do something
    }

    private fun onComplete() {
        textView?.text = "completed"
    }

}