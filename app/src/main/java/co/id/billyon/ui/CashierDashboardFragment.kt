package co.id.billyon.ui


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import co.id.billyon.R
import co.id.billyon.adapter.ProductsRecyclerAdapter
import co.id.billyon.databinding.FragmentCashierDashboardBinding
import co.id.billyon.model.Product
import co.id.billyon.viewmodel.CashierDashboardViewModel


class CashierDashboardFragment : Fragment(), ProductsRecyclerAdapter.OnProductClickListener {


    lateinit var binding : FragmentCashierDashboardBinding
    private val productRecylerViewAdapter = ProductsRecyclerAdapter(arrayListOf(), this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_cashier_dashboard, container,false)

        val viewModel = ViewModelProviders.of(this).get(CashierDashboardViewModel::class.java)
        viewModel.loadProducts()

        binding.viewModel = viewModel
        binding.executePendingBindings()

        binding.recyclerView.layoutManager = GridLayoutManager(activity, 2)
        binding.recyclerView.adapter = productRecylerViewAdapter
        viewModel.products.observe(this, Observer<ArrayList<Product>> {
            it?.let {
                productRecylerViewAdapter.replaceData(it)
            }
        })

        viewModel.loadAllProducts().observe(this, Observer { data ->
            data?.let {
                Log.v("Ha", data.size.toString())
            }


        })
        return binding.root
    }

    override fun onProductSelected(position: Int) {
        Toast.makeText(activity,"$position", Toast.LENGTH_SHORT).show()
    }

}
