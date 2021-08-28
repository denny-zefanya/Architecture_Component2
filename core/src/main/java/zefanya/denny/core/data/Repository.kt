package zefanya.denny.core.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import zefanya.denny.core.data.source.local.LocalDataSource
import zefanya.denny.core.data.source.local.entity.DetailMovieEntity
import zefanya.denny.core.data.source.local.entity.DetailTvShowEntity
import zefanya.denny.core.data.source.local.entity.MovieEntity
import zefanya.denny.core.data.source.local.entity.TvShowEntity
import zefanya.denny.core.data.source.remote.RemoteDataSources
import zefanya.denny.core.data.source.remote.response.*
import zefanya.denny.core.domain.model.DetailMovie
import zefanya.denny.core.domain.model.DetailTvShow
import zefanya.denny.core.domain.model.Movie
import zefanya.denny.core.domain.model.TvShow
import zefanya.denny.core.domain.repository.ICatalogRepository
import zefanya.denny.core.utils.AppExecutors
import zefanya.denny.core.utils.DataMapper

//import zefanya.denny.core.utils.DataMapper

class Repository constructor(
    private val remoteDataSources: RemoteDataSources,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) :
    ICatalogRepository {

    companion object {
        @Volatile
        private var instance: Repository? = null

    }

    override fun getlistMovies(
        sort: String,
        isFav: Boolean
    ): Flow<Resource<List<Movie>>> {
        return object :
            NetworkBoundResource<List<Movie>, List<ResultsItem>?>() {
            override fun loadFromDB(): Flow<List<Movie>> {

                return localDataSource.getMovieList(
                    sort,
                    isFav,
                    "movieentities"
                ).map {
                    DataMapper.mapMovieEntityToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Movie>?): Boolean {
                return data == null || data.isEmpty()
            }

            override suspend fun createCall(): Flow<ApiResponse<List<ResultsItem>?>> {
                return remoteDataSources.getListMovies()
            }

            override suspend fun saveCallResult(data: List<ResultsItem>?) {

                data!!.let {
                    val listResult = ArrayList<MovieEntity>()
                    for (item in it) {
                        val popular = item.popularity.toString().replace(".", "")
                        val value = MovieEntity(
                            item.id.toString(),
                            item.title,
                            item.releaseDate.substring(0, 4).toInt(),
                            popular.toDouble(),
                            item.posterPath
                        )
                        listResult.add(value)
                    }
                    localDataSource.insertHeaderMovie(listResult)
                }
            }
        }.asFlow()
    }

    override fun getListTvShows(
        sort: String,
        isFav: Boolean
    ): Flow<Resource<List<TvShow>>> {
        return object :
            NetworkBoundResource<List<TvShow>, List<ResultsItemTv>?>() {
            override fun loadFromDB(): Flow<List<TvShow>> {
                return localDataSource.getTvShowList(
                    sort,
                    isFav,
                    "tvshowentities"
                ).map {
                    DataMapper.mapTvShowEntityToDomain(it)
                }
            }

            override fun shouldFetch(data: List<TvShow>?): Boolean {
                return data == null || data.isEmpty()
            }

            override suspend fun createCall(): Flow<ApiResponse<List<ResultsItemTv>?>> {
                return remoteDataSources.getListTvShows()
            }

            override suspend fun saveCallResult(data: List<ResultsItemTv>?) {

                data!!.let {
                    val resultList = ArrayList<TvShowEntity>()

                    for (item in it) {
                        val popular = item.popularity.toString().replace(".", "")
                        val value = TvShowEntity(
                            item.id.toString(),
                            item.name,
                            item.firstAirDate.substring(0, 4).toInt(),
                            popular.toDouble(),
                            item.posterPath
                        )
                        resultList.add(value)
                    }
                    localDataSource.insertHeaderTvShow(resultList)
                }
            }
        }.asFlow()
    }

    override fun getDetailMovie(id: String): Flow<Resource<List<DetailMovie>>> {
        return object :
            NetworkBoundResource<List<DetailMovie>, DetailMovieResponse?>() {
            override fun loadFromDB(): Flow<List<DetailMovie>> {

                return localDataSource.getDetailMovie(id).map {
                    DataMapper.mapDetailMovieEntityToDomain(it)
                }
            }


            override fun shouldFetch(data: List<DetailMovie>?): Boolean {
                return data.isNullOrEmpty()
            }

            override suspend fun createCall(): Flow<ApiResponse<DetailMovieResponse?>> {
                return remoteDataSources.getDetailMovie(id)
            }

            override suspend fun saveCallResult(data: DetailMovieResponse?) {
                with(data!!) {
                    val listGenres = ArrayList<String>()
                    for (genre in genres!!) {
                        listGenres.add(genre?.name.toString())
                        val value = DetailMovieEntity(
                            id,
                            posterPath,
                            title,
                            listGenres.joinToString(","),
                            releaseDate,
                            homepage,
                            overview
                        )
                        localDataSource.insertDetailMovie(value)
                    }
                }
            }

        }.asFlow()
    }

    override fun getDetailTvShow(id: String): Flow<Resource<List<DetailTvShow>>> {
        return object :
            NetworkBoundResource<List<DetailTvShow>, DetailTvShowResponse>() {
            override fun loadFromDB(): Flow<List<DetailTvShow>> {
                return localDataSource.getDetailTvShow(id)
                    .map { DataMapper.mapDetailTvShowEntityToDomain(it) }
            }

            override fun shouldFetch(data: List<DetailTvShow>?): Boolean {
                return data.isNullOrEmpty()
            }

            override suspend fun createCall(): Flow<ApiResponse<DetailTvShowResponse>> {
                return remoteDataSources.getDetailTvShow(id)
            }

            override suspend fun saveCallResult(data: DetailTvShowResponse) {
                with(data) {
                    val listGenres = ArrayList<String>()
                    for (genre in this.genres!!) {
                        listGenres.add(genre?.name.toString())
                    }
                    val value = DetailTvShowEntity(
                        id,
                        posterPath,
                        originalName,
                        listGenres.joinToString(","),
                        firstAirDate,
                        homepage,
                        overview
                    )
                    localDataSource.insertDetailTvShow(value)
                }
            }

        }.asFlow()
    }

    override fun updateHeaderMovie(idMovie: String) {
        appExecutors.diskIO().execute { localDataSource.updateHeaderMovie(idMovie) }
    }

    override fun getStatusMovie(idMovie: String): Flow<Boolean> =
        localDataSource.getStatusMovie(idMovie)

    override fun updateHeaderTvShow(idTv: String) {
        appExecutors.diskIO().execute { localDataSource.updateHeaderTvShow(idTv) }
    }

    override fun getStatusTvShow(idTv: String): Flow<Boolean> =
        localDataSource.getStatusTvShow(idTv)

    override fun deleteMovie() {
        appExecutors.diskIO().execute { localDataSource.deleteMovie() }
    }

    override fun deleteTvShow() {
        appExecutors.diskIO().execute { localDataSource.deleteMovie() }
    }

    override fun deleteDetailMovie() {
        appExecutors.diskIO().execute { localDataSource.deleteDetailMovie() }
    }

    override fun deleteDetailTvShow() {
        appExecutors.diskIO().execute { localDataSource.deleteDetailTvShow() }
    }

    override fun deleteFavoriteMovie() {
        appExecutors.diskIO().execute { localDataSource.deleteFavoriteMovie() }
    }

    override fun deleteFavoritTvShow() {
        appExecutors.diskIO().execute { localDataSource.deleteFavoritTvShow() }
    }

}