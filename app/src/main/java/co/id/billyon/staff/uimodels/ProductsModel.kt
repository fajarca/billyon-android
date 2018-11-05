package co.id.billyon.staff.uimodels

import java.util.logging.Handler

class ProductsModel {

    fun getProducts(callback: onProductsReadyCallback)  {
        var products = arrayListOf<Product>()
        products.add(Product(1,1,"img/prof",1,"Kopi Susu Keluarga",1,100,12000,8000,10000,10000,1))
        products.add(Product(1,1,"img/prof",1,"Kopi Susu Keluarga",1,100,12000,8000,10000,10000,1))
        products.add(Product(1,1,"img/prof",1,"Kopi Susu Keluarga",1,100,12000,8000,10000,10000,1))
        products.add(Product(1,1,"img/prof",1,"Kopi Susu Keluarga",1,100,12000,8000,10000,10000,1))

        callback.onDataReady(products)
    }

    interface onProductsReadyCallback {
        fun onDataReady(data : ArrayList<Product>)
    }
}