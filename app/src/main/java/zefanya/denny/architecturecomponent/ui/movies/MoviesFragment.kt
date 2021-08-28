package zefanya.denny.architecturecomponent.ui.movies

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.android.viewmodel.ext.android.viewModel
import zefanya.denny.architecturecomponent.R
import zefanya.denny.architecturecomponent.databinding.FragmentMoviesBinding
import zefanya.denny.architecturecomponent.ui.adapter.MovieAdapter
import zefanya.denny.architecturecomponent.ui.home.HomeActivity
import zefanya.denny.core.data.Resource
import zefanya.denny.core.domain.model.Movie
import zefanya.denny.core.utils.ShareMoviesCallback
import zefanya.denny.core.utils.SortUtils

//class MoviesFragment(private val isFav: Boolean) : Fragment(), ShareMoviesCallback {
class MoviesFragment() : Fragment(), ShareMoviesCallback {
    constructor(isFavorite: Boolean) : this() {
        isFav = isFavorite
    }

    private var viewBinding: FragmentMoviesBinding? = null
    private val viewModel: MoviesViewModel by viewModel()
    private lateinit var moviesAdapter: MovieAdapter
    private var isFav = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewBinding = FragmentMoviesBinding.inflate(layoutInflater, container, false)
        viewBinding?.progressBar?.visibility = View.VISIBLE
        setHasOptionsMenu(true)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            (activity as HomeActivity).setActionBarTitle("Movies")
            moviesAdapter = MovieAdapter(this, 0)
            viewModel.getListMovies(SortUtils.MOST_POPULAR, isFav)
                .observe(viewLifecycleOwner, movieObserver)

            with(viewBinding?.rvMovies) {
                this?.layoutManager = LinearLayoutManager(context)
                this?.setHasFixedSize(true)
                this?.adapter = moviesAdapter

            }
        }
    }

    override fun onShareClick(movies: Movie) {
        if (activity != null) {
            val mimeType = "text/plain"
            ShareCompat.IntentBuilder
                .from(requireActivity())
                .setType(mimeType)
                .setChooserTitle("Bagikan aplikasi ini sekarang.")
                .setText(movies.title)
                .startChooser()
        }
    }

    private val movieObserver = Observer<Resource<List<Movie>>> { movies ->
        if (movies != null) {
            if (movies.message != null) {
                viewBinding?.progressBar?.visibility = View.GONE
                viewBinding?.rvMovies?.visibility = View.VISIBLE
                Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
            } else if (movies.data == null)
                viewBinding?.progressBar?.visibility = View.VISIBLE
            else {
                viewBinding?.progressBar?.visibility = View.GONE
                if (movies.data!!.isEmpty()) {
                    viewBinding?.rvMovies?.visibility = View.GONE
                    viewBinding?.tvEmptyFavourite?.visibility = View.VISIBLE
                } else
                    moviesAdapter.setData(movies.data)
            }

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
        viewModel.getListMovies(sort, isFav).observe(viewLifecycleOwner, movieObserver)
        item.isChecked = true
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getListMovies(SortUtils.MOST_POPULAR, isFav)
            .observe(viewLifecycleOwner, movieObserver)
    }
}