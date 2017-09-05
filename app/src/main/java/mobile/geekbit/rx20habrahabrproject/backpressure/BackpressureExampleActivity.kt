package mobile.geekbit.rx20habrahabrproject.backpressure

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import io.reactivex.BackpressureOverflowStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.processors.PublishProcessor
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber
import kotlinx.android.synthetic.main.activity_backpressure.*
import mobile.geekbit.rx20habrahabrproject.R
import java.nio.BufferOverflowException
import java.util.concurrent.TimeUnit


/**
 * Created by aleksejskrobot on 01.09.17.
 */

class BackpressureExampleActivity : AppCompatActivity() {

    private val TAG = BackpressureExampleActivity::class.simpleName
    private val n = 1000000
    private val adapter = BackpressureAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_backpressure)

        list.layoutManager = LinearLayoutManager(this)
        list.adapter = adapter

        Log.d(TAG, Integer.getInteger("rx2.buffer-size", 128).toString())

        case3()
    }

    private fun backpressureCase() {
        val source = PublishProcessor.create<Int>()

        source
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::addToIntListAdapter, Throwable::printStackTrace, this::onComplete)

        for(i in 0..n) {
            source.onNext(i)
            if(i == n) {
                source.onComplete()
            }
        }

        Thread.sleep(10000)
    }

    private fun backpressureBufferCase() {
        val source = PublishProcessor.create<Int>()

        source
                .buffer(1024*1024)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread(), false, 1024)
                .flatMap { PublishProcessor.fromIterable(it) }
                .subscribe(this::addToIntListAdapter, Throwable::printStackTrace, this::onComplete)

        for(i in 0..n) {
            source.onNext(i)
            if(i == n) {
                source.onComplete()
            }
        }
    }

    private fun backpressureSamplingCase() {
        val source = PublishProcessor.create<Int>()

        source
                .sample(1, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread(), false, 1024)
                .subscribe(this::addToIntListAdapter, Throwable::printStackTrace, this::onComplete)

        for(i in 0..n) {
            source.onNext(i)
            if(i == n) {
                source.onComplete()
            }
        }

    }

    private fun backpressureExpression() {
        Flowable.range(1, n)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::addToIntListAdapter, Throwable::printStackTrace, this::onComplete)
    }

    private fun backpressureBufferExpression() {
        Flowable.range(1, n)
                .onBackpressureBuffer(1024*1024)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::addToIntListAdapter, Throwable::printStackTrace, this::onComplete)
    }

    private fun backpressureBufferStrategyExpression() {
        Flowable.range(1, n)
                .onBackpressureBuffer(1024, { Toast.makeText(baseContext, BufferOverflowException::class.simpleName, Toast.LENGTH_SHORT).show() }, BackpressureOverflowStrategy.DROP_LATEST)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::addToIntListAdapter, Throwable::printStackTrace, this::onComplete)
    }

    private fun case2() {
        Flowable.range(1, n)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : DisposableSubscriber<Int>() {

                    public override fun onStart() {
                        request(1)
                    }

                    override fun onNext(v: Int?) {
                        addToIntListAdapter(v)

                        request(1)
                    }

                    override fun onError(ex: Throwable) {
                        ex.printStackTrace()
                    }

                    override fun onComplete() {
                        onComplete()
                    }
                })
    }

    private fun case3() {
        Flowable.range(1, n)
                .subscribe(object : DisposableSubscriber<Int>() {
                    lateinit var mapper: IntMapper

                    public override fun onStart() {
                        request(1)

                        mapper = IntMapper()
                    }

                    override fun onNext(v: Int?) {
                        addToIntListAdapter(mapper.map(v))

                        request(1)
                    }

                    override fun onError(ex: Throwable) {
                        ex.printStackTrace()
                    }

                    override fun onComplete() {
                        onComplete()
                    }
                })
    }

    private fun addToIntListAdapter(number: Int?) {
//        Log.d(TAG, number.toString())

        adapter.addItem(number)
    }

    private fun onComplete() {
        result?.text = "completed"
    }

}