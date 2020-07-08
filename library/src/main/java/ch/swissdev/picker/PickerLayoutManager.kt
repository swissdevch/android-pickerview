package ch.swissdev.picker

import android.animation.ArgbEvaluator
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.BlendMode
import android.graphics.Color
import android.graphics.PorterDuff
import android.util.TypedValue
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class PickerLayoutManager(
    context: Context,
    @RecyclerView.Orientation orientation: Int,
    @ColorInt private val selectedItemColor: Int?,
    @ColorInt private val fadeColor: Int?
) : LinearLayoutManager(context, orientation, false) {

    private val centerSmoothScroller = CenterSmoothScroller(context)

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        super.onLayoutChildren(recycler, state)
        updateColors()
    }

    override fun scrollVerticallyBy(dy: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?): Int {
        return super.scrollVerticallyBy(dy, recycler, state).also {
            updateColors()
        }
    }

    override fun scrollHorizontallyBy(
        dx: Int,
        recycler: RecyclerView.Recycler?,
        state: RecyclerView.State?
    ): Int {
        return super.scrollHorizontallyBy(dx, recycler, state).also {
            updateColors()
        }
    }

    override fun smoothScrollToPosition(recyclerView: RecyclerView?, state: RecyclerView.State?, position: Int) {
        centerSmoothScroller.targetPosition = position
        startSmoothScroll(centerSmoothScroller)
    }

    private fun updateColors() {
        if (fadeColor == null && selectedItemColor == null) {
            return
        }

        val middle = if (orientation == VERTICAL) {
            height / 2
        } else {
            width / 2
        }

        // TODO: Get this working without TextView
        // TODO: Add more comments explaining how it works
        (0 until childCount).mapNotNull(this::getChildAt).mapNotNull { it as? TextView }.forEach { view ->
            val childMiddle = if (orientation == VERTICAL) {
                (getDecoratedTop(view) + getDecoratedBottom(view)) / 2
            } else {
                (getDecoratedLeft(view) + getDecoratedRight(view)) / 2
            }

            val distanceToMiddle = abs(middle - childMiddle).toFloat()

            val size = if (orientation == VERTICAL) view.height else view.width

            var color: Any? = null

            if (selectedItemColor != null && fadeColor != null && distanceToMiddle < size) {
                val colorAmount = max(0f, 1 - (distanceToMiddle / size))
                color = ArgbEvaluator().evaluate(colorAmount, fadeColor, selectedItemColor)
            }

            if (fadeColor != null) {
                val transparencyAmount = min(1f, distanceToMiddle / (middle + (size / 2)))
                color = ArgbEvaluator().evaluate(transparencyAmount, color ?: fadeColor, Color.TRANSPARENT)
            }

            if (color != null) {
                view.setTextColor(color as Int)
            }
        }
    }

}
