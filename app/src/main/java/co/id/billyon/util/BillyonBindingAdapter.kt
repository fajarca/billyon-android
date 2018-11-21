package co.id.billyon.util

import android.databinding.BindingAdapter
import android.net.Uri
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.text.Editable
import android.text.TextWatcher
import android.widget.ImageView
import com.bumptech.glide.Glide
import java.io.File

@BindingAdapter("app:loadImage")
fun loadImage(view: ImageView, imagePath:String) {
    val file = File(imagePath)
    val uri = Uri.fromFile(file)
    Glide.with(view.context).load(uri).into(view)
}

@BindingAdapter("app:minLength")
fun minLength(view : TextInputEditText, minLength : Int) {
    if (minLength > 0) {
        view.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(sequence: CharSequence, start: Int, before: Int, count: Int) {
                if (sequence.length < minLength) {
                    view.error = "Tidak boleh kurang dari $minLength karakter"
                } else {
                    view.error = null
                }
            }

        })
    }

}

/*@BindingAdapter("app:required","app:minLength",requireAll = true)
fun isFieldRequired(view : TextInputEditText, isRequired : Boolean, minLength: Int) {
    if (isRequired){

        view.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(sequence: CharSequence, start: Int, before: Int, count: Int) {
                if (sequence.length < minLength) {
                    view.error = "Tidak boleh kurang dari $minLength karakter"
                } else {
                    view.error = null
                }
            }

        })

    }
}

@BindingAdapter("app:errorMessage")
fun errorMessage(view : TextInputLayout, errorMessage : String) {
    view.error = errorMessage
}*/
