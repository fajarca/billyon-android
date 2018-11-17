package co.id.billyon.ui.handlers

import android.util.Log
import android.view.View

interface BillyonHandlers {
    interface Dashboard {
        fun onFabAddProductPressed(view : View)
    }
    interface AddProduct {
        fun onButtonSaveProductPressed(view : View)
    }

}