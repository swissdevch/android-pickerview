package ch.swissdev.picker

import android.content.Context
import android.util.DisplayMetrics
import androidx.recyclerview.widget.LinearSmoothScroller

class CenterSmoothScroller(context: Context) : LinearSmoothScroller(context) {

    override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics?): Float {
        // The default animation speed is way to fast
        return super.calculateSpeedPerPixel(displayMetrics) * 5
    }

}
