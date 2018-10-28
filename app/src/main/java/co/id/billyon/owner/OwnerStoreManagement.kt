package co.id.billyon.owner


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.id.billyon.R

/**
 * A simple [Fragment] subclass.
 *
 */
class OwnerStoreManagement : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_owner_store_management, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*btnAddStore.setOnClickListener {


            val action = OwnerStoreManagementDirections.openAddStoreFragmentAction()
            action.setStoreId(97)

            val navController = findNavController(view )
            navController.navigate(action)
        }*/
    }


}
