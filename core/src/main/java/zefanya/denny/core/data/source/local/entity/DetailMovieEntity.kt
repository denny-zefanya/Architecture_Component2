package zefanya.denny.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "detailmovieentities")
data class DetailMovieEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    var id: String = "0",
    @ColumnInfo(name = "poster")
    var poster: String?,
    @ColumnInfo(name = "title")
    var title: String?,
    @ColumnInfo(name = "genre")
    var genre: String?,
    @ColumnInfo(name = "date_release")
    var dateRelease: String?,
    @ColumnInfo(name = "web_page")
    var webPage: String?,
    @ColumnInfo(name = "story_line")
    var storyLine: String?

)