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
import co.id.billyon.di.DaggerAppComponent
import co.id.billyon.di.Info
import co.id.billyon.viewmodel.CashierDashboardViewModel
import javax.inject.Inject


class CashierDashboardFragment : Fragment(), ProductsRecyclerAdapter.OnProductClickListener {

    @Inject
    lateinit var info : Info

    lateinit var binding : FragmentCashierDashboardBinding
    private val productRecylerViewAdapter = ProductsRecyclerAdapter(arrayListOf(), this)
    private lateinit var viewModel : CashierDashboardViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        viewModel = ViewModelProviders.of(this).get(CashierDashboardViewModel::class.java)

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_cashier_dashboard, container,false)

        binding.apply {
            viewmodel = viewModel
            binding.executePendingBindings()

            recyclerView.layoutManager = GridLayoutManager(activity, 2)
            recyclerView.adapter = productRecylerViewAdapter
        }

        viewModel.loadAllProducts()?.observe(this, Observer { data ->
            data?.let {
                productRecylerViewAdapter.replaceData(it)
                Log.v("Ha", data.size.toString())
            }
        })

        DaggerAppComponent.create().inject(this)
        val text = info.text

        return binding.root
    }

    override fun onProductSelected(position: Int) {
        Toast.makeText(activity,"$position", Toast.LENGTH_SHORT).show()
        viewModel.insertProduct()
    }

}
