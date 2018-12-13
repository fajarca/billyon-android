package co.id.billyon.ui.cashier.dashboard

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import co.id.billyon.db.entity.Category
import co.id.billyon.db.entity.join.CategoryAndProducts
import co.id.billyon.db.entity.join.CategoryWithProducts
import co.id.billyon.model.PostsResponse
import co.id.billyon.repository.cashier.dashboard.DashboardRepository
import co.id.billyon.util.extensions.plusAssign
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber
import javax.inject.Inject

class DashboardViewModel @Inject constructor(private val repository: DashboardRepository) : ViewModel() {
    val isLoading = ObservableField<Boolean>()
    val data = MutableLiveData<List<PostsResponse>>()
    val compositeDisposable = CompositeDisposable()
    val addCategoryMessage = MutableLiveData<String>()
    private val _categories = MutableLiveData<List<CategoryWithProducts>>()
    private val _productCountOnCart = MutableLiveData<Int>()

    val categories : LiveData<List<CategoryWithProducts>>
        get() = _categories
    val productCountOnCart: LiveData<Int>
        get() = _productCountOnCart

    /*fun loadAllProducts() {
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
                        data.value = t
                        isLoading.set(false)
                    }

                    override fun onError(e: Throwable) {
                        isLoading.set(false)
                    }

                })
    }

    fun getAllCategory() {
        compositeDisposable += repository.getAllCategories()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableMaybeObserver<List<Category>>() {
                    override fun onSuccess(categories: List<Category>) {
                        if (categories.isEmpty()) {
                            categoryEmpty.set(true)
                        } else {
                            categoryEmpty.set(false)
                        }
                        _categories.value = categories
                        Log.v("tagg", "onSuccess, ${categories.size}")
                    }

                    override fun onComplete() {
                        Log.v("tagg", "onComplete, no user found")
                    }

                    override fun onError(e: Throwable) {
                        Log.v("tagg", "onError, ${e.message}")
                    }


                })
    }*/

    fun getAllCategories() {
        isLoading.set(true)
        compositeDisposable += repository.getAllCategoriesWithProductCount()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSubscriber<List<CategoryWithProducts>>() {
                    override fun onComplete() {
                        isLoading.set(false)
                    }

                    override fun onNext(categories: List<CategoryWithProducts>?) {
                        isLoading.set(false)
                        _categories.value = categories

                    }

                    override fun onError(t: Throwable?) {
                        isLoading.set(true)
                    }

                })
    }

    fun getProductCountOnCart() {
        isLoading.set(true)
        compositeDisposable += repository.getProductCountOnCart()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    isLoading.set(false)
                    it?.let {
                        _productCountOnCart.value = it
                    }

                }
                .doOnComplete {
                    isLoading.set(false)
                }
                .doOnError {
                    isLoading.set(false)
                }
                .subscribe()
    }

    fun insertCategory(category: Category) {
        compositeDisposable += Completable.fromCallable { repository.insertCategory(category) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableCompletableObserver() {
                    override fun onComplete() {
                    }

                    override fun onError(e: Throwable) {
                        addCategoryMessage.value = "Unable to add new category ${e.message}"
                    }
                })
    }

    fun deleteCategory(categoryId: Long) {
        compositeDisposable += Completable.fromCallable { repository.deleteCategory(categoryId) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableCompletableObserver() {
                    override fun onComplete() {
                    }

                    override fun onError(e: Throwable) {
                        addCategoryMessage.value = "Unable to delete category ${e.message}"
                    }
                })
    }

    /*fun insertAllCategories(categories: List<Category>) {
        isInsertCategorySuccessful.value = false
        compositeDisposable += Completable.fromCallable { repository.insertAllCategories(categories) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableCompletableObserver() {
                    override fun onComplete() {
                        isInsertCategorySuccessful.value = true
                    }

                    override fun onError(e: Throwable) {
                        isInsertCategorySuccessful.value = false
                    }
                })

    }*/


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}