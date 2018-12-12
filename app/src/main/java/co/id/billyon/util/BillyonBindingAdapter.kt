package co.id.billyon.util

import android.databinding.BindingAdapter
import android.net.Uri
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ImageView
import co.id.billyon.R
import com.bumptech.glide.Glide
import java.io.File
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*
import android.opengl.ETC1.getHeight
import android.view.animation.TranslateAnimation
import android.opengl.ETC1.getHeight





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

@BindingAdapter("addThousandSeparator")
fun addThousandSeparator(view : TextInputEditText, price : Long) {
    view.addTextChangedListener(object : TextWatcher {

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }
        override fun afterTextChanged(s: Editable?) {

            view.removeTextChangedListener(this)
            try {

                var originalString : String = s.toString()

                //Remove comma
                if (originalString.contains(".")) {
                    originalString = originalString.replace(".", "")
                }

                var longValue = originalString.toLong()

                val formatter = NumberFormat.getInstance(Locale.GERMAN) as DecimalFormat
                formatter.applyPattern("#,###,###,###")

                val formattedString : String = formatter.format(longValue)


                view.setText(formattedString)
                view.setSelection(view.text!!.length)


            } catch (e : NumberFormatException) {
                e.printStackTrace()
            }

            view.addTextChangedListener(this)
        }

    })
}

@BindingAdapter("shouldShow")
fun shouldShowView(view : View, shouldShow : Boolean) {
    if (shouldShow) {
        val animate = TranslateAnimation(0f, 0f, 0f, 1f)
        animate.duration = 700
        animate.fillAfter = true
        view.startAnimation(animate)
        view.visibility = View.VISIBLE
    } else {
        val animate = TranslateAnimation(0f, 0f, 0f, view.height.toFloat())
        animate.duration = 500
        animate.fillAfter = true
        view.startAnimation(animate)
        view.visibility = View.INVISIBLE
    }
}



