package com.training.todoapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.training.todoapp.databinding.ActivityMainBinding
import com.training.todoapp.frags.AddTodoBottomSheet
import com.training.todoapp.frags.ListFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBottomNavigation()
        setupFab()

    }

    private fun setupFab() {
        val bottomSheet = AddTodoBottomSheet()
        binding.fabAddTodo.setOnClickListener {
            bottomSheet.show(supportFragmentManager, null)
            bottomSheet.setOnDismissListener {
                val navHostFragment =
                    supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as? NavHostFragment
                val listFragment = navHostFragment?.childFragmentManager?.fragments?.firstOrNull { it is ListFragment } as? ListFragment
                listFragment?.refreshTodos()
            }
        }
    }

    private fun setupBottomNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNav.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.my_nav)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}