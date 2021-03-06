package co.id.billyon.ui.cashier.cart


import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import co.id.billyon.R
import co.id.billyon.base.BaseFragment
import co.id.billyon.databinding.FragmentCartBinding


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

        val args = CartFragmentArgs.fromBundle(arguments)
        val cartId = args.cartId

        getViewModel().getAllProducts(cartId)
        getViewModel().products.observe(this,
                Observer {
                    it?.let {

                    }
                }
        )
    }




}
