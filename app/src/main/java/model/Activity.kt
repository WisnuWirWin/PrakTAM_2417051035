package model
import androidx.annotation.DrawableRes

data class Activity(
    val nama: String,
    val deskripsi: String,
    val poin: Int,
    @DrawableRes val imageRes: Int
)
