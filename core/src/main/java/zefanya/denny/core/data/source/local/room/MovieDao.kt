package zefanya.denny.core.data.source.local.room

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import kotlinx.coroutines.flow.Flow
import zefanya.denny.core.data.source.local.entity.DetailMovieEntity
import zefanya.denny.core.data.source.local.entity.DetailTvShowEntity
import zefanya.denny.core.data.source.local.entity.MovieEntity
import zefanya.denny.core.data.source.local.entity.TvShowEntity

@Dao
interface MovieDao {

    @RawQuery(observedEntities = [MovieEntity::class])
    fun getTopRatedMovieList(query: SupportSQLiteQuery): Flow<List<MovieEntity>>

    @Query("SELECT * FROM detailmovieentities where id = :id")
    fun getDetailMovie(id: String): Flow<List<DetailMovieEntity>>

    @RawQuery(observedEntities = [MovieEntity::class])
    fun getTopRatedTvShowList(query: SupportSQLiteQuery): Flow<List<TvShowEntity>>

    @Query("SELECT * FROM detailtvshowentities WHERE id = :id")
    fun getDetailTvShow(id: String): Flow<List<DetailTvShowEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHeaderMovie(listEntity: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDetailMovie(detailMovieEntity: DetailMovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHeaderTvShow(listEntity: List<TvShowEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDetailTvShow(detailTvShowEntity: DetailTvShowEntity)

    @Query("UPDATE movieentities set favourite = 1-(favourite & 1) WHERE id_movie = :idMovie")
    fun updateHeaderMovie(idMovie: String)

    @Query("UPDATE tvshowentities set favourite = 1-(favourite & 1) WHERE id_movie = :idTv")
    fun updateHeaderTvShow(idTv: String)

    @Query("SELECT favourite FROM movieentities WHERE id_movie = :idMovie")
    fun getStatusMovie(idMovie: String): Flow<Boolean>

    @Query("SELECT favourite FROM tvshowentities WHERE id_movie = :idTv")
    fun getStatusTvShow(idTv: String): Flow<Boolean>

    @Query("DELETE FROM movieentities")
    fun deleteMovie()

    @Query("DELETE FROM detailmovieentities")
    fun deleteDetailMovie()

    @Query("DELETE FROM tvshowentities")
    fun deleteTvShow()

    @Query("DELETE FROM detailtvshowentities")
    fun deleteDetailTvShow()

    @Query("UPDATE movieentities SET favourite = 0 WHERE favourite = 1")
    fun deleteFavouriteMovie()

    @Query("UPDATE tvshowentities SET favourite = 0 WHERE favourite = 1")
    fun deleteFavouriteTvShow()

}