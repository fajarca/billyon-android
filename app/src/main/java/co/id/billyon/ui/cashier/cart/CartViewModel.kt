package co.id.billyon.ui.cashier.cart

import android.util.Log
import androidx.lifecycle.ViewModel
import co.id.billyon.base.BaseViewModel
import co.id.billyon.repository.cashier.category.CategoryRepository
import timber.log.Timber
import javax.inject.Inject

class CartViewModel @Inject constructor(private val repository: CategoryRepository) : BaseViewModel() {

    fun sayO() {
        Timber.d("Ouch")
    }
}