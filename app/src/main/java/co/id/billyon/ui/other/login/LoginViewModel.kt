package co.id.billyon.ui.other.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import co.id.billyon.model.response.LoginResponse
import co.id.billyon.repository.cashier.login.LoginRepository
import co.id.billyon.util.extensions.plusAssign
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val repository: LoginRepository) : ViewModel() {
    val compositeDisposable = CompositeDisposable()

    var username = ObservableField<String>()
    var errorUsername = ObservableField<String>()
    var password = ObservableField<String>()
    var errorPassword = ObservableField<String>()
    var isLoading = ObservableBoolean()

    private val _isLoginSuccess = MutableLiveData<Boolean>()
    private val _data = MutableLiveData<LoginResponse.Data>()

    val isLoginSuccess: LiveData<Boolean>
        get() = _isLoginSuccess

    val data: LiveData<LoginResponse.Data>
        get() = _data

    var isLoggedIn: Boolean
        get() = repository.isLoggedIn()
        set(value) {
            repository.setLoggedIn(value)
        }

    var isCashier: Boolean
        get() = repository.isCashier()
        set(value) {
            repository.setAsCashier(value)
        }

    fun onLoginPressed() {
        if (username.get().isNullOrEmpty()) {
            errorUsername.set("Username can't be empty")
            _isLoginSuccess.value = false
            return
        }

        if (password.get().isNullOrEmpty()) {
            errorPassword.set("Password can't be empty")
            _isLoginSuccess.value = false
            return
        }

        if (!username.get().isNullOrEmpty() && !password.get().isNullOrEmpty()) {
            errorUsername.set(null)
            errorPassword.set(null)
            login(username.get(), password.get())
        }

    }


    fun login(username: String?, password: String?) {
        isLoading.set(true)
        compositeDisposable += repository.login(username!!, password!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                /*.doOnNext {
                    it?.let {

                        if (!it.error) {
                            _data.value = it.data
                            _isLoginSuccess.value = true

                            repository.setLoggedIn(true)
                            repository.setAsCashier(it.data.users.roleId == 1) //role id 1 = cashier
                        }

                        isLoading.set(false)

                    }
                }
                .doOnError {
                    _isLoginSuccess.value = false
                    isLoading.set(false)
                }
                .doOnComplete {
                    isLoading.set(false)
                }*/
                .subscribeWith(object : DisposableObserver<LoginResponse>() {
                    override fun onComplete() {
                        isLoading.set(false)
                    }

                    override fun onNext(it: LoginResponse) {
                        if (!it.error) {
                            _data.value = it.data
                            _isLoginSuccess.value = true

                            repository.setLoggedIn(true)
                            repository.setAsCashier(it.data.users.roleId == 1) //role id 1 = cashier
                        }

                        isLoading.set(false)


                    }

                    override fun onError(e: Throwable) {
                        _isLoginSuccess.value = false
                        isLoading.set(false)
                    }
                })

    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}