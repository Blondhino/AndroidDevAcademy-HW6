package hr.ferit.brunozoric.taskie.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import hr.ferit.brunozoric.taskie.ui.fragments.AboutAplicationFragment

class ViewPagerAdapter(manager :FragmentManager) : FragmentStatePagerAdapter(manager) {


    companion object{
        const val NUM_OF_PAGES =2
        const val PAGE_ONE_TITLE = "About Application"
        const val PAGE_TWO_TITLE = "About Author"
    }

    private val frags = mutableListOf<Fragment>(AboutAplicationFragment(),AboutAplicationFragment())
    private val titles = mutableListOf(PAGE_ONE_TITLE, PAGE_TWO_TITLE)

    override fun getItem(position: Int): Fragment {
        return frags[position]
    }

    override fun getPageTitle(position :Int) : String{
        return titles[position]
    }

    override fun getCount(): Int {
        return NUM_OF_PAGES
    }
}