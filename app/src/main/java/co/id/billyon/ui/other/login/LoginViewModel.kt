package co.id.billyon.ui.other.login

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.BaseObservable
import android.databinding.Bindable
import javax.inject.Inject

class LoginViewModel @Inject constructor() : ViewModel() {

    val _usernameValue = MutableLiveData<String>()
    val usernameValue: LiveData<String>
        get() = _usernameValue
}