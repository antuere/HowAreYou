package antuere.data.util

import com.google.android.gms.common.util.Base64Utils

fun String.encodeByBase64() : String {
    return Base64Utils.encode(this.toByteArray())
}

fun String.decodeByBase64() : String {
    return String(Base64Utils.decode(this))
}
