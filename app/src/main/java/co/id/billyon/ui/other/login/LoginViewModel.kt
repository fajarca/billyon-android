package co.id.billyon.ui.other.login

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.BaseObservable
import android.databinding.Bindable
import android.databinding.ObservableBoolean
import android.text.TextUtils
import co.id.billyon.repository.cashier.login.LoginRepository
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val repository: LoginRepository) : ViewModel() {

    val _usernameValue = MutableLiveData<String>()
    val _passwordValue = MutableLiveData<String>()
    private val _loginSuccess = MutableLiveData<Boolean>()

    val loginSuccess: LiveData<Boolean>
        get() = _loginSuccess

    /* val usernameValue: LiveData<String>
         get() = _usernameValue

     val passwordValue: LiveData<String>
         get() = _passwordValue*/

    var isLoggedIn: Boolean
        get() = repository.isLoggedIn()
        set(value) {
            repository.setLoggedIn(value)
        }

    fun onLoginPressed() {
        val username = _usernameValue.value
        val password = _passwordValue.value

        if (TextUtils.isEmpty(username)) {
            _loginSuccess.value = false
        } else if (TextUtils.isEmpty(password)) {
            _loginSuccess.value = false
        } else if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
            _loginSuccess.value = true
        }
    }
}