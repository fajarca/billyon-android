package co.id.billyon.ui.cashier.product

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import co.id.billyon.db.entity.CategoryWithProducts
import co.id.billyon.db.entity.Products
import co.id.billyon.repository.cashier.product.ProductRepository
import co.id.billyon.util.extensions.plusAssign
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber
import javax.inject.Inject

class ProductListViewModel @Inject constructor(private val repository: ProductRepository): ViewModel() {
    val isLoading = ObservableField<Boolean>()
    val compositeDisposable = CompositeDisposable()
    val products = MutableLiveData<List<Products>>()

    fun findProduct(categoryId : Int) {
        isLoading.set(true)
        compositeDisposable += repository.findProductFromCategory(categoryId)
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
               /* .subscribeWith(object : DisposableSubscriber<List<CategoryWithProducts>>() {
                    override fun onComplete() {
                        isLoading.set(false)
                    }

                    override fun onNext(categories: List<CategoryWithProducts>?) {
                        isLoading.set(false)
                        _categoriesProducts.value = categories

                    }

                    override fun onError(t: Throwable?) {
                        isLoading.set(true)
                    }

                })*/
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}