package co.id.billyon.ui.cashier.dashboard

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import co.id.billyon.db.entity.Category
import co.id.billyon.db.entity.CategoryWithProducts
import co.id.billyon.db.entity.Products
import co.id.billyon.model.PostsResponse
import co.id.billyon.repository.cashier.dashboard.DashboardRepository
import co.id.billyon.util.extensions.plusAssign
import io.reactivex.Completable
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
    val isInsertCategorySuccessful = MutableLiveData<Boolean>()
    val _categoriesProducts = MutableLiveData<List<CategoryWithProducts>>()

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
                        _categoriesProducts.value = categories

                    }

                    override fun onError(t: Throwable?) {
                        isLoading.set(true)
                    }

                })
    }

    fun insertCategory(category: Category) {
        isInsertCategorySuccessful.value = false
        compositeDisposable += Completable.fromCallable { repository.insertCategory(category) }
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