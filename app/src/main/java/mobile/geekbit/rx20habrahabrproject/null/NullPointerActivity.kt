package mobile.geekbit.rx20habrahabrproject.`null`

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.TextView
import io.reactivex.Observable
import io.reactivex.Single
import org.jetbrains.anko.textView
import org.jetbrains.anko.verticalLayout

/**
 * Created by aleksejskrobot on 01.09.17.
 */
class NullPointerActivity : AppCompatActivity() {

    val TAG = NullPointerActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var obsJust: TextView? = null
        var singleJust: TextView? = null
        var callable: TextView? = null
        var nullMaping: TextView? = null

        verticalLayout {
            obsJust = textView()
            singleJust = textView()
            callable = textView()
            nullMaping = textView()
        }

        try {
            Observable.just(null).subscribe()
        } catch (e: Exception) {
            obsJust?.text = e.localizedMessage
            e.printStackTrace()
        }

        try {
            Single.just(null).subscribe()
        } catch (e: Exception) {
            singleJust?.text = e.localizedMessage
            e.printStackTrace()
        }

        Observable.fromCallable{null}.subscribe({Log.d(TAG, it)}, {
            callable?.text = it.localizedMessage
            it.printStackTrace()
        })

        Observable.just(1).map{null}.subscribe({Log.d(TAG, it)}, {
            nullMaping?.text = it.localizedMessage
            it.printStackTrace()
        })

        val source = Observable.create<Any> {emitter ->
            Log.d(TAG, "Side-effect 1")
            emitter.onNext(Irrelevant.INSTANCE)

            Log.d(TAG, "Side-effect 2")
            emitter.onNext(Irrelevant.INSTANCE)

            Log.d(TAG, "Side-effect 3")
            emitter.onNext(Irrelevant.INSTANCE)
        }

        source.subscribe({Log.d(TAG, it.toString())}, Throwable::printStackTrace)
    }

    enum class Irrelevant { INSTANCE; }

}