package co.id.billyon.ui.addproduct

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import co.id.billyon.db.entity.Products
import co.id.billyon.model.PostsResponse
import co.id.billyon.repository.ProductRepository
import co.id.billyon.util.extensions.plusAssign
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AddProductViewModel @Inject constructor(private val repository: ProductRepository) : ViewModel() {
    val isLoading = ObservableField<Boolean>()
    val data = MutableLiveData<List<PostsResponse>>()
    val productsData = MutableLiveData<List<Products>>()
    val compositeDisposable = CompositeDisposable()

    fun insertProduct(product: Products) {
        isLoading.set(true)
        compositeDisposable += Completable.fromCallable { repository.insertProduct(product) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { isLoading.set(false) }
    }

    fun deleteProduct(product: Products) {
        compositeDisposable += Completable.fromCallable { repository.delete(product) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }

    fun deleteAllProduct() {
        isLoading.set(true)
        compositeDisposable += Completable.fromCallable { repository.deleteAll() }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { isLoading.set(false) }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}