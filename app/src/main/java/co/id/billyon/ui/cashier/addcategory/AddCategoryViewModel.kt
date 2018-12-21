package co.id.billyon.ui.cashier.addcategory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import co.id.billyon.db.entity.Category
import co.id.billyon.model.request.CategoryRequest
import co.id.billyon.model.response.CategoryResponse
import co.id.billyon.repository.cashier.category.CategoryRepository
import co.id.billyon.util.extensions.default
import co.id.billyon.util.extensions.plusAssign
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AddCategoryViewModel @Inject constructor(private val repository: CategoryRepository) : ViewModel() {

    var name = ObservableField<String>()
    var errorName = ObservableField<String>()
    var isLoading = ObservableBoolean()

    private val compositeDisposable = CompositeDisposable()

    private val _isValid = MutableLiveData<Boolean>().default(false)
    private val _data = MutableLiveData<CategoryResponse>()

    val isValid: LiveData<Boolean>
        get() = _isValid
    val data : LiveData<CategoryResponse>
        get() = _data


    fun insertCategory(category: Category) {
        compositeDisposable += Single.fromCallable { repository.insertCategory(category) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { id ->
                    uploadCategory(category)
                }
    }

    fun uploadCategory(category: Category) {
        isLoading.set(true)
        val request = CategoryRequest(category.categoryName, category.categoryImagePath, category.storeId)
        compositeDisposable += repository.uploadCategory(request)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<CategoryResponse>() {
                    override fun onComplete() {
                        isLoading.set(false)
                    }

                    override fun onNext(data: CategoryResponse) {
                       _data.value = data
                        isLoading.set(false)
                    }

                    override fun onError(e: Throwable) {
                        isLoading.set(false)
                    }


                })
    }

    fun onAddCategory() {
        if (name.get().isNullOrEmpty()) {
            errorName.set("Tidak boleh kosong")
        } else if (name.get()?.length!! < 4) {
            errorName.set("Kurang dari 4 karakter")
        } else {
            errorName.set(null)
            _isValid.value = true
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}
