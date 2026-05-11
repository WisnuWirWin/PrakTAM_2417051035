package com.example.praktam_2417051035.data.model
import com.google.gson.annotations.SerializedName

data class Activity(
    @SerializedName("nama")
    val nama: String,

    @SerializedName("deskripsi")
    val deskripsi: String,

    @SerializedName("poin")
    val poin: Int,

    @SerializedName("image_url")
    val image_url: String
)
