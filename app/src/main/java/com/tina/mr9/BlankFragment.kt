package com.tina.mr9

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.xw.repo.BubbleSeekBar
import com.xw.repo.BubbleSeekBar.OnProgressChangedListenerAdapter
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BlankFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DemoFragment1 : Fragment() {
    @Nullable
    override fun onCreateView(
        @NonNull inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_demo_1, container, false)
        val bubbleSeekBar: BubbleSeekBar = view.findViewById(R.id.demo_1_seek_bar)
        bubbleSeekBar.setProgress(20f)
        val button =
            view.findViewById<Button>(R.id.demo_1_button)
        button.setOnClickListener { v ->
            val progress = Random().nextInt(bubbleSeekBar.max.toInt())
            bubbleSeekBar.setProgress(progress.toFloat())
            Snackbar.make(v, "set random progress = $progress", Snackbar.LENGTH_SHORT).show()
        }


        return view
    }

    companion object {
        fun newInstance(): DemoFragment1 {
            return DemoFragment1()
        }
    }
}

