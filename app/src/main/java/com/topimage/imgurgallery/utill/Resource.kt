package com.topimage.imgurgallery.utill

import android.webkit.ConsoleMessage

sealed class Resource<T>(val Data: T? = null, val message: String? = null,){
    class Success<T>(data:T?) : Resource<T>(data)
    class Error<T>(message: String?, data : T? = null) : Resource<T>(Data = data, message = message)
    class Loading<T>(val isLoading : Boolean = true) : Resource<T>(Data = null)
}
