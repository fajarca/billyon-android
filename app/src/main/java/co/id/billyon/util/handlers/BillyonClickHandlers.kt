package co.id.billyon.util.handlers

import android.util.Log
import android.view.View

interface BillyonClickHandlers {
    interface Dashboard {
        fun onFabAddProductPressed(view : View)
    }
    interface AddProduct {
        fun onButtonSaveProductPressed(view : View)
    }
    interface ProductList {

    }

}