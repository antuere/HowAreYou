package antuere.domain.remote_db

interface RemoteDbApi {

    fun isHasUser() : Boolean

    fun isNetworkAvailable() : Boolean

    fun getDaysNode()

}