package com.topimage.imgurgallery.data.repositories

import com.topimage.imgurgallery.data.network.MyApi
import com.topimage.imgurgallery.data.network.responses.ImageDetails
import com.topimage.imgurgallery.utill.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val api: MyApi
    )
{

     suspend fun getWeekTopImage(searchImageName : String): Flow<Resource<ImageDetails>> {
         return flow {
             try {
                 emit(Resource.Loading(isLoading = true))
                 val result = if(searchImageName.isEmpty()){
                     api.getWeekTopImage()
                 }else{
                     api.getSearchImage(searchImageName)
                 }
                 if(result.data.isNotEmpty()){
                     emit(Resource.Success(result))
                 }else{
                     emit(Resource.Error("Something Wrong With Api"))
                 }
             } catch (e: HttpException) {
                 e.printStackTrace()
                 emit(Resource.Error(message = e.message().toString(), data = null))
             } catch (e: IOException) {
                 e.printStackTrace()
                 emit(Resource.Error(message = e.message.toString(), data = null))
             } catch (e: Exception) {
                 e.printStackTrace()
                 emit(Resource.Error(message = e.message.toString(), data = null))
             }
         }
    }

}