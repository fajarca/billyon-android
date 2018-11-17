package co.id.billyon.ui.cashier.addproduct


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import co.id.billyon.R
import co.id.billyon.databinding.FragmentAddProductBinding
import co.id.billyon.db.entity.Products
import co.id.billyon.util.handlers.BillyonClickHandlers
import co.id.billyon.util.Utils
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


class AddProductFragment : Fragment(), BillyonClickHandlers.AddProduct {

    private lateinit var binding: FragmentAddProductBinding
    private lateinit var vm: AddProductViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        vm = ViewModelProviders.of(this, viewModelFactory).get(AddProductViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_product, container, false)
        binding.handlers = this

        vm.isInsertSuccessful.observe(this, Observer { isSuccess->
            isSuccess?.let {
                if (it) {
                    Toast.makeText(activity, "Berhasil insert",Toast.LENGTH_LONG).show()
                    findNavController().navigate(R.id.fragmentCashierDashboard)
                }
            } })

        return binding.root
    }

    override fun onButtonSaveProductPressed(view: View) {
        val imagePath = "/haha"
        val productName = binding.contentAddProduct.etProductName.text.toString().trim()
        val productQty = binding.contentAddProduct.etQuantity.text.toString()
        val productMinQty = binding.contentAddProduct.etMinQuantity.text.toString()
        val productDisplayPrice = binding.contentAddProduct.etDisplayPrice.text.toString()
        val productActualPrice = binding.contentAddProduct.etActualPrice.text.toString()

        val currentTimestampAsId = Utils.getCurrentTimestampAsId()
        val currentTimestamp = Utils.getCurrentTimeStamp()
        val product = Products(currentTimestampAsId, 1, 1, imagePath, productName, productQty.toInt(), productMinQty.toInt(), productDisplayPrice.toLong(), productActualPrice.toLong(), true, false, currentTimestamp, currentTimestamp)

         vm.insertProduct(product)
    }

}
