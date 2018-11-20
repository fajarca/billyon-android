package co.id.billyon.util

import android.databinding.BindingAdapter
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import java.io.File

@BindingAdapter("app:loadImage")
fun loadImage(view: ImageView, imagePath:String) {
    val file = File(imagePath)
    val uri = Uri.fromFile(file)
    Glide.with(view.context).load(uri).into(view)
}