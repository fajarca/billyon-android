package co.id.billyon.ui.cashier.product


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.*
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import co.id.billyon.R
import co.id.billyon.adapter.ProductsRecyclerAdapter
import co.id.billyon.databinding.DialogCustomQuantityBinding
import co.id.billyon.databinding.FragmentProductListBinding
import co.id.billyon.db.entity.Carts
import co.id.billyon.db.entity.Products
import co.id.billyon.db.entity.join.ProductsAndCartProduct
import co.id.billyon.ui.cashier.addproduct.AddProductFragmentArgs
import co.id.billyon.util.Utils
import com.karumi.dexter.Dexter
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

    override fun onAttach(context: Context?) {
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

            binding.executePendingBindings()

            recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
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

    override fun onQuantityTyped(quantity: Int, product: ProductsAndCartProduct) {
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

    fun dismissDialog() {
        dialog.dismiss()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater) {
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
