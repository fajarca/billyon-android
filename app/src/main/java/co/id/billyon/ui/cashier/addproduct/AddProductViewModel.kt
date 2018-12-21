package co.id.billyon.ui.cashier.addproduct

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import co.id.billyon.db.entity.Category
import co.id.billyon.db.entity.Products
import co.id.billyon.db.entity.join.CategoryWithProducts
import co.id.billyon.model.request.AddProductRequest
import co.id.billyon.model.request.CategoryRequest
import co.id.billyon.model.response.AddProductResponse
import co.id.billyon.model.response.CategoryResponse
import co.id.billyon.repository.cashier.product.ProductRepository
import co.id.billyon.util.extensions.plusAssign
import co.id.billyon.util.extensions.removeAllThousandSeparator
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber
import javax.inject.Inject

class AddProductViewModel @Inject constructor(private val repository: ProductRepository) : ViewModel() {
    val isLoading = ObservableBoolean()
    val isInsertSuccessful = MutableLiveData<Boolean>()
    val compositeDisposable = CompositeDisposable()

    var productName = ObservableField<String>()
    val errorProductName = ObservableField<String>()

    var displayPrice = ObservableField<String>()
    val errorDisplayPrice = ObservableField<String>()

    var actualPrice = ObservableField<String>()
    val errorActualPrice = ObservableField<String>()

    var initialStock = ObservableField<String>()
    val errorInitialStock = ObservableField<String>()

    var minStock = ObservableField<String>()
    val errorMinStock = ObservableField<String>()

    private val _isAddProductValid = MutableLiveData<Boolean>()
    val isAddProductValid: LiveData<Boolean>
        get() = _isAddProductValid

    fun insertProduct(product: Products) {
        isInsertSuccessful.value = false
        compositeDisposable += Completable.fromCallable { repository.insertProduct(product) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableCompletableObserver() {
                    override fun onComplete() {
                        isInsertSuccessful.value = true

                        val productList = mutableListOf(product)
                        uploadProduct(productList)

                    }

                    override fun onError(e: Throwable) {
                        isInsertSuccessful.value = false
                    }
                })

    }

    fun getAllUnsyncronizedProduct() {
        compositeDisposable += repository.getAllUnsyncronizedProduct()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSubscriber<List<Products>>() {
                    override fun onNext(t: List<Products>) {
                        uploadProduct(t)
                    }

                    override fun onComplete() {
                    }

                    override fun onError(t: Throwable?) {
                    }

                })
    }

    fun uploadProduct(products: List<Products>) {
        isLoading.set(true)

        if (!products.isEmpty()) {

            val productList = arrayListOf<AddProductRequest>()
            for (i in products) {
                val product = AddProductRequest(i.storeId, i.categoryId, i.imagePath, i.name, i.stock, i.minStock, i.displayPrice, i.actualPrice)
                productList.add(product)
            }

            compositeDisposable += repository.uploadProduct(productList)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableObserver<AddProductResponse>() {
                        override fun onComplete() {
                            isLoading.set(false)
                        }

                        override fun onNext(data: AddProductResponse) {
                            isLoading.set(false)
                        }

                        override fun onError(e: Throwable) {
                            isLoading.set(false)
                        }


                    })
        }

    }

    fun deleteProduct(product: Products) {
        compositeDisposable += Completable.fromCallable { repository.delete(product) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }

    fun deleteAllProduct() {
        compositeDisposable += Completable.fromCallable { repository.deleteAll() }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { }
    }

    fun validateAddProduct() {

        if (!isValidProductName()) {
            _isAddProductValid.value = false
            return
        }

        if (!isValidDisplayPrice()) {
            _isAddProductValid.value = false
            return
        }

        if (!isValidActualPrice()) {
            _isAddProductValid.value = false
            return
        }

        if (!isValidInitialStock()) {
            _isAddProductValid.value = false
            return
        }

        if (!isValidMinimalStock()) {
            _isAddProductValid.value = false
            return
        }

        _isAddProductValid.value = true

    }

    private fun isValidProductName(): Boolean {
        if (productName.get().isNullOrEmpty()) {
            errorProductName.set("Product name can't be empty")
            return false
        } else {
            errorProductName.set(null)
        }
        return true
    }

    private fun isValidDisplayPrice(): Boolean {
        if (displayPrice.get().isNullOrEmpty()) {
            errorDisplayPrice.set("Display price can't be empty")
            return false
        } else {
            errorDisplayPrice.set(null)
        }
        return true
    }

    /**
     * Display price cannot be bigger than actual price
     */
    private fun isValidActualPrice(): Boolean {
        if (actualPrice.get().isNullOrEmpty()) {
            errorActualPrice.set("Actual price can't be empty")
            return false
        } else if (displayPrice.get().toString().trim().removeAllThousandSeparator() < actualPrice.get().toString().trim().removeAllThousandSeparator()) {
            errorActualPrice.set("Display price should be bigger than actual price")
            return false
        } else {
            errorActualPrice.set(null)
        }
        return true
    }


    private fun isValidInitialStock(): Boolean {
        if (initialStock.get().isNullOrEmpty()) {
            errorInitialStock.set("Initial stock can't be empty")
            return false
        } else {
            errorInitialStock.set(null)
        }
        return true
    }

    private fun isValidMinimalStock(): Boolean {
        if (minStock.get().isNullOrEmpty()) {
            errorMinStock.set("Minimal stock can't be empty")
            return false
        } else {
            errorMinStock.set(null)
        }
        return true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}