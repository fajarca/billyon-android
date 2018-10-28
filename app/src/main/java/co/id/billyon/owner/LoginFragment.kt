package co.id.billyon.owner


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import co.id.billyon.owner.LoginFragmentDirections
import co.id.billyon.R
import kotlinx.android.synthetic.main.fragment_login.*



class LoginFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        labelRegister.setOnClickListener {
            val action = LoginFragmentDirections.openRegisterFragmentAction()
            val navController = Navigation.findNavController(view)
            navController.navigate(action)
        }
        btnLogin.setOnClickListener {
            val action = LoginFragmentDirections.openDashboardFragmentAction()
            val navController = Navigation.findNavController(view)
            navController.navigate(action)
        }
    }


}
