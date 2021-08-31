package zefanya.denny.architecturecomponent.ui.adapter

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import zefanya.denny.architecturecomponent.R
import zefanya.denny.architecturecomponent.databinding.ItemMovieBinding
import zefanya.denny.architecturecomponent.ui.content.ContentActivity
import zefanya.denny.core.data.source.local.entity.MovieEntity
import zefanya.denny.core.data.source.remote.network.Net
import zefanya.denny.core.domain.model.Movie
import zefanya.denny.core.utils.ShareMoviesCallback

class MovieAdapter(private val callback: ShareMoviesCallback, private val source: Int) :
    RecyclerView.Adapter<MovieAdapter.MoviesViewHolder>() {

    private var listData = ArrayList<Movie>()

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieEntity>() {
            override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem.idMovie == newItem.idMovie
            }

            override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class MoviesViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            with(binding) {
                tvItemTitle.text =
                    itemView.resources.getString(R.string.titleItem, movie.title, movie.year)
                val s = String.format("%,d", movie.star.toInt())
                tvRating.text = itemView.resources.getString(R.string.ratingImdb, s)
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, ContentActivity::class.java)
                    intent.putExtra(ContentActivity.EXTRA_MOVIE, movie.idMovie)
                    intent.putExtra(ContentActivity.EXTRA_SOURCE_LIST, source)
                    itemView.context.startActivity(intent)
                }
                imgShare.setOnClickListener {
                    callback.onShareClick(movie)
                }
                Glide.with(itemView.context)
                    .asBitmap()
                    .load(Net.IMAGE_URL + movie.poster)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_baseline_error_24)
                    )
                    .into(object : CustomTarget<Bitmap>() {
                        override fun onResourceReady(
                            resource: Bitmap,
                            transition: Transition<in Bitmap>?
                        ) {
                            imgMovie.setImageBitmap(resource)

                            Palette.from(resource).generate { palette ->
                                val defValue = itemView.resources.getColor(
                                    R.color.dark,
                                    itemView.context.theme
                                )
                                cardItem.setCardBackgroundColor(
                                    palette?.getDarkMutedColor(defValue) ?: defValue
                                )
                            }
                        }

                        override fun onLoadCleared(placeholder: Drawable?) {
                        }
                    })

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val itemMoviesBinding = ItemMovieBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return MoviesViewHolder(itemMoviesBinding)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val movie = listData[position]
        if (movie != null)
            holder.bind(movie)
    }

    fun setData(newListData: List<Movie>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = listData.count()

}

