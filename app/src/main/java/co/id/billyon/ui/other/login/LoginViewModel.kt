package co.id.billyon.ui.other.login

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.BaseObservable
import android.databinding.Bindable
import co.id.billyon.repository.cashier.login.LoginRepository
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val repository: LoginRepository) : ViewModel() {

    val _usernameValue = MutableLiveData<String>()
    val usernameValue: LiveData<String>
        get() = _usernameValue

    var isLoggedIn: Boolean
        get() = repository.isLoggedIn()
        set(value) {
            repository.setLoggedIn(value)
        }
}