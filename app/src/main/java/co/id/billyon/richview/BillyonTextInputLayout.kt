package co.id.billyon.richview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import co.id.billyon.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.custom_text_input_layout.view.*

class BillyonTextInputLayout @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0,
        defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {


    init {
        LayoutInflater.from(context).inflate(R.layout.custom_text_input_layout, this, true)
        orientation = VERTICAL

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.billyon_text_input_layout_attributes, 0, 0)

            val textHint = resources.getText(typedArray.getResourceId(R.styleable.billyon_text_input_layout_attributes_textHint, R.string.default_hint))
            val textErrorMessage = resources.getText(typedArray.getResourceId(R.styleable.billyon_text_input_layout_attributes_textErrorMessage,R.string.err_msg_field_empty)).toString()
            val textMinLength = typedArray.getInt(R.styleable.billyon_text_input_layout_attributes_textMinLength, -1)

            textInputLayout.hint = textHint
            textInputEditText.addTextChangedListener(BillyonTextWatcher(textErrorMessage,textMinLength,textInputLayout,textInputEditText))

            typedArray.recycle()
        }
    }

    class BillyonTextWatcher(val errorMsg : String, val minLength : Int, val textInputLayout: TextInputLayout, val textInputEditText: TextInputEditText) : TextWatcher {


        override fun afterTextChanged(p0: Editable?) {

        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            validateTextLength(errorMsg, minLength)
        }

        fun validateTextLength(errorMsg : String, minLength: Int) {
            val inputTextLength = textInputEditText.text.toString().trim().length

            if (inputTextLength < minLength ){
                textInputLayout.error = errorMsg
            } else {
                textInputLayout.isErrorEnabled = false
            }
        }
    }

    fun getText() : String = textInputEditText.toString().trim()


}