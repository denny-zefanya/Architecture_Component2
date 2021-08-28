package zefanya.denny.core.data.source.remote

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import zefanya.denny.core.data.source.remote.network.ApiService
import zefanya.denny.core.data.source.remote.network.Net
import zefanya.denny.core.data.source.remote.response.*

class RemoteDataSources constructor(private val apiService: ApiService) {


    //fun getListMovies(callback: LoadMoviesCallback)
    suspend fun getListMovies(): Flow<ApiResponse<List<ResultsItem>?>> {
        return flow {
            try {
                val response = apiService.getListMovies(Net.API_KEY)
                val dataArray = response.results
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(dataArray))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }

        }.flowOn(Dispatchers.IO)
    }

    //fun getListTvShows(callback: LoadTvShowsCallback)
    fun getListTvShows(): Flow<ApiResponse<List<ResultsItemTv>?>> {
        return flow {
            try {
                val response = apiService.getListTvShows(Net.API_KEY)
                val dataArray = response.results
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(dataArray))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }

        }.flowOn(Dispatchers.IO)
    }

    //fun getDetailMovie(id: String, callback: LoadDetailMovieCallback)
    fun getDetailMovie(id: String): Flow<ApiResponse<DetailMovieResponse?>> {

        return flow {
            try {
                val response = apiService.getDetailMovie(id, Net.API_KEY)
                val data = response
                emit(ApiResponse.Success(data))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }

        }.flowOn(Dispatchers.IO)
    }

    fun getDetailTvShow(id: String): Flow<ApiResponse<DetailTvShowResponse>> {
        return flow {
            try {
                val response = apiService.getDetailTvShow(id, Net.API_KEY)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }

        }.flowOn(Dispatchers.IO)
    }
}