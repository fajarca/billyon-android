package co.id.billyon.base

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import co.id.billyon.ui.cashier.product.ProductListViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

abstract class BaseFragment<B : ViewDataBinding, V : ViewModel> : Fragment() {

    lateinit var mViewDataBinding: B
    lateinit var mViewModel: V


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mViewModel = ViewModelProviders.of(this, getVmFactory()).get(getViewModelClass())
        mViewDataBinding = DataBindingUtil.inflate(inflater, getLayoutResourceId(), container, false)
        mViewDataBinding.executePendingBindings()
        return mViewDataBinding.root
    }

    @LayoutRes
    abstract fun getLayoutResourceId(): Int
    abstract fun getViewModelClass() : Class<V>
    abstract fun getVmFactory() : ViewModelProvider.Factory

    fun getDataBinding(): B {
        return mViewDataBinding
    }

    fun getViewModel() : V {
        return mViewModel
    }


}