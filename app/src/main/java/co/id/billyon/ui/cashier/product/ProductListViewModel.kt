package co.id.billyon.ui.cashier.product

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.databinding.ObservableLong
import co.id.billyon.db.entity.Carts
import co.id.billyon.db.entity.join.CartsAndCartProducts
import co.id.billyon.db.entity.Products
import co.id.billyon.repository.cashier.carts.CartsRepository
import co.id.billyon.repository.cashier.product.ProductRepository
import co.id.billyon.util.extensions.plusAssign
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber
import javax.inject.Inject

class ProductListViewModel @Inject constructor(private val productRepo: ProductRepository, private val cartRepo : CartsRepository): ViewModel() {
    val isLoading = ObservableField<Boolean>()
    val compositeDisposable = CompositeDisposable()
    val products = MutableLiveData<List<Products>>()
    var itemCount = ObservableField<String>()
    var totalPrice = ObservableLong()
    var showAddToCart = ObservableBoolean()
    var showQtyPicker = ObservableBoolean()

    fun findProduct(categoryId : Int) {
        isLoading.set(true)
        compositeDisposable += productRepo.findProductFromCategory(categoryId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSubscriber<List<Products>>() {
                    override fun onComplete() {
                       isLoading.set(false)
                    }

                    override fun onNext(data: List<Products>?) {
                        isLoading.set(false)
                        products.value = data
                    }

                    override fun onError(t: Throwable?) {
                        isLoading.set(false)
                    }

                })
    }

    fun findAllCart(isFinished : Boolean) {
        compositeDisposable += cartRepo.findAll(isFinished)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object :DisposableSubscriber<List<CartsAndCartProducts>>() {
                    override fun onComplete() {
                        isLoading.set(false)
                    }

                    override fun onNext(data: List<CartsAndCartProducts>?) {
                        isLoading.set(false)
                        itemCount.set("${data?.size} items")
                        totalPrice.set(4000)
                        //products.value = data
                    }

                    override fun onError(t: Throwable?) {
                        isLoading.set(false)
                    }

                })


    }

    fun addToCart(cart : Carts) {
        compositeDisposable += Completable.fromCallable { cartRepo.insertCart(cart) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableCompletableObserver() {
                    override fun onComplete() {
                        showAddToCart.set(false)
                        showQtyPicker.set(true)
                    }

                    override fun onError(e: Throwable) {

                    }
                })
    }

    fun deleteFromCart(cart: Carts) {
        compositeDisposable += Completable.fromCallable { cartRepo.delete(cart) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableCompletableObserver() {
                    override fun onComplete() {

                    }

                    override fun onError(e: Throwable) {

                    }
                })
    }

    /*fun updateQuatity(productId : Long, quantity : Int) {
        compositeDisposable += Completable.fromCallable { cartRepo.updateQuantity(productId, quantity) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableCompletableObserver() {
                    override fun onComplete() {

                    }

                    override fun onError(e: Throwable) {

                    }
                })
    }*/

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}