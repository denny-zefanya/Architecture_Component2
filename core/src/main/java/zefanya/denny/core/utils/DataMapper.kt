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


    fun mapDomainToDetailMovieEntity(input: DetailMovie): DetailMovieEntity {
        return DetailMovieEntity(
            input.id,
            input.poster,
            input.title,
            input.genre,
            input.dateRelease,
            input.webPage,
            input.storyLine
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


    fun mapDomainToTvShowEntity(input: DetailTvShow): DetailTvShow {
        return DetailTvShow(
            input.id,
            input.poster,
            input.title,
            input.genre,
            input.dateRelease,
            input.webPage,
            input.storyLine
        )
    }

    fun mapMovieEntityToDomain(input: List<MovieEntity>): List<Movie> = input.map {
        Movie(it.idMovie, it.title, it.year, it.star, it.poster, it.favourite)
    }

    fun mapDomainToMovieEntity(input: List<Movie>): List<MovieEntity> = input.map {
        MovieEntity(it.idMovie, it.title, it.year, it.star, it.poster, it.favourite)
    }

    fun mapTvShowEntityToDomain(input: List<TvShowEntity>): List<TvShow> = input.map {
        TvShow(it.idMovie, it.title, it.year, it.star, it.poster, it.favourite)
    }

    fun mapDomainToTvShowEntity(input: List<TvShow>): List<TvShowEntity> = input.map {
        TvShowEntity(it.idMovie, it.title, it.year, it.star, it.poster, it.favourite)
    }
}