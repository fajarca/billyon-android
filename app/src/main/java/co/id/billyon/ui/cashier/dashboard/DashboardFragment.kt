package co.id.billyon.ui.cashier.dashboard


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import co.id.billyon.R
import co.id.billyon.adapter.CategoryRecyclerAdapter
import co.id.billyon.adapter.ProductsRecyclerAdapter
import co.id.billyon.databinding.FragmentCashierDashboardBinding
import co.id.billyon.db.entity.Category
import co.id.billyon.db.entity.CategoryWithProducts
import co.id.billyon.db.entity.Products
import co.id.billyon.util.Utils
import co.id.billyon.util.handlers.BillyonClickHandlers
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


class DashboardFragment : Fragment(), ProductsRecyclerAdapter.OnProductClickListener, BillyonClickHandlers.Dashboard, CategoryRecyclerAdapter.OnCategoryClickListener {

    lateinit var binding: FragmentCashierDashboardBinding
    private val categoryAdapter = CategoryRecyclerAdapter(arrayListOf(), this)
    private lateinit var viewModel: DashboardViewModel
    private val TAG = DashboardFragment::class.java.simpleName

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DashboardViewModel::class.java)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cashier_dashboard, container, false)
        binding.handlers = this
        binding.apply {
            viewmodel = viewModel
            binding.executePendingBindings()

            recyclerView.layoutManager = LinearLayoutManager(activity)
            recyclerView.adapter = categoryAdapter
        }
       /* viewModel.loadAllProducts()
        viewModel.productsData.observe(this, Observer { data ->
            data?.let {
                productRecylerViewAdapter.replaceData(it)
            }
        })

        viewModel.getAllCategory()*/
        viewModel.getAllCategories()

        /*viewModel._categories.observe(this, Observer { data ->
            data?.let {
                categoryAdapter.refreshData(it)

            }
        })*/

        viewModel._categoriesProducts.observe(this, Observer { data ->
            data?.let {
                categoryAdapter.refreshData(it)
            }
        })

        viewModel.isInsertCategorySuccessful.observe(this, Observer { datas ->
            datas?.let {
               if (it) {
                   Toast.makeText(activity, "Berhasil insert", Toast.LENGTH_LONG).show()
               }
            }
        })

        return binding.root
    }

    override fun onProductSelected(product: Products) {
        //viewModel.deleteProduct(product)
    }

    override fun onFabAddProductPressed(view: View) {
        val currentTimestamp = Utils.getCurrentTimeStamp();
        val categories = arrayListOf<Category>()

        categories.add(Category("Pakaian", 1, true, true, currentTimestamp, currentTimestamp))
        /*categories.add(Category("Pakaian Dalam", 1, true, true, currentTimestamp, currentTimestamp))
        categories.add(Category("Bawahan", 2, true, true, currentTimestamp, currentTimestamp))
        categories.add(Category("Outer", 3, true, true, currentTimestamp, currentTimestamp))
        categories.add(Category("Jacket", 4, true, true, currentTimestamp, currentTimestamp))
        categories.add(Category("Sweater", 5, true, true, currentTimestamp, currentTimestamp))*/


        viewModel.insertAllCategories(categories)

        //viewModel.loadPosts()
        //viewModel.deleteAllProduct()
        /*  val currentTimestampAsId = Utils.getCurrentTimestampAsId()
          val currentTimestamp = Utils.getCurrentTimeStamp()
          val product = Products(currentTimestampAsId, 1, 1, "/haha", "Kopi Susu Keluarga", 100, 80, 12000, 8000, true,true,currentTimestamp,currentTimestamp)

          viewModel.insertProduct(product)*/

      /*  val action = DashboardFragmentDirections.actionLaunchAddProduct()
        val navController = Navigation.findNavController(view)
        navController.navigate(action)*/

        /*  val currentTimestampAsId = Utils.getCurrentTimestampAsId()
          val currentTimestamp = Utils.getCurrentTimeStamp()
          val product = Products(currentTimestampAsId, 1, 1, "/haha", "Kopi Susu Keluarga", 100, 80, 12000, 8000, true, true, currentTimestamp, currentTimestamp)

          viewModel.insertProduct(product)
  */
    }

    override fun onCategorySelected(category: CategoryWithProducts) {
        val action = DashboardFragmentDirections.actionLaunchProductList()
        action.setStoreId(category.storeId.toInt())
        action.setCategoryId(category.id.toInt())
        Log.v(TAG, "Store id ${category.storeId} category id ${category.id}")
        findNavController().navigate(action)
    }

}
