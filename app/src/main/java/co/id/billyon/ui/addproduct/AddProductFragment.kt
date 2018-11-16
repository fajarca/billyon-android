package co.id.billyon.ui.addproduct


import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.id.billyon.R
import co.id.billyon.databinding.FragmentAddProductBinding
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


class AddProductFragment : Fragment() {
    private lateinit var binding : FragmentAddProductBinding
    private lateinit var vm : AddProductViewModel
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

        return binding.root
    }


}
