package co.id.billyon.ui.cashier.cart


import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import co.id.billyon.R
import co.id.billyon.base.BaseFragment
import co.id.billyon.databinding.FragmentCartBinding
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


class CartFragment : BaseFragment<FragmentCartBinding, CartViewModel>() {



    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_cart
    }

    override fun getViewModelClass(): Class<CartViewModel> {
        return CartViewModel::class.java
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDataBinding().tvTest.setText("Ha")
        getViewModel().sayO()
    }




}
