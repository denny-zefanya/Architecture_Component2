package zefanya.denny.core.utils

import zefanya.denny.core.domain.model.TvShow

interface ShareTvShowsCallBack {
    fun onShareClick(tvShows: TvShow)
}