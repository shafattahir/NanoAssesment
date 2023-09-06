package shafat.projects.assesment.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import shafat.projects.assesment.R
import shafat.projects.assesment.databinding.ActivityMainBinding

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {
    lateinit var bottomNav: BottomNavigationView
    lateinit var binding: ActivityMainBinding

    private lateinit var controller: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private var listener = NavController.OnDestinationChangedListener { _, destination, _ ->




    }
}