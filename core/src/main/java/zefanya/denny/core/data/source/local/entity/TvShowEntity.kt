package zefanya.denny.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tvshowentities")
data class TvShowEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id_movie")
    var idMovie: String = "0",
    @ColumnInfo(name = "title")
    var title: String?,
    @ColumnInfo(name = "year")
    var year: Int?,
    @ColumnInfo(name = "star")
    var star: Double,
    @ColumnInfo(name = "poster")
    var poster: String?,
    @ColumnInfo(name = "favourite")
    var favourite: Boolean = false
)
