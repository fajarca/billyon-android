package co.id.billyon

import android.net.Uri
import android.opengl.Visibility
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.internal.BottomNavigationMenu
import android.support.design.widget.BottomNavigationView
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavController.OnNavigatedListener {



    lateinit var toolbar : Toolbar
    lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupToolbar()
        setupNavigation()
    }

    private fun setupToolbar() {
        toolbar = findViewById(R.id.toolbar)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        setSupportActionBar(toolbar)
    }

    private fun setupNavigation() {
        val navController = findNavController(R.id.nav_host)

        //When we navigate to a new screen, we would like to update the Toolbar’s title
        setupActionBarWithNavController(navController)
        bottomNavigationView.setupWithNavController(navController)
        navController.addOnNavigatedListener(this)
    }

    override fun onSupportNavigateUp() = findNavController(R.id.nav_host).navigateUp()

    override fun onNavigated(controller: NavController, destination: NavDestination) {
        when(destination.id) {


            R.id.registerFragment, R.id.loginFragment -> {
                toolbar.visibility = View.GONE
                bottomNavigationView.visibility = View.GONE
            }

            else -> {
                toolbar.visibility = View.VISIBLE
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                bottomNavigationView.visibility = View.VISIBLE
            }

        }
    }
}
