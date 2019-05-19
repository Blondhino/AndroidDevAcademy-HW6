package hr.ferit.brunozoric.taskie.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import hr.ferit.brunozoric.taskie.R
import hr.ferit.brunozoric.taskie.ui.adapters.ViewPagerAdapter
import hr.ferit.brunozoric.taskie.ui.fragments.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_about.*



class AboutFragment: BaseFragment() {



    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_about

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupPager()



    }

    private fun setupPager() {
        viewPager.adapter = ViewPagerAdapter(fragmentManager!!)
        tabLayout.setupWithViewPager(viewPager)

    }


    companion object{
        fun newIstance(): Fragment {
            return AboutFragment()
        }
    }

}


