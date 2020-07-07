package ch.swissdev.picker

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import ch.swissdev.library.R

class Picker(context: Context, attrs: AttributeSet) : RecyclerView(context, attrs) {

    private var isHorizontal: Boolean

    init {
        val styledAttributes = context.theme.obtainStyledAttributes(attrs, R.styleable.Picker, 0, 0)
        isHorizontal = styledAttributes.getInteger(R.styleable.Picker_orientation, 0) == 0

        clipToPadding = false
        layoutManager = LayoutManager(context, if (isHorizontal) HORIZONTAL else VERTICAL)

        LinearSnapHelper().attachToRecyclerView(this)
    }

    override fun onMeasure(widthSpec: Int, heightSpec: Int) {
        super.onMeasure(widthSpec, heightSpec)

        if (isHorizontal) {
            val padding = MeasureSpec.getSize(widthSpec) / 2
            setPadding(padding, 0, padding, 0)
        } else {
            val padding = MeasureSpec.getSize(heightSpec) / 2
            setPadding(0, padding, 0, padding)
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        smoothScrollToPosition(0)
    }

}
