package co.id.billyon.ui

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import co.id.billyon.R
import co.id.billyon.databinding.ActivityMainBinding
import co.id.billyon.di.Info
import co.id.billyon.util.HELLO
import co.id.billyon.util.LOVE
import co.id.billyon.util.annotation.Use
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : AppCompatActivity(), NavController.OnNavigatedListener {

    @Inject
    @field:Use(LOVE)
    lateinit var infoLove : Info

    @Inject @field:Use(HELLO)
    lateinit var infoHello : Info

    lateinit var toolbar : Toolbar
    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setupToolbar()
        setupNavigation()

        val text = "${infoHello.text} ${infoLove.text}"
        Log.v("Ha",text)
    }

    private fun setupToolbar() {
        toolbar = binding.toolbar.toolbar
        setSupportActionBar(toolbar)
    }

    private fun setupNavigation() {
        val navController = findNavController(R.id.nav_host)
        bottomNavigationView = binding.bottomNavigationView
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
