package com.example.praktam_2417051035.data.repository

import com.example.praktam_2417051035.data.api.RetrofitClient
import com.example.praktam_2417051035.data.model.Activity

class ActivityRepository {
    suspend fun getActivity() : List<Activity> {
        return try {
            RetrofitClient.instance.getActivity()
        } catch (e : Exception){
            emptyList()
        }
    }
}