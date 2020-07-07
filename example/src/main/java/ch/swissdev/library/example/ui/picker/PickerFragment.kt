package ch.swissdev.library.example.ui.picker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearSnapHelper
import ch.swissdev.library.example.R
import kotlinx.android.synthetic.main.fragment_picker.*

class PickerFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_picker, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        picker_horizontal.adapter = PickerAdapter(R.layout.picker_element_horizontal)

        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(picker_horizontal)

        picker_vertical.adapter = PickerAdapter(R.layout.picker_element_vertical)

        val snapHelper2 = LinearSnapHelper()
        snapHelper2.attachToRecyclerView(picker_vertical)
    }

}
