package zefanya.denny.architecturecomponent.ui.adapter

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import zefanya.denny.architecturecomponent.ui.content.ContentActivity
import zefanya.denny.core.R
import zefanya.denny.core.data.source.remote.network.Net
import zefanya.denny.core.databinding.ItemMovieBinding
import zefanya.denny.core.domain.model.TvShow
import zefanya.denny.core.utils.ShareTvShowsCallBack

class TvShowAdapter(private val callback: ShareTvShowsCallBack, private val source: Int) :
    RecyclerView.Adapter<TvShowAdapter.tvshowsViewHolder>() {

    private var listData = ArrayList<TvShow>()

    inner class tvshowsViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tvshow: TvShow) {
            with(binding) {
                tvItemTitle.text =
                    itemView.resources.getString(R.string.titleItem, tvshow.title, tvshow.year)
                val s = String.format("%,d", tvshow.star.toInt())
                tvRating.text = itemView.resources.getString(R.string.ratingImdb, s)
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, ContentActivity::class.java)
                    intent.putExtra(ContentActivity.EXTRA_MOVIE, tvshow.idMovie)
                    intent.putExtra(ContentActivity.EXTRA_SOURCE_LIST, source)
                    itemView.context.startActivity(intent)
                }
                imgShare.setOnClickListener {
                    callback.onShareClick(tvshow)
                }
                Glide.with(itemView.context)
                    .asBitmap()
                    .load(Net.IMAGE_URL + tvshow.poster)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): tvshowsViewHolder {
        val itemtvshowsBinding = ItemMovieBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return tvshowsViewHolder(itemtvshowsBinding)
    }

    override fun onBindViewHolder(holder: tvshowsViewHolder, position: Int) {
        val tvshow = listData[position]
        if (tvshow != null)
            holder.bind(tvshow)
    }

    fun setData(listTvShow: List<TvShow>?) {
        if (listTvShow == null) return
        listData.clear()
        listData.addAll(listTvShow)
        notifyDataSetChanged()
    }

    override fun getItemCount() = listData.count()
}