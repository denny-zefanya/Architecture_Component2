package zefanya.denny.favourite.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import zefanya.denny.architecturecomponent.ui.home.HomeActivity
import zefanya.denny.favourite.databinding.FragmentFavoriteBinding


class FavoriteFragment : Fragment() {
    private lateinit var viewBinding: FragmentFavoriteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            (activity as HomeActivity).setActionBarTitle("Favourite")
            setViewPager()
        }
    }

    private fun setViewPager() {
        val sectionsPagerAdapter =
            SectionsPagerAdapter(requireContext(), childFragmentManager)
        viewBinding.viewPagerFav.adapter = sectionsPagerAdapter
        viewBinding.tabsFav.setupWithViewPager(viewBinding.viewPagerFav)
    }

}