package antuere.data.util

import androidx.annotation.DrawableRes
import antuere.data.R
import antuere.domain.dto.SmileImage


object SmileProvider {

    // Converter image id to SmileImage
    fun getSmileImage(@DrawableRes id: Int): SmileImage {
        return when (id) {
            R.drawable.smile_very_happy -> SmileImage.VERY_HAPPY
            R.drawable.smile_happy -> SmileImage.HAPPY
            R.drawable.smile_low -> SmileImage.LOW
            R.drawable.smile_none -> SmileImage.NONE
            R.drawable.smile_sad -> SmileImage.SAD
            else -> SmileImage.NONE
        }
    }

    // Converter smileName to drawable id
    fun getSmileResId(smileName: String): Int {
        return when (smileName) {
            SmileImage.VERY_HAPPY.name -> R.drawable.smile_very_happy
            SmileImage.HAPPY.name -> R.drawable.smile_happy
            SmileImage.LOW.name -> R.drawable.smile_low
            SmileImage.NONE.name -> R.drawable.smile_none
            SmileImage.SAD.name -> R.drawable.smile_sad
            else -> R.drawable.smile_none
        }
    }

    // Converter SmileImage to drawable id
    fun getSmileResId(smileImage: SmileImage): Int {
        return when (smileImage) {
            SmileImage.VERY_HAPPY -> R.drawable.smile_very_happy
            SmileImage.HAPPY -> R.drawable.smile_happy
            SmileImage.LOW -> R.drawable.smile_low
            SmileImage.NONE -> R.drawable.smile_none
            SmileImage.SAD -> R.drawable.smile_sad
        }
    }

}