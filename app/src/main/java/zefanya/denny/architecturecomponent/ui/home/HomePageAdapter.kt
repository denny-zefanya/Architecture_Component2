package zefanya.denny.architecturecomponent.ui.home

//class HomePageAdapter(
//    private val mContext: Context,
//    fm: FragmentManager,
//    private val isFav: Boolean
//) : FragmentPagerAdapter(
//    fm,
//    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
//) {
//    companion object {
//        @StringRes
//        private val TAB_TITLE = intArrayOf(R.string.Movies, R.string.tvShows)
//    }
//
//    override fun getCount(): Int = 2
//
//    override fun getItem(position: Int): Fragment =
//        when (position) {
//            0 -> MoviesFragment(false)
//            1 -> TvShowsFragment(isFav)
//            else -> Fragment()
//        }
//
//    override fun getPageTitle(position: Int): CharSequence? =
//        mContext.getString(TAB_TITLE[position])
//}