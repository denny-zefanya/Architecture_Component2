package zefanya.denny.architecturecomponent.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import org.koin.android.viewmodel.ext.android.viewModel
import zefanya.denny.architecturecomponent.databinding.FragmentSettingBinding
import zefanya.denny.architecturecomponent.ui.home.HomeActivity

class SettingFragment : Fragment() {

    private lateinit var viewBinding: FragmentSettingBinding
    private val viewModel: SettingViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentSettingBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            (activity as HomeActivity).setActionBarTitle("Setting")
            viewBinding.btDeleteMovie.setOnClickListener {
                viewModel.deleteMovie()
                viewModel.deleteDetailMovie()
                Toast.makeText(context, "movie dihapus", Toast.LENGTH_SHORT)
                    .show()
            }

            viewBinding.btDeleteTvshow.setOnClickListener {
                viewModel.deleteTvShow()
                viewModel.deleteDetailTvShow()
                Toast.makeText(context, "Tv Show dihapus", Toast.LENGTH_SHORT).show()
            }

            viewBinding.btDeleteFavoriteMovie.setOnClickListener {
                viewModel.deleteFavouriteMovie()
                Toast.makeText(context, "Semua data favorit movie dihapus", Toast.LENGTH_SHORT)
                    .show()
            }

            viewBinding.btDeleteFavoriteTvshow.setOnClickListener {
                viewModel.deleteFavouriteTvShow()
                Toast.makeText(context, "semua data favorit tv show dihapus", Toast.LENGTH_SHORT)
                    .show()
            }

        }

    }

}