package com.example.taghiveddemoapp.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.TextView

@SuppressLint("AppCompatCustomView")
class MyTextView : TextView {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
      init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        val tf = Typeface.createFromAsset(getContext().getAssets(), "Poppins Regular.otf")
        setTypeface(tf)
    }

}