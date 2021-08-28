package zefanya.denny.core.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import zefanya.denny.core.data.source.local.entity.DetailMovieEntity
import zefanya.denny.core.data.source.local.entity.DetailTvShowEntity
import zefanya.denny.core.data.source.local.entity.MovieEntity
import zefanya.denny.core.data.source.local.entity.TvShowEntity

@Database(
    entities = [MovieEntity::class, DetailMovieEntity::class, TvShowEntity::class, DetailTvShowEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDataBase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

    companion object {

        @Volatile
        private var INSTANCE: MovieDataBase? = null

        fun getInstance(context: Context): MovieDataBase =
            INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    MovieDataBase::class.java,
                    "Movie.db"
                ).build().apply {
                    INSTANCE = this
                }
            }
    }
}
