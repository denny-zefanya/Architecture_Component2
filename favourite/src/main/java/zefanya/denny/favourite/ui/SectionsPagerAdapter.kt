package zefanya.denny.favourite.ui

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import zefanya.denny.architecturecomponent.ui.movies.MoviesFragment
import zefanya.denny.architecturecomponent.ui.tvShows.TvShowsFragment
import zefanya.denny.favourite.R

class SectionsPagerAdapter(
    private val mContext: Context,
    fm: FragmentManager
) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.Movies, R.string.tvShows)
    }

    override fun getItem(position: Int): Fragment =
        when (position) {
            0 -> MoviesFragment(true)
            1 -> TvShowsFragment(true)
            else -> Fragment()
        }

    override fun getPageTitle(position: Int): CharSequence? =
        mContext.resources.getString(TAB_TITLES[position])

    override fun getCount(): Int = 2

}