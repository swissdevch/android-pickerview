package ch.swissdev.picker

import android.content.Context
import android.util.DisplayMetrics
import androidx.recyclerview.widget.LinearSmoothScroller

class CenterSmoothScroller(context: Context) : LinearSmoothScroller(context) {

    override fun calculateDtToFit(viewStart: Int, viewEnd: Int, boxStart: Int, boxEnd: Int, snapPreference: Int): Int {
        return (boxStart + (boxEnd - boxStart) / 2) - (viewStart + (viewEnd - viewStart) / 2)
    }

    override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics?): Float {
        // The default animation speed is way to fast
        return super.calculateSpeedPerPixel(displayMetrics) * 5
    }

}
