package com.tina.mr9

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.tina.mr9.rate.RateViewModel
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mock
import java.math.BigDecimal


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun addition_isWrong() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun get() {


        assertEquals(4, 2 + 2)
    }

    @Mock
    lateinit var rMVM : RateViewModel
    lateinit var bindingAdapter: BindingAdapter




    @Test
    fun bindFloatToString() {

//        val args = -4.553F

        val args = 4.553F
        var textView = ""

        textView = if (args >= 0) {

            args.toDouble().toBigDecimal().setScale(2, BigDecimal.ROUND_HALF_UP).toString()

        }else{

            "NA"
        }

        assertEquals("NA",textView)

    }

    @Test
    fun bindArrayToString() {

//        val args = listOf<String>("GIN","RUM")

        val args = listOf<String>()


        val textView = (args.let { java.lang.String.join(", ", args ?: listOf()) })

        assertEquals("",textView)
    }
}
