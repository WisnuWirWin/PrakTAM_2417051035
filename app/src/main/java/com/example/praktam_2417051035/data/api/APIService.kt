package com.example.praktam_2417051035.data.api

import com.example.praktam_2417051035.data.model.Activity
import retrofit2.http.GET

interface APIService {
    @GET("list_activity.json")
    suspend fun getActivity(): List<Activity>
}