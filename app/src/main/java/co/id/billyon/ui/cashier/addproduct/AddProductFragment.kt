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
import androidx.navigation.fragment.findNavController
import co.id.billyon.R
import co.id.billyon.databinding.FragmentAddProductBinding
import co.id.billyon.db.entity.Products
import co.id.billyon.util.Utils
import co.id.billyon.util.extensions.removeAllThousandSeparator
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


class AddProductFragment : Fragment(){
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
        binding.vm = vm

        vm.isInsertSuccessful.observe(this, Observer { findNavController().popBackStack()})
        vm.isAddProductValid.observe(this,
                Observer {
                    it?.let {
                        if (it) {
                            addProduct()
                        }
                    }
                }
        )

        val passedArgument = AddProductFragmentArgs.fromBundle(arguments)
        categoryId = passedArgument.categoryId
        storeId = passedArgument.storeId


        return binding.root
    }

    private fun addProduct() {
        val imagePath = "/haha"
        val productName = binding.contentAddProduct.etProductName.text.toString().trim()
        val productInitialStock = binding.contentAddProduct.etInitialStock.text.toString().trim().toInt()
        val productMinStock = binding.contentAddProduct.etMinStock.text.toString().trim().toInt()
        val productDisplayPrice = binding.contentAddProduct.etDisplayPrice.text.toString().trim().removeAllThousandSeparator()
        val productActualPrice = binding.contentAddProduct.etActualPrice.text.toString().trim().removeAllThousandSeparator()

        val currentTimestampAsId = Utils.getCurrentTimestampAsId()
        val currentTimestamp = Utils.getCurrentTimeStamp()
        val product = Products(currentTimestampAsId, storeId, categoryId, imagePath, productName, productInitialStock, productMinStock, productDisplayPrice, productActualPrice, true, true, currentTimestamp, currentTimestamp, true,false)

        vm.insertProduct(product)
    }


}
