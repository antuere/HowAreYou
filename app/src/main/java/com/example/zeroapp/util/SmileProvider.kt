package com.example.zeroapp.util

import com.example.zeroapp.R

object SmileProvider {

    const val SMILE_VERY_HAPPY = "very happy"
    const val SMILE_HAPPY = "happy"
    const val SMILE_NONE = "none"
    const val SMILE_LOW = "low"
    const val SMILE_SAD = "sad"


    // Converter buttonId to drawable SmileId
    private fun getSmileImage(id: Int): Int {
        return when (id) {
            R.id.b_very_happy -> R.drawable.smile_very_happy
            R.id.b_happySmile -> R.drawable.smile_happy
            R.id.b_none -> R.drawable.smile_none
            R.id.b_smile_low -> R.drawable.smile_low
            R.id.b_sad -> R.drawable.smile_sad
            else -> R.drawable.smile_none
        }
    }

    private fun getSmileNameById(id: Int): String {
        return when (id) {
            R.drawable.smile_very_happy -> SMILE_VERY_HAPPY
            R.drawable.smile_happy -> SMILE_HAPPY
            R.drawable.smile_none -> SMILE_NONE
            R.drawable.smile_low -> SMILE_LOW
            R.drawable.smile_sad -> SMILE_SAD
            else -> SMILE_NONE
        }
    }

    fun getSmileNameFromBtnId(id: Int): String {
        val resId = getSmileImage(id)
        return getSmileNameById(resId)
    }

    fun getSmileImageByName(name: String): Int {
        return when (name) {
            SMILE_VERY_HAPPY -> R.drawable.smile_very_happy
            SMILE_HAPPY -> R.drawable.smile_happy
            SMILE_NONE -> R.drawable.smile_none
            SMILE_LOW -> R.drawable.smile_low
            SMILE_SAD -> R.drawable.smile_sad
            else -> R.drawable.smile_none
        }
    }

}