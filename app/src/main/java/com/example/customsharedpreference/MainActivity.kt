package com.example.customsharedpreference

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.customsharedpreference.databinding.ActivityMainBinding
import com.example.customsharedpreference.roomDbSetup.AppDatabase
import com.example.customsharedpreference.sharedPrefUtils.SharedPrefUtils
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private val appDatabase by lazy { AppDatabase.getInstance(this).objectStoreDao() }
    val context: CoroutineContext = Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        SharedPrefUtils.saveDataString(appDatabase, "stringKey", "test")
        SharedPrefUtils.saveDataLong(appDatabase, "longKey", 1234353535)
        SharedPrefUtils.saveDataInt(appDatabase, "intKey", 5)
        SharedPrefUtils.saveDataFloat(appDatabase, "floatKey", 2.5)

        lifecycleScope.launch(Dispatchers.IO) {
            val value: String = SharedPrefUtils.getDataSting(appDatabase, "stringKey", "0")
            Log.i("appDatabaseResult: ", "key: stringKey value $value")
        }

        lifecycleScope.launch(Dispatchers.IO) {
            val value: Long = SharedPrefUtils.getDataLong(appDatabase, "longKey", 0)
            Log.i("appDatabaseResult: ", "key: longKey value $value")
        }

        lifecycleScope.launch(Dispatchers.IO) {
            val value: Int = SharedPrefUtils.getDataInt(appDatabase, "intKey", 0)
            Log.i("appDatabaseResult: ", "key: intKey value $value")
        }

        lifecycleScope.launch(Dispatchers.IO) {
            val value: Float = SharedPrefUtils.getDataFloat(appDatabase, "floatKey", 0.0F)
            Log.i("appDatabaseResult: ", "key: floatKey value $value")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}