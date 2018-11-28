package co.id.billyon.util

import android.databinding.BindingAdapter
import android.net.Uri
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.TextWatcher
import android.widget.ImageView
import co.id.billyon.R
import com.bumptech.glide.Glide
import java.io.File

@BindingAdapter("loadImage")
fun loadImage(view: ImageView, imagePath: String) {
    val file = File(imagePath)
    val uri = Uri.fromFile(file)
    Glide.with(view.context).load(uri).into(view)
}

@BindingAdapter("minLength")
fun minLength(view: TextInputEditText, minLength: Int) {
    if (minLength > 0) {
        view.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(sequence: CharSequence, start: Int, before: Int, count: Int) {
                if (sequence.length < minLength) {
                    view.error = "${view.hint} tidak boleh kurang dari $minLength karakter"
                } else {
                    view.error = null
                }
            }

        })
    }

}

@BindingAdapter("required")
fun shouldShowErrorMessages(view: TextInputLayout, required: Boolean) {
    if (required) {
        view.isErrorEnabled = true
        view.error = view.context.getString(R.string.err_msg_field_empty_)
    } else {
        view.isErrorEnabled = false
        view.error = null
    }
}

@BindingAdapter("error")
fun shouldShowError(view: TextInputLayout, errorMessage: String?) {
    view.error = errorMessage
}



