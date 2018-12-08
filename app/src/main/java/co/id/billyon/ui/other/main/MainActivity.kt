package co.id.billyon.ui.other.main

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
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
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import co.id.billyon.R
import co.id.billyon.databinding.ActivityMainBinding
import co.id.billyon.di.Info
import co.id.billyon.ui.other.login.LoginViewModel
import co.id.billyon.util.HELLO
import co.id.billyon.util.LOVE
import co.id.billyon.util.annotation.Use
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {
    @Inject
    lateinit var dispatchingAndroidInjector : DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    private lateinit var toolbar : Toolbar
    private lateinit var binding : ActivityMainBinding
    private lateinit var navController: NavController
    private val isCashier = true
    private val isLoggedIn = true
    private lateinit var viewModel: LoginViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel::class.java)

        val navHostFragment= supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        val inflater = navHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.main_nav)

        viewModel.isLoggedIn = isLoggedIn

        if (viewModel.isLoggedIn) {
            if (isCashier) {
                binding.bottomNavigationView.inflateMenu(R.menu.menu_cashier)
                graph.startDestination = R.id.fragmentCashierDashboard
            } else {
                binding.bottomNavigationView.inflateMenu(R.menu.menu_owner)
                graph.startDestination = R.id.fragmentDashboard
            }
        } else {
            binding.bottomNavigationView.inflateMenu(R.menu.menu_owner)
            graph.startDestination = R.id.fragmentLogin
        }

        navHostFragment.navController.graph = graph
        navController = navHostFragment.navController

        setupToolbar()
        setupBottomNavigation()
    }

    private fun setupToolbar() {
        toolbar = binding.toolbar.toolbar
        setSupportActionBar(toolbar)
        setupActionBarWithNavController(navController)
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigationView.setupWithNavController(navController)
        navController.addOnNavigatedListener(navigationListener)
    }

    val navigationListener = NavController.OnNavigatedListener { controller, destination ->
        when(destination.id) {


            R.id.fragmentRegister, R.id.fragmentLogin -> {
                toolbar.visibility = View.GONE
                binding.bottomNavigationView.visibility = View.GONE
            }

            R.id.fragmentAddProduct -> {
                toolbar.visibility = View.VISIBLE
                binding.bottomNavigationView.visibility = View.GONE
            }

            R.id.fragmentProductList -> {
                toolbar.visibility = View.VISIBLE
                binding.bottomNavigationView.visibility = View.GONE
            }

            else -> {
                toolbar.visibility = View.VISIBLE
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                binding.bottomNavigationView.visibility = View.VISIBLE
            }

        }
    }
    override fun onSupportNavigateUp() = navController.navigateUp()

}
