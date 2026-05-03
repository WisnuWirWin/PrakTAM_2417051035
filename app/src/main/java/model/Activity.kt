package model
import com.google.gson.annotations.SerializedName
import androidx.annotation.DrawableRes

data class Activity(
    @SerializedName("nama")
    val nama: String,

    @SerializedName("deskripsi")
    val deskripsi: String,

    @SerializedName("poin")
    val poin: Int,

    @SerializedName("imageUrl")
    val iamge_name: String
)
