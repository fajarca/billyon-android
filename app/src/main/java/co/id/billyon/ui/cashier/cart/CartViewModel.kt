package co.id.billyon.ui.cashier.cart

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.id.billyon.base.BaseViewModel
import co.id.billyon.db.entity.CartProducts
import co.id.billyon.repository.cashier.cartproducts.CartProductsRepository
import co.id.billyon.repository.cashier.category.CategoryRepository
import co.id.billyon.util.extensions.plusAssign
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber
import timber.log.Timber
import javax.inject.Inject

class CartViewModel @Inject constructor(private val repository: CartProductsRepository) : BaseViewModel() {

    private val _products = MutableLiveData<List<CartProducts>>()
    val products: LiveData<List<CartProducts>>
        get() = _products

    fun getAllProducts(cartId: Int) {

        if (cartId != -1) {
            getCompositeDisposable() += repository.getProductsOnCartFromDb(cartId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSubscriber<List<CartProducts>>() {
                        override fun onComplete() {
                            Timber.d("Complete")
                        }

                        override fun onNext(t: List<CartProducts>) {
                            _products.value = t
                            Timber.d("Total item ${t.size}")
                        }

                        override fun onError(t: Throwable?) {
                            Timber.d("Error occured")
                        }
                    })
        }

    }
}