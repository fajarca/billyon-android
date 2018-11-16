package co.id.billyon.ui.login


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import co.id.billyon.R
import co.id.billyon.di.Info
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

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
        val text = "${infoHello.text} ${infoLove.text}"
        Log.v("Ha",text)
    }
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
