package com.example.praktam_2417051035.model
import android.content.Context
import android.media.Image
import androidx.compose.ui.tooling.data.SourceContext
import com.example.praktam_2417051035.R
import model.Activity

object ActivitySource {
    fun getResourceId(context: Context, imageName: String): Int{
        return context.resources.getIdentifier(imageName,"drawable",context.packageName)
    }
}