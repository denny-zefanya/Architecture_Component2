package zefanya.denny.architecturecomponent.ui.tvShows

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.android.viewmodel.ext.android.viewModel
import zefanya.denny.architecturecomponent.R
import zefanya.denny.architecturecomponent.databinding.FragmentTvShowsBinding
import zefanya.denny.architecturecomponent.ui.adapter.TvShowAdapter
import zefanya.denny.architecturecomponent.ui.home.HomeActivity
import zefanya.denny.core.data.Resource
import zefanya.denny.core.domain.model.TvShow
import zefanya.denny.core.utils.ShareTvShowsCallBack
import zefanya.denny.core.utils.SortUtils

//class TvShowsFragment(private val isFav: Boolean) : Fragment(), ShareTvShowsCallBack {
class TvShowsFragment() : Fragment(), ShareTvShowsCallBack {

    constructor(isFavourite: Boolean) : this() {
        this.isFav = isFavourite
    }

    private lateinit var viewBinding: FragmentTvShowsBinding
    private val viewModel: TvShowsViewModel by viewModel()
    private lateinit var tvShowAdapter: TvShowAdapter
    private var isFav = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentTvShowsBinding.inflate(
            layoutInflater, container, false
        )
        setHasOptionsMenu(true)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            (activity as HomeActivity).setActionBarTitle("Tv Shows")
            tvShowAdapter = TvShowAdapter(this, 1)
            viewModel.getListTvShows(SortUtils.MOST_POPULAR, isFav)
                .observe(viewLifecycleOwner, tvShowObserver)

            with(viewBinding.rvTvShows) {
                this.layoutManager = LinearLayoutManager(context)
                this.setHasFixedSize(true)
                this.adapter = tvShowAdapter
            }
        }
    }

    private val tvShowObserver = Observer<Resource<List<TvShow>>> { tvShow ->
        if (tvShow != null) {
            if (tvShow.message != null) {
                viewBinding.progressBar.visibility = View.GONE
                Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
            } else if (tvShow.data == null)
                viewBinding.progressBar.visibility = View.VISIBLE
            else {
                viewBinding.progressBar.visibility = View.GONE
                if (tvShow.data!!.isEmpty()) {
                    viewBinding.tvEmptyFavouriteTvshow.visibility = View.GONE
                    viewBinding.rvTvShows.visibility = View.GONE
                } else
                    tvShowAdapter.setData(tvShow.data)
            }

        }
    }

    override fun onShareClick(tvShows: TvShow) {
        if (activity != null) {
            val mimeType = "text/plain"
            ShareCompat.IntentBuilder
                .from(requireActivity())
                .setType(mimeType)
                .setChooserTitle("Bagikan aplikasi ini sekarang.")
                .setText(tvShows.title)
                .startChooser()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var sort = ""
        when (item.itemId) {
            R.id.action_newest -> sort = SortUtils.MOST_POPULAR
            R.id.action_oldest -> sort = SortUtils.LEAST_POPULAR
            R.id.action_random -> sort = SortUtils.RANDOM
        }
        viewModel.getListTvShows(sort, isFav).observe(viewLifecycleOwner, tvShowObserver)
        item.isChecked = true
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getListTvShows(SortUtils.MOST_POPULAR, isFav)
            .observe(viewLifecycleOwner, tvShowObserver)
    }
}