package co.id.billyon.ui.owner


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import co.id.billyon.R


/**
 * A simple [Fragment] subclass.
 *
 */
class AddStoreFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_store, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        arguments?.let {
            val passedArgument = AddStoreFragmentArgs.fromBundle(it)
            val storeId = passedArgument.storeId
            //tvStoreId.text = "Passed ID : $storeId"
        }
    }

}
