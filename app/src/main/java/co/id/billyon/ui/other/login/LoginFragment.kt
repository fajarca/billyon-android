package co.id.billyon.ui.other.login


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
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import co.id.billyon.R
import co.id.billyon.databinding.FragmentCashierDashboardBinding
import co.id.billyon.databinding.FragmentLoginBinding
import co.id.billyon.di.Info
import co.id.billyon.ui.cashier.dashboard.DashboardFragment
import co.id.billyon.ui.cashier.dashboard.DashboardViewModel
import co.id.billyon.util.HELLO
import co.id.billyon.util.LOVE
import co.id.billyon.util.annotation.Use
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_login.*
import javax.inject.Inject


class LoginFragment : Fragment() {

    @Inject
    @field:Use(LOVE)
    lateinit var infoLove : Info

    @Inject
    @field:Use(HELLO)
    lateinit var infoHello : Info

    private lateinit var viewModel: LoginViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var binding: FragmentLoginBinding

    private val TAG = DashboardFragment::class.java.simpleName

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel::class.java)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        binding.apply {
            vm = viewModel
            binding.executePendingBindings()

        }

        viewModel.usernameValue.observe(this, Observer { data -> Log.v(TAG, "Username $data") })
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       /* labelRegister.setOnClickListener {
            val action = LoginFragmentDirections.actionLaunchRegister()
            val navController = Navigation.findNavController(view)
            navController.navigate(action)
        }
        btnLogin.setOnClickListener {
            val action = LoginFragmentDirections.actionLaunchOwnerDashboard()
            val navController = Navigation.findNavController(view)
            navController.navigate(action)
        }*/
    }


}
