package zefanya.denny.core.utils

import zefanya.denny.core.data.source.local.entity.DetailMovieEntity
import zefanya.denny.core.data.source.local.entity.DetailTvShowEntity
import zefanya.denny.core.data.source.local.entity.MovieEntity
import zefanya.denny.core.data.source.local.entity.TvShowEntity
import zefanya.denny.core.domain.model.DetailMovie
import zefanya.denny.core.domain.model.DetailTvShow
import zefanya.denny.core.domain.model.Movie
import zefanya.denny.core.domain.model.TvShow


object DataMapper {
    fun mapDetailMovieEntityToDomain(input: List<DetailMovieEntity>): List<DetailMovie> =
        input.map {
            DetailMovie(
                it.id,
                it.poster,
                it.title,
                it.genre,
                it.dateRelease,
                it.webPage,
                it.storyLine
            )
        }


    fun mapDetailTvShowEntityToDomain(input: List<DetailTvShowEntity>): List<DetailTvShow> =
        input.map {
            DetailTvShow(
                it.id,
                it.poster,
                it.title,
                it.genre,
                it.dateRelease,
                it.webPage,
                it.storyLine
            )
        }


    fun mapMovieEntityToDomain(input: List<MovieEntity>): List<Movie> = input.map {
        Movie(it.idMovie, it.title, it.year, it.star, it.poster, it.favourite)
    }

    fun mapTvShowEntityToDomain(input: List<TvShowEntity>): List<TvShow> = input.map {
        TvShow(it.idMovie, it.title, it.year, it.star, it.poster, it.favourite)
    }

}