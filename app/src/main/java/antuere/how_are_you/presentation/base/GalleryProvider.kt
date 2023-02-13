package antuere.how_are_you.presentation.base

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import com.skydoves.landscapist.glide.GlideImageState
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

object GalleryProvider {

    fun saveImage(bitmap: Bitmap, context: Context, onSuccess: () -> Unit) {
        val filename = "Cat-${System.currentTimeMillis()}.jpg"
        var fos: OutputStream? = null

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            context.contentResolver?.also { resolver ->
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }

                val imageUri: Uri? =
                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

                fos = imageUri?.let { resolver.openOutputStream(it) }
            }
        } else {
            val imagesDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            if (!imagesDir.exists()) {
                imagesDir.mkdirs()
            }
            val image = File(imagesDir, filename)
            fos = FileOutputStream(image)
        }

        fos?.use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
            Timber.i("CATS ERROR : WE IN catsScreenState, is before onSuccess in provider")
            onSuccess()
            Timber.i("CATS ERROR : WE IN catsScreenState, is after onSuccess in provider")
        }
    }
}