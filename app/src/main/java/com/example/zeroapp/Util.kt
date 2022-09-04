package com.example.zeroapp


fun getSmileImage(id: Int): Int {
    return when (id) {
        R.id.b_very_happy -> R.drawable.smile_very_happy
        R.id.b_happySmile -> R.drawable.smile_happy
        R.id.b_none -> R.drawable.smile_none
        R.id.b_smile_low -> R.drawable.smile_low
        R.id.b_sad -> R.drawable.smile_sad
        else -> R.drawable.smile_none
    }
}