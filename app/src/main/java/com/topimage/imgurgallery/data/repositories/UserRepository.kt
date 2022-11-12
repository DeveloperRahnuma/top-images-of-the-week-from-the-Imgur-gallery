package com.topimage.imgurgallery.data.repositories

import android.os.Build
import androidx.annotation.RequiresApi
import com.topimage.imgurgallery.data.network.MyApi
import com.topimage.imgurgallery.data.network.responses.ImageDetails
import com.topimage.imgurgallery.utill.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import javax.inject.Inject

private const val MINIMUM_INTERVAL = 1

class UserRepository @Inject constructor(
    private val api: MyApi
    )
{

     suspend fun getWeekTopImage(searchImageName : String): Flow<Resource<ImageDetails>> {
         return flow {
             try {
                 emit(Resource.Loading(isLoading = true))
                 var result : ImageDetails? = null
                 if(searchImageName.isEmpty()){
                     result = api.getWeekTopImage()
                 }else{
                      result = api.getSearchImage(searchImageName)
                 }
                 if(result.data.isNotEmpty()){
                     emit(Resource.Success(result))
                 }else{
                     emit(Resource.Error("Something Wrong With Api"))
                 }
             } catch (e: HttpException) {
                 e.printStackTrace()
                 emit(Resource.Error(message = e.message().toString(), data = null))
                 null
             } catch (e: IOException) {
                 e.printStackTrace()
                 emit(Resource.Error(message = e.message.toString(), data = null))
                 null
             } catch (e: Exception) {
                 e.printStackTrace()
                 emit(Resource.Error(message = e.message.toString(), data = null))
                 null
             }
         }
    }

}