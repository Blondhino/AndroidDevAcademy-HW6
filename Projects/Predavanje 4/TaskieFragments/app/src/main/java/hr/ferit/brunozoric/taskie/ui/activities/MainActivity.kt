package hr.ferit.brunozoric.taskie.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import hr.ferit.brunozoric.taskie.R
import hr.ferit.brunozoric.taskie.ui.activities.base.BaseActivity
import hr.ferit.brunozoric.taskie.ui.fragments.AboutFragment
import hr.ferit.brunozoric.taskie.ui.fragments.TasksFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_tasks.*

class MainActivity : AppCompatActivity() {
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {item->
        when(item.itemId){
            R.id.nav_home -> {
                println("home pressed")
                replaceFragment(TasksFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_about -> {
                println("map pressed")
                replaceFragment(AboutFragment())
                return@OnNavigationItemSelectedListener true
            }

        }

        false

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        replaceFragment(TasksFragment())


    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.commit()
    }
}
