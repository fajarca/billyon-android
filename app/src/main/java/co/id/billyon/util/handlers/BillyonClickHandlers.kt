package co.id.billyon.util.handlers

import android.view.View

interface BillyonClickHandlers {
    interface AddProduct {
        fun onButtonSaveProductPressed(view : View)
    }
    interface ProductList {
        fun onFabAddProductPressed(view : View)
    }

}