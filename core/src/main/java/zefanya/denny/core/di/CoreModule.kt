package zefanya.denny.core.di

import androidx.room.Room
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import zefanya.denny.core.data.Repository
import zefanya.denny.core.data.source.local.LocalDataSource
import zefanya.denny.core.data.source.local.room.MovieDataBase
import zefanya.denny.core.data.source.remote.RemoteDataSources
import zefanya.denny.core.data.source.remote.network.ApiService
import zefanya.denny.core.data.source.remote.network.Net
import zefanya.denny.core.domain.repository.ICatalogRepository
import zefanya.denny.core.utils.AppExecutors
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<MovieDataBase>().movieDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            MovieDataBase::class.java, "Movie.db"
        ).fallbackToDestructiveMigration().build()
    }
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(Net.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSources(get()) }
    factory { AppExecutors() }
    single<ICatalogRepository> {
        Repository(
            get(),
            get(),
            get()
        )
    }
}