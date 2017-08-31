package mobile.geekbit.rx20habrahabrproject

import android.content.Context
import android.content.Intent

/**
 * Created by aleksejskrobot on 01.09.17.
 */

fun Context.openNewScreen(clazz: Class<*>) {
    val intent = Intent(this, clazz)
    this.startActivity(intent)
}
