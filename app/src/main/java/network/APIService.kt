package network

import model.Activity
import retrofit2.http.GET

interface APIService {
    @GET("list_activity.json")
    suspend fun getActivity(): List<Activity>
}