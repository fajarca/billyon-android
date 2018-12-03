package co.id.billyon.ui.cashier.product


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
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
import co.id.billyon.util.Utils
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


class ProductListFragment : Fragment(), ProductsRecyclerAdapter.OnProductClickListener {
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

        binding.apply {
            vm = viewModel
            fragment = this@ProductListFragment
            binding.executePendingBindings()

            recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
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
        viewModel.findAllCart(false)


        val toolbar = activity?.findViewById<Toolbar>(R.id.toolbar)
        toolbar?.title = categoryName
        val bottomNav = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNav?.visibility = View.GONE

        return binding.root
    }


    override fun onAddProductPressed(product: Products) {
        /* val cart = Carts(1, false, true,  Utils.getCurrentTimeStamp(), Utils.getCurrentTimeStamp())
         viewModel.createCart(cart)*/
        viewModel.addToCart(false, product.id, product.storeId.toLong(), 2)
    }

    override fun onRemoveProductPressed(product: Products) {
        /*val cart = Carts(product.id, product.storeId, 1, 1, false, Utils.getCurrentTimeStamp(), Utils.getCurrentTimeStamp())
        viewModel.deleteFromCart(cart)*/
    }

    override fun onAddQtyPressed(quantity: Int, product: Products) {
        //viewModel.updateQuatity(product.id, 2)
        Toast.makeText(activity, "$quantity", Toast.LENGTH_SHORT).show()
    }

    override fun onRemoveQtyPressed(product: Products) {
        //viewModel.updateQuatity(product.id, 1)
    }

    fun onFabAddProductPressed() {
        val action = ProductListFragmentDirections.actionLaunchAddProduct()
        action.setStoreId(storeId)
        action.setCategoryId(categoryId)
        findNavController().navigate(action)
    }

}
