package zefanya.denny.architecturecomponent.ui.content

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.appbar.AppBarLayout
import org.koin.android.viewmodel.ext.android.viewModel
import zefanya.denny.architecturecomponent.R
import zefanya.denny.architecturecomponent.databinding.ActivityContentBinding
import zefanya.denny.core.data.Resource
import zefanya.denny.core.data.source.remote.network.Net
import zefanya.denny.core.domain.model.DetailMovie
import zefanya.denny.core.domain.model.DetailTvShow
import kotlin.math.abs

class ContentActivity : AppCompatActivity(), AppBarLayout.OnOffsetChangedListener {
    companion object {
        const val EXTRA_MOVIE = "extra_movie"
        const val EXTRA_SOURCE_LIST = "extra_source_list"
    }

    private val percentageToShowImage = 20
    private var mMaxScrollSize = 0
    private var mIsImageHidden = false
    private lateinit var contentViewBinding: ActivityContentBinding
    private var detailMovie: DetailMovie? = null
    private var detailTvShow: DetailTvShow? = null
    private val viewModel: ContentViewModel by viewModel()
    private var source: Int = 0
    private var hasClicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        contentViewBinding = ActivityContentBinding.inflate(layoutInflater)
        setContentView(contentViewBinding.root)

        supportActionBar?.hide()

        val index = intent.getStringExtra(EXTRA_MOVIE)
        source = intent.getIntExtra(EXTRA_SOURCE_LIST, 0)
        contentViewBinding.toolbar.setNavigationOnClickListener { onBackPressed() }
        contentViewBinding.appbar.addOnOffsetChangedListener(this)
        showProgressBar(true)

        if (source == 0) {
            viewModel.generateDetailMovie(index.toString()).observe(this, {

                fillDetailMovie(it)
            })

            viewModel.getStatusMovie(index.toString()).observe(this, {
                setFabIcon(it)
            })
        } else {
            viewModel.generateDetailTvShow(index.toString()).observe(this, {
                fillTvShow(it)
            })

            viewModel.getStatusTvShow(index.toString()).observe(this, {
                setFabIcon(it)
            })

        }

        contentViewBinding.fabAddToFavorite.setOnClickListener {
            addToFavorit(index)
        }
    }

    private fun addToFavorit(id: String?) {
        hasClicked = true
        if (source == 0)
            viewModel.updateHeaderMovie(id!!)
        else
            viewModel.updateHeaderTvShow(id!!)
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        if (mMaxScrollSize == 0) mMaxScrollSize = appBarLayout!!.totalScrollRange

        val currentScrollPercentage: Int = (abs(verticalOffset) * 100 / mMaxScrollSize)

        if (currentScrollPercentage >= percentageToShowImage) {
            if (!mIsImageHidden) {
                mIsImageHidden = true
            }
        }

        if (currentScrollPercentage < percentageToShowImage) {
            if (mIsImageHidden) {
                mIsImageHidden = false
            }
        }
    }

    private fun showProgressBar(state: Boolean) {
        contentViewBinding.progressBar.isVisible = state
    }

    private fun fillViewMovie(item: DetailMovie) {
        with(contentViewBinding) {
            with(item) {
                collapsing.title = title
                tvGenre.text = genre.toString().replace("[", "").replace("]", "")
                tvDateRelease.text = dateRelease
                tvDuration.text = webPage
                tvStoryLine.text = storyLine
                Glide.with(this@ContentActivity)
                    .asBitmap()
                    .load(Net.IMAGE_URL + poster)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_baseline_error_24)
                    )
                    .into(object : CustomTarget<Bitmap>() {
                        override fun onResourceReady(
                            resource: Bitmap,
                            transition: Transition<in Bitmap>?
                        ) {
                            contentViewBinding.imageView.setImageBitmap(resource)
                        }

                        override fun onLoadCleared(placeholder: Drawable?) {
                        }
                    })
                showProgressBar(false)
            }
        }
    }

    private fun fillDetailMovie(movie: Resource<List<DetailMovie>>) {
        if (movie.message != null) {
            showProgressBar(false)
            Toast.makeText(applicationContext, "Terjadi Error", Toast.LENGTH_SHORT)
                .show()
        } else if (movie.data == null)
            showProgressBar(true)
        else {
            fillViewMovie(movie.data!![0])
            detailMovie = movie.data!![0]
        }

    }

    private fun fillViewTvShow(item: DetailTvShow) {
        with(contentViewBinding) {
            with(item) {
                collapsing.title = title
                tvGenre.text = genre.toString().replace("[", "").replace("]", "")
                tvDateRelease.text = dateRelease
                tvDuration.text = webPage
                tvStoryLine.text = storyLine
                //setFabIcon(favourite)
                Glide.with(this@ContentActivity)
                    .asBitmap()
                    .load(Net.IMAGE_URL + poster)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_baseline_error_24)
                    )
                    .into(object : CustomTarget<Bitmap>() {
                        override fun onResourceReady(
                            resource: Bitmap,
                            transition: Transition<in Bitmap>?
                        ) {
                            contentViewBinding.imageView.setImageBitmap(resource)
                        }

                        override fun onLoadCleared(placeholder: Drawable?) {

                        }
                    })

                showProgressBar(false)
            }
        }
    }

    private fun fillTvShow(tvshow: Resource<List<DetailTvShow>>) {

        if (tvshow.message != null) {
            showProgressBar(false)
            Toast.makeText(applicationContext, "Terjadi Error", Toast.LENGTH_SHORT)
                .show()
        } else if (tvshow.data == null)
            showProgressBar(true)
        else {
            fillViewTvShow(tvshow.data!![0])
            detailTvShow = tvshow.data!![0]
        }
//        if (tvshow != null) {
//            when (tvshow.status) {
//                Status.LOADING -> showProgressBar(true)
//                Status.SUCCESS -> {
//                    fillViewTvShow(tvshow.data!!)
//                    tvShowEntity = tvshow.data
//                }
//                Status.ERROR -> {
//                    showProgressBar(false)
//                    Toast.makeText(applicationContext, "Terjadi kesalahan", Toast.LENGTH_SHORT)
//                        .show()
//                }
//            }
//
//        }
    }

    private fun setFabIcon(isFav: Boolean?) {
        with(contentViewBinding) {
            if (isFav == true) {
                fabAddToFavorite.setImageResource(R.drawable.ic_favorite_red)
                if (detailMovie != null && source == 0 && hasClicked)
                    Toast.makeText(
                        applicationContext,
                        "${detailMovie!!.title} added to favourite",
                        Toast.LENGTH_SHORT
                    ).show()
                else if (detailTvShow != null && hasClicked)
                    Toast.makeText(
                        applicationContext,
                        "${detailTvShow!!.title} added to favourite",
                        Toast.LENGTH_SHORT
                    ).show()
            } else {
                fabAddToFavorite.setImageResource(R.drawable.ic_favorite_border_red)
                if (detailMovie != null && source == 0 && hasClicked)
                    Toast.makeText(
                        applicationContext,
                        "${detailMovie!!.title} removed from favourite",
                        Toast.LENGTH_SHORT
                    ).show()
                else if (detailTvShow != null && hasClicked)
                    Toast.makeText(
                        applicationContext,
                        "${detailTvShow!!.title} removed to favourite",
                        Toast.LENGTH_SHORT
                    ).show()
            }
        }
    }
}