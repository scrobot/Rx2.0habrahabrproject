package mobile.geekbit.rx20habrahabrproject.backpressure

/**
 * Created by aleksejskrobot on 05.09.17.
 */
class IntMapper {

    private val KOFF = 2

    fun map(int: Int?): Int = int ?: 0 * KOFF

}