package co.id.billyon.ui.cashier.product


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import co.id.billyon.R
import co.id.billyon.adapter.ProductsRecyclerAdapter
import co.id.billyon.databinding.FragmentProductListBinding
import co.id.billyon.db.entity.Carts
import co.id.billyon.db.entity.Products
import co.id.billyon.ui.cashier.addproduct.AddProductFragmentArgs
import co.id.billyon.ui.cashier.dashboard.DashboardFragment
import co.id.billyon.ui.cashier.dashboard.DashboardFragmentDirections
import co.id.billyon.util.handlers.BillyonClickHandlers
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


class ProductListFragment : Fragment(), ProductsRecyclerAdapter.OnProductClickListener, BillyonClickHandlers.ProductList {
    lateinit var binding: FragmentProductListBinding
    private val adapter = ProductsRecyclerAdapter(arrayListOf(), this)
    private lateinit var viewModel: ProductListViewModel
    private val TAG = ProductListFragment::class.java.simpleName

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var categoryId: Int = 0
    private var storeId: Int = 0
    private var categoryName: String = ""

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ProductListViewModel::class.java)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_list, container, false)
        binding.handlers = this

        binding.apply {
            vm = viewModel
            binding.executePendingBindings()

            recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
            recyclerView.adapter = adapter
        }


        viewModel.products.observe(this, Observer { products ->
            products?.let {
                adapter.replaceData(it)
            }
        })

        val passedArgument = AddProductFragmentArgs.fromBundle(arguments)
        categoryId = passedArgument.categoryId
        storeId = passedArgument.storeId
        categoryName = passedArgument.categoryName

        viewModel.findProduct(categoryId)

        val toolbar = activity?.findViewById<Toolbar>(R.id.toolbar)
        toolbar?.title = categoryName
        return binding.root
    }


    override fun onAddProductPressed(product: Products) {
        val cart = Carts(product.id, product.storeId, 1)
        viewModel.addToCart(cart)
    }

    override fun onRemoveProductPressed(product: Products) {
        val cart = Carts(product.id, product.storeId, 1)
        viewModel.deleteFromCart(cart)
    }

    override fun onAddQtyPressed(product: Products) {
        viewModel.updateQuatity(product.id,2)
    }

    override fun onRemoveQtyPressed(product: Products) {
        viewModel.updateQuatity(product.id,1)
    }

    override fun onFabAddProductPressed(view: View) {
        val action = ProductListFragmentDirections.actionLaunchAddProduct()
        action.setStoreId(storeId)
        action.setCategoryId(categoryId)
        findNavController().navigate(action)
    }

}
