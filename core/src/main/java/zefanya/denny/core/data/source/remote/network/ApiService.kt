package zefanya.denny.core.data.source.remote.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import zefanya.denny.core.data.source.remote.response.DetailMovieResponse
import zefanya.denny.core.data.source.remote.response.DetailTvShowResponse
import zefanya.denny.core.data.source.remote.response.MoviesResponse
import zefanya.denny.core.data.source.remote.response.TvShowsResponse

interface ApiService {

    @GET("movie/top_rated")
    suspend fun getListMovies(@Query("api_key") apikey: String): MoviesResponse

    @GET("tv/top_rated")
    suspend fun getListTvShows(@Query("api_key") apikey: String): TvShowsResponse

    @GET("movie/{id}")
    suspend fun getDetailMovie(
        @Path("id") id: String,
        @Query("api_key") apikey: String
    ): DetailMovieResponse

    @GET("tv/{id}")
    suspend fun getDetailTvShow(
        @Path("id") id: String,
        @Query("api_key") apikey: String
    ): DetailTvShowResponse

}