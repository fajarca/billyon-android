package co.id.billyon.ui.cashier.product

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.databinding.ObservableLong
import android.util.Log
import co.id.billyon.db.entity.CartProducts
import co.id.billyon.db.entity.Carts
import co.id.billyon.db.entity.Products
import co.id.billyon.db.entity.join.CartsAndCartProducts
import co.id.billyon.repository.cashier.carts.CartsRepository
import co.id.billyon.repository.cashier.product.ProductRepository
import co.id.billyon.util.Utils
import co.id.billyon.util.extensions.plusAssign
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableMaybeObserver
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber
import javax.inject.Inject

class ProductListViewModel @Inject constructor(private val productRepo: ProductRepository, private val cartRepo: CartsRepository) : ViewModel() {
    val isLoading = ObservableField<Boolean>()
    val compositeDisposable = CompositeDisposable()
    val products = MutableLiveData<List<Products>>()
    var itemCount = ObservableField<String>()
    var totalPrice = ObservableLong()
    var showAddToCart = ObservableBoolean()
    var showQtyPicker = ObservableBoolean()
    var TAG = ProductListViewModel::class.java.simpleName

    fun findActiveCartId(isFinished: Boolean, productId: Long, storeId: Long, quantity: Int) {
        compositeDisposable += cartRepo.findActiveCartId(isFinished)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess {
                    //Add product to active cart
                    val product = CartProducts(productId, storeId, it, quantity, true, Utils.getCurrentTimeStamp())
                    addProductToCart(product)
                }
                .doOnError {
                    Log.v(TAG, "FindAciveCartId error ${it.message}")
                }
                .doOnComplete {
                    //Active cart not found. Create a new one
                    val cart = Carts(1, false, true, Utils.getCurrentTimeStamp(), Utils.getCurrentTimeStamp())
                    createCart(cart, productId, storeId, quantity)

                    Log.v(TAG, "FindAciveCartId complete (not found)")
                }
                .subscribe()
    }

    fun findAllCart(isFinished: Boolean) {
        compositeDisposable += cartRepo.findAll(isFinished)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSubscriber<List<CartsAndCartProducts>>() {
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

    fun createCart(cart: Carts, productId: Long, storeId: Long, quantity: Int) {
        compositeDisposable += Single.fromCallable { cartRepo.createCart(cart) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess {
                    //Add the product into the cart
                    val product = CartProducts(productId, storeId, it, quantity, true, Utils.getCurrentTimeStamp())
                    addProductToCart(product)
                }
                .doOnError {
                    Log.e(TAG, "Error add product to cart")
                }
                .subscribe()
    }

    fun deleteCart(cart: Carts) {
        compositeDisposable += Completable.fromCallable { cartRepo.deleteCart(cart) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableCompletableObserver() {
                    override fun onComplete() {

                    }

                    override fun onError(e: Throwable) {

                    }
                })
    }


    fun findProduct(categoryId: Int) {
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

    fun addProductToCart(cartProducts: CartProducts) {
        compositeDisposable += Completable.fromCallable { cartRepo.addProductToCart(cartProducts) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableCompletableObserver() {
                    override fun onComplete() {

                    }

                    override fun onError(e: Throwable) {

                    }
                })
    }

    fun updateQuantity(productId: Long, quantity: Int) {
        compositeDisposable += Completable.fromCallable { cartRepo.updateProductQuantity(productId, quantity) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableCompletableObserver() {
                    override fun onComplete() {

                    }

                    override fun onError(e: Throwable) {

                    }
                })
    }

    /*fun addToCart(cashierId: Long, productId: Long, storeId: Long, quantity: Int) {
        val activeCartId = findActiveCartId(false)
        //No active cart found, create a new cart before adding new product
        if (activeCartId == -1L) {

            //Create new cart
            val cart = Carts(cashierId, false, true, Utils.getCurrentTimeStamp(), Utils.getCurrentTimeStamp())
            val cartId = createCart(cart)

            //Add the product to the cart
            val product = CartProducts(productId, storeId, cartId, quantity, true, Utils.getCurrentTimeStamp())
            addProductToCart(product)

        } else {
            //Add the product to the existing cart
            val product = CartProducts(productId, storeId, activeCartId, quantity, true, Utils.getCurrentTimeStamp())
            addProductToCart(product)
        }

    }*/

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}