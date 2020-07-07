package ch.swissdev.picker

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class LayoutManager(
    context: Context,
    @RecyclerView.Orientation orientation: Int
) : LinearLayoutManager(context, orientation, false) {

    private val centerSmoothScroller = CenterSmoothScroller(context)

    override fun smoothScrollToPosition(recyclerView: RecyclerView?, state: RecyclerView.State?, position: Int) {
        centerSmoothScroller.targetPosition = position
        startSmoothScroll(centerSmoothScroller)
    }

}
