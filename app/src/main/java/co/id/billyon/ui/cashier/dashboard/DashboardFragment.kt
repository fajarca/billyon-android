package co.id.billyon.ui.cashier.dashboard


import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import co.id.billyon.R
import co.id.billyon.adapter.CategoryRecyclerAdapter
import co.id.billyon.databinding.FragmentCashierDashboardBinding
import co.id.billyon.db.entity.join.CategoryWithProducts
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.DexterError
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.PermissionRequestErrorListener
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


class DashboardFragment : Fragment(), CategoryRecyclerAdapter.OnCategoryClickListener {


    lateinit var binding: FragmentCashierDashboardBinding
    private val categoryAdapter = CategoryRecyclerAdapter(arrayListOf(), this)
    private lateinit var viewModel: DashboardViewModel
    private var storeId = 0
    private val TAG = DashboardFragment::class.java.simpleName
    private var cartCount : Int = 0

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DashboardViewModel::class.java)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cashier_dashboard, container, false)
        binding.apply {
            viewmodel = viewModel
            binding.executePendingBindings()

            recyclerView.layoutManager = LinearLayoutManager(activity)
            recyclerView.adapter = categoryAdapter
        }


        val args = DashboardFragmentArgs.fromBundle(arguments)
        storeId = args.storeId

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.getAllCategories()
        viewModel.categories.observe(this, Observer { categories ->
            categories?.let {
                categoryAdapter.refreshData(it)
            }
        })

        viewModel.getProductCountOnCart()
        viewModel.productCountOnCart.observe(this, Observer {
            it?.let {
                cartCount = it
                activity?.invalidateOptionsMenu()
            }
        })

        viewModel.addCategoryMessage.observe(this, Observer { Toast.makeText(activity, it, Toast.LENGTH_LONG).show() })

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_dashboard, menu)

        val menuItem = menu.findItem(R.id.menu_view_cart)
        val actionView = menuItem.actionView

        val tvBadgeCount = actionView.findViewById(R.id.tvCartCount) as TextView
        val ivCount = actionView.findViewById(R.id.ivCart) as ImageView

        tvBadgeCount.setOnClickListener {

        }

        ivCount.setOnClickListener {
            Toast.makeText(activity, "Hola", Toast.LENGTH_SHORT).show()
        }

        displayBadge(tvBadgeCount)


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.menu_add_category -> {
                Dexter.withActivity(activity)
                        .withPermissions(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(permissionListener)
                        .withErrorListener(errorPermissionListener)
                        .onSameThread()
                        .check()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }/*
    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }*/

    override fun onCategorySelected(category: CategoryWithProducts) {
        launchProductDetails(category)
    }
    override fun onCategoryImageSelected(category: CategoryWithProducts) {

    }

    fun launchProductDetails(category: CategoryWithProducts) {
        val action = DashboardFragmentDirections.actionLaunchProductList()
        action.setStoreId(category.storeId.toInt())
        action.setCategoryId(category.id.toInt())
        action.setCategoryName(category.categoryName)
        findNavController().navigate(action)
    }

    fun launchAddCategory() {
        val action = DashboardFragmentDirections.actionLaunchAddCategory()
        action.setStoreId(storeId)
        findNavController().navigate(action)
    }

    val permissionListener = object : MultiplePermissionsListener {
        override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
            report?.let {
                // check if all permissions are granted
                if (report.areAllPermissionsGranted()) {
                    launchAddCategory()
                }

                //One of the required permission is denied
                if (report.isAnyPermissionPermanentlyDenied) {
                    Toast.makeText(activity, "One of the required permission is denied", Toast.LENGTH_LONG).show()
                }
            }
        }

        override fun onPermissionRationaleShouldBeShown(permissions: MutableList<PermissionRequest>?, token: PermissionToken?) {
            token?.continuePermissionRequest()
        }

    }
    val errorPermissionListener = object : PermissionRequestErrorListener {
        override fun onError(error: DexterError?) {
            Toast.makeText(activity, "Permission error ${error.toString()}", Toast.LENGTH_LONG).show()
        }

    }

    fun displayBadge(tvBadgeCount : TextView) {
        if (cartCount == 0) {
            tvBadgeCount.visibility = View.GONE
        } else {
            tvBadgeCount.visibility = View.VISIBLE
            tvBadgeCount.text = cartCount.toString()
        }
    }

}
