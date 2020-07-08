package ch.swissdev.picker

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper

class PickerView(context: Context, attrs: AttributeSet) : RecyclerView(context, attrs) {

    private val isHorizontal: Boolean

    private val snapHelper: SnapHelper

    init {
        val styledAttributes = context.theme.obtainStyledAttributes(attrs, R.styleable.PickerView, 0, 0)
        isHorizontal = styledAttributes.getInteger(R.styleable.PickerView_orientation, 0) == 0

        // TODO: Figure out how to do this without having to specify a fade color
        val selectedItemColor = styledAttributes.getColor(R.styleable.PickerView_selectedItemColor, -1).takeIf { it != -1 }
        val fadeColor = styledAttributes.getColor(R.styleable.PickerView_fadeColor, -1).takeIf { it != -1 }

        clipToPadding = false
        layoutManager = PickerLayoutManager(context, if (isHorizontal) HORIZONTAL else VERTICAL, selectedItemColor, fadeColor)

        snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(this)
    }

    fun getCurrentItem(): View? {
        return snapHelper.findSnapView(layoutManager)
    }

    fun scrollToView(view: View) {
        smoothScrollToPosition(getChildAdapterPosition(view))
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
