package co.id.billyon.ui.cashier.addcategory

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import co.id.billyon.db.entity.Category
import co.id.billyon.repository.cashier.dashboard.CategoryRepository
import co.id.billyon.util.extensions.default
import co.id.billyon.util.extensions.plusAssign
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AddCategoryViewModel @Inject constructor(private val repository: CategoryRepository) : ViewModel() {

    var name = ObservableField<String>()
    var errorName = ObservableField<String>()
    private val _isValid = MutableLiveData<Boolean>().default(false)
    private val compositeDisposable = CompositeDisposable()

    val isValid: LiveData<Boolean>
        get() = _isValid

    fun insertCategory(category: Category) {
        compositeDisposable += Completable.fromCallable { repository.insertCategory(category) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableCompletableObserver() {
                    override fun onComplete() {
                    }

                    override fun onError(e: Throwable) {

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
