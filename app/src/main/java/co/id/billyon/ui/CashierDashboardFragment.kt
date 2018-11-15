package co.id.billyon.ui


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
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
import co.id.billyon.db.entity.Products
import co.id.billyon.di.Info
import co.id.billyon.ui.handlers.BillyonHandlers
import co.id.billyon.util.HELLO
import co.id.billyon.util.LOVE
import co.id.billyon.util.Utils
import co.id.billyon.util.annotation.Use
import co.id.billyon.viewmodel.CashierDashboardViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


class CashierDashboardFragment : Fragment(), ProductsRecyclerAdapter.OnProductClickListener, BillyonHandlers {

    lateinit var binding: FragmentCashierDashboardBinding
    private val productRecylerViewAdapter = ProductsRecyclerAdapter(arrayListOf(), this)
    private lateinit var viewModel: CashierDashboardViewModel

    @Inject
    @field:Use(LOVE)
    lateinit var infoLove: Info

    @Inject
    @field:Use(HELLO)
    lateinit var infoHello: Info

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
        val text = "${infoHello.text} ${infoLove.text}"
        Log.v("Ha", text)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CashierDashboardViewModel::class.java)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cashier_dashboard, container, false)
        binding.handlers = this
        binding.apply {
            viewmodel = viewModel
            binding.executePendingBindings()

            recyclerView.layoutManager = GridLayoutManager(activity, 2)
            recyclerView.adapter = productRecylerViewAdapter
        }

        viewModel.loadAllProducts().observe(this, Observer { data ->
            data?.let {
                productRecylerViewAdapter.replaceData(it)
            }
        })
        return binding.root
    }

    override fun onProductSelected(position: Int) {

    }

    override fun onFabAddProductPressed() {
        val currentTimestampAsId = Utils.getCurrentTimestampAsId()
        val currentTimestamp = Utils.getCurrentTimeStamp()
        val product = Products(currentTimestampAsId, 1, 1, "/haha", "Kopi Susu Keluarga", 100, 80, 12000, 8000, true,true,currentTimestamp,currentTimestamp)

        viewModel.insertProduct(product)
    }

}
