package co.id.billyon.ui.cashier.product


import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.id.billyon.R
import co.id.billyon.adapter.ProductsRecyclerAdapter
import co.id.billyon.databinding.DialogCustomQuantityBinding
import co.id.billyon.databinding.FragmentProductListBinding
import co.id.billyon.db.entity.join.ProductsAndCartProduct
import co.id.billyon.ui.cashier.addproduct.AddProductFragmentArgs
import com.google.android.material.bottomnavigation.BottomNavigationView
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
    private lateinit var dialog : AlertDialog

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ProductListViewModel::class.java)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_list, container, false)

        binding.apply {
            vm = viewModel
            fragment = this@ProductListFragment

            binding.executePendingBindings()

            recyclerView.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
            recyclerView.adapter = adapter
        }


        viewModel.products.observe(this,
                Observer { products ->
                    products?.let {
                       adapter.replaceData(it)
                    }
                })

        viewModel.isQuantityValid.observe(this,
                Observer { isValid ->
                    isValid?.let {
                        if (it.isValid) {
                            dismissDialog()
                            viewModel.updateQuantity(it.productId, it.quantity)
                        }
                    }
                })

        viewModel.isDialogDismissed.observe(this,
                Observer { shouldDismiss ->
                    shouldDismiss?.let {
                        if (it) {
                            dismissDialog()
                        }
                    }
                })

        viewModel.activeCartId.observe(this,
                Observer {
                    if (it.success) {
                        launchCartFragment(it.cartId.toInt())
                    }
                }
        )

        val passedArgument = AddProductFragmentArgs.fromBundle(arguments)
        categoryId = passedArgument.categoryId
        storeId = passedArgument.storeId
        categoryName = passedArgument.categoryName

        viewModel.findProduct(categoryId)
        viewModel.findAllProductsOnCart()



        val toolbar = activity?.findViewById<Toolbar>(R.id.toolbar)
        toolbar?.title = categoryName
        val bottomNav = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNav?.visibility = View.GONE

        return binding.root
    }


    override fun onAddProductPressed(product: ProductsAndCartProduct, position: Int) {
        viewModel.addToCart(false, product.productId, 1)
    }

    override fun onRemoveProductPressed(product: ProductsAndCartProduct) {
        viewModel.deleteFromCart(product.productId)
    }

    override fun onAddQtyPressed(quantity: Int, product: ProductsAndCartProduct) {
        viewModel.updateQuantity(product.productId, quantity)
    }

    override fun onRemoveQtyPressed(quantity: Int, product: ProductsAndCartProduct) {
        viewModel.updateQuantity(product.productId, quantity)
    }

    override fun onCustomQtyPressed(quantity: Int, product: ProductsAndCartProduct) {
        val binding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.dialog_custom_quantity,null,false) as DialogCustomQuantityBinding
        binding.vm = viewModel
        binding.etEnterQuantity.setText(quantity.toString())
        binding.etEnterQuantity.setSelection(quantity.toString().length)

        binding.btnAdd.setOnClickListener {
            val newQty = binding.etEnterQuantity.text.toString().trim()
            viewModel.validateQty(product.productId, newQty, product.stock)
        }


        val builder = AlertDialog.Builder(activity!!)
        builder.setView(binding.root)

        dialog = builder.create()
        dialog.show()
    }

    fun launchCartFragment(cartId : Int) {
        val action = ProductListFragmentDirections.actionLaunchCartFragment()
        action.setCartId(cartId)
        findNavController().navigate(action)
    }

    private fun dismissDialog() {
        dialog.dismiss()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_add_product, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.menu_add_product -> {
                val action = ProductListFragmentDirections.actionLaunchAddProduct()
                action.setStoreId(storeId)
                action.setCategoryId(categoryId)
                findNavController().navigate(action)
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
