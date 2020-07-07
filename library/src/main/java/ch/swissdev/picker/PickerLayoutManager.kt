package ch.swissdev.picker

import android.animation.ArgbEvaluator
import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class PickerLayoutManager(
    context: Context,
    @RecyclerView.Orientation orientation: Int,
    private val isFaded: Boolean
) : LinearLayoutManager(context, orientation, false) {

    private val centerSmoothScroller = CenterSmoothScroller(context)

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
        if (!isFaded) {
            return
        }

        val middle = if (orientation == VERTICAL) {
            height / 2
        } else {
            width / 2
        }

        // TODO: Get this working without TextView
        (0 until childCount).mapNotNull(this::getChildAt).mapNotNull { it as? TextView }.forEach { view ->
            val childMiddle = if (orientation == VERTICAL) {
                (getDecoratedTop(view) + getDecoratedBottom(view)) / 2
            } else {
                (getDecoratedLeft(view) + getDecoratedRight(view)) / 2
            }

            val distanceToMiddle = middle - childMiddle
            val delta = if (orientation == VERTICAL) {
                view.height / 2
            } else {
                view.width / 2
            }

            val transparencyAmount = min(1f, abs(distanceToMiddle).toFloat() / (middle + delta))

            // TODO: Don't use black
            val color = ArgbEvaluator().evaluate(transparencyAmount, Color.BLACK, Color.TRANSPARENT)

            view.setTextColor(color as Int)
        }
    }

}
