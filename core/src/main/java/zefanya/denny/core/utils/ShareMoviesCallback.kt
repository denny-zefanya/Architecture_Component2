package zefanya.denny.core.utils

import zefanya.denny.core.domain.model.Movie


interface ShareMoviesCallback {
    fun onShareClick(movies: Movie)
}