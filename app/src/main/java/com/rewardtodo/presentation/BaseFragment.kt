package com.rewardtodo.presentation

import android.view.MotionEvent
import androidx.fragment.app.Fragment

open class BaseFragment: Fragment() {
    open fun dispatchTouchEvent(ev: MotionEvent?) {}
}