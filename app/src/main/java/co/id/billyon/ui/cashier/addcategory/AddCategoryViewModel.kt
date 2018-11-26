package co.id.billyon.ui.cashier.addcategory

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean
import co.id.billyon.db.entity.Category
import co.id.billyon.repository.cashier.dashboard.DashboardRepository
import co.id.billyon.util.extensions.plusAssign
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AddCategoryViewModel @Inject constructor(private val repository: DashboardRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    val _categoryName = MutableLiveData<String>()
    val isEmpty = ObservableBoolean()
    val isValid = MutableLiveData<Boolean>()

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
        if (_categoryName.value.isNullOrEmpty()) {
            isEmpty.set(true)
            isValid.value = false
        } else {
            isEmpty.set(false)
            isValid.value = true
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}
