package co.id.billyon.ui.other.login


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.id.billyon.R
import co.id.billyon.databinding.FragmentLoginBinding
import co.id.billyon.di.Info
import co.id.billyon.ui.cashier.dashboard.DashboardFragment
import co.id.billyon.util.HELLO
import co.id.billyon.util.LOVE
import co.id.billyon.util.annotation.Use
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


class LoginFragment : Fragment() {

    @Inject
    @field:Use(LOVE)
    lateinit var infoLove: Info

    @Inject
    @field:Use(HELLO)
    lateinit var infoHello: Info

    private lateinit var viewModel: LoginViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var binding: FragmentLoginBinding

    private val TAG = DashboardFragment::class.java.simpleName

    override fun onAttach(context: Context) {
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



        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.isLoginSuccess.observe(this,
                Observer {
                    it?.let {
                        if (it) {
                            val action = LoginFragmentDirections.actionLaunchCashierDashboard()
                            action.setStoreId(2)
                            findNavController().navigate(action)

                        } else {
                            Toast.makeText(activity, "Username or password incorrect", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
        )

        viewModel.data.observe(this,
                Observer {
                    it?.let {
                        Log.v(TAG, "")
                    }
                }
        )
    }


}
