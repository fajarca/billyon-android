package co.id.billyon.ui.other.main

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import co.id.billyon.R
import co.id.billyon.databinding.ActivityMainBinding
import co.id.billyon.di.Info
import co.id.billyon.util.HELLO
import co.id.billyon.util.LOVE
import co.id.billyon.util.annotation.Use
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasSupportFragmentInjector,NavController.OnNavigatedListener {
    @Inject
    lateinit var dispatchingAndroidInjector : DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    @Inject
    @field:Use(LOVE)
    lateinit var infoLove : Info

    @Inject @field:Use(HELLO)
    lateinit var infoHello : Info

    lateinit var toolbar : Toolbar
    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var binding : ActivityMainBinding
    private lateinit var navController: NavController
    private val isCashier = false

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val navHostFragment= supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        val inflater = navHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.main_nav)
        if (isCashier) {
            binding.bottomNavigationView.inflateMenu(R.menu.menu_cashier)
            graph.startDestination = R.id.fragmentCashierDashboard
        } else {
            binding.bottomNavigationView.inflateMenu(R.menu.menu_owner)
            graph.startDestination = R.id.fragmentDashboard
        }
        navHostFragment.navController.graph = graph
        navController = navHostFragment.navController
        setupToolbar()
        binding.bottomNavigationView.setupWithNavController(navController)
        //navController.addOnNavigatedListener(this)
    }

    private fun setupToolbar() {
        toolbar = binding.toolbar.toolbar
        setSupportActionBar(toolbar)
        setupActionBarWithNavController(navController)
    }

    /*private fun setupNavigation() {
        bottomNavigationView = binding.bottomNavigationView

        if (isCashier) {
            bottomNavigationView.inflateMenu(R.menu.menu_cashier)
            navGraph.startDestination = R.id.fragmentCashierDashboard
        }  else {
            bottomNavigationView.inflateMenu(R.menu.menu_owner)
            navGraph.startDestination = R.id.fragmentDashboard
        }
        navController.graph = navGraph
        bottomNavigationView.setupWithNavController(navController)
        navController.addOnNavigatedListener(this)

    }
*/
    override fun onSupportNavigateUp() = findNavController(R.id.nav_host).navigateUp()

    override fun onNavigated(controller: NavController, destination: NavDestination) {
        when(destination.id) {


            R.id.fragmentRegister, R.id.fragmentLogin, R.id.fragmentAddProduct -> {
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
