package co.id.billyon.ui.cashierdashboard

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import android.util.Log
import co.id.billyon.db.entity.Products
import co.id.billyon.model.PostsResponse
import co.id.billyon.repository.ProductRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber
import javax.inject.Inject

class CashierDashboardViewModel @Inject constructor(private val repository: ProductRepository) : ViewModel() {
    val isLoading = ObservableField<Boolean>()
    val data = MutableLiveData<List<PostsResponse>>()
    val productsData = MutableLiveData<List<Products>>()

    fun loadAllProducts() {
        repository.getAllProduct()
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
    fun insertProduct(product: Products) = repository.insert(product)
    fun loadPosts() {
        isLoading.set(true)
        repository.getAllPost()
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
}