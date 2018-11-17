package co.id.billyon.ui.cashier.addproduct

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import co.id.billyon.db.entity.Products
import co.id.billyon.repository.ProductRepository
import co.id.billyon.util.extensions.plusAssign
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AddProductViewModel @Inject constructor(private val repository: ProductRepository) : ViewModel() {
    val isInsertSuccessful = MutableLiveData<Boolean>()
    val compositeDisposable = CompositeDisposable()

    fun insertProduct(product: Products) {
        isInsertSuccessful.value = false
        compositeDisposable += Completable.fromCallable { repository.insertProduct(product) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableCompletableObserver(){
                    override fun onComplete() {
                        isInsertSuccessful.value = true
                    }

                    override fun onError(e: Throwable) {
                        isInsertSuccessful.value = false
                    }
                })

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
                .subscribe {  }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}