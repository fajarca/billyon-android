package co.id.billyon.ui.cashierdashboard

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import android.util.Log
import co.id.billyon.db.entity.Products
import co.id.billyon.model.PostsResponse
import co.id.billyon.repository.ProductRepository
import co.id.billyon.util.extensions.plusAssign
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableObserver
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber
import javax.inject.Inject

class CashierDashboardViewModel @Inject constructor(private val repository: ProductRepository) : ViewModel() {
    val isLoading = ObservableField<Boolean>()
    val data = MutableLiveData<List<PostsResponse>>()
    val productsData = MutableLiveData<List<Products>>()
    val compositeDisposable = CompositeDisposable()

    fun loadAllProducts() {
        compositeDisposable += repository.getAllProduct()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSubscriber<List<Products>>() {
                    override fun onComplete() {

                    }

                    override fun onNext(t: List<Products>?) {
                        productsData.value = t
                    }

                    override fun onError(t: Throwable?) {

                    }


                })
    }

    fun loadPosts() {
        isLoading.set(true)
        compositeDisposable += repository.getAllPost()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<List<PostsResponse>>() {
                    override fun onComplete() {
                        isLoading.set(false)
                    }

                    override fun onNext(t: List<PostsResponse>) {
                        val list = t
                        data.value = t
                        Log.v("tag", list.size.toString())
                        isLoading.set(false)
                    }

                    override fun onError(e: Throwable) {
                        isLoading.set(false)
                    }

                })
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}