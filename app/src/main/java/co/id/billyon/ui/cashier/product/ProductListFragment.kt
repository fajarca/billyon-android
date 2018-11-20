package co.id.billyon.ui.cashier.product


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import co.id.billyon.R
import co.id.billyon.adapter.ProductsRecyclerAdapter
import co.id.billyon.databinding.FragmentProductListBinding
import co.id.billyon.db.entity.Products
import co.id.billyon.ui.cashier.addproduct.AddProductFragmentArgs
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

    private var categoryId : Int = 0
    private var storeId : Int = 0

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

            recyclerView.layoutManager = GridLayoutManager(activity,2)
            recyclerView.adapter = adapter
        }


        viewModel.products.observe(this, Observer { products-> products?.let {
            adapter.replaceData(it)
        } })

        val passedArgument = AddProductFragmentArgs.fromBundle(arguments)
        categoryId = passedArgument.categoryId
        storeId = passedArgument.storeId

        viewModel.findProduct(categoryId)

        return binding.root
    }


    override fun onProductSelected(product: Products) {
       
    }

    override fun onFabAddProductPressed(view: View) {
        val action = ProductListFragmentDirections.actionLaunchAddProduct()
        action.setStoreId(storeId)
        action.setCategoryId(categoryId)
        findNavController().navigate(action)
    }

}
