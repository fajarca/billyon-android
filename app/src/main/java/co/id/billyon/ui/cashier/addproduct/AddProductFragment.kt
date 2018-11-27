package co.id.billyon.ui.cashier.addproduct


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import co.id.billyon.R
import co.id.billyon.databinding.FragmentAddProductBinding
import co.id.billyon.db.entity.Category
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
    private var categoryId : Int = 0
    private var storeId : Int = 0


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
        binding.contentAddProduct.handlers = this



        vm.isInsertSuccessful.observe(this, Observer { findNavController().popBackStack()})


        //vm.getAllCategory()

       /* vm._categories.observe(this, Observer { data ->
            data?.let {
                val adapter = ArrayAdapter<Category>(activity, R.layout.support_simple_spinner_dropdown_item, data)
                binding.contentAddProduct.spinner.adapter = adapter
                adapter.notifyDataSetChanged()
            }
        })*/

        val passedArgument = AddProductFragmentArgs.fromBundle(arguments)
        categoryId = passedArgument.categoryId
        storeId = passedArgument.storeId


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


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
        val product = Products(currentTimestampAsId, storeId, categoryId, imagePath, productName, productQty.toInt(), productMinQty.toInt(), productDisplayPrice.toLong(), productActualPrice.toLong(), true, true, currentTimestamp, currentTimestamp)

        vm.insertProduct(product)
    }

}
