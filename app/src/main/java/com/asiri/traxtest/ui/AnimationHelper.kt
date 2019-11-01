package com.asiri.traxtest.ui

import android.app.Activity
import com.asiri.traxtest.R

object AnimationHelper {
    fun animationLeft(activity: Activity) {
        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    fun animationRight(activity: Activity) {
        activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

}