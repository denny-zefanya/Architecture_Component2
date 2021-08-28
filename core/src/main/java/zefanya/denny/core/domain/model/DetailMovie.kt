package zefanya.denny.core.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailMovie(
    val id: String,
    val poster: String?,
    val title: String?,
    val genre: String?,
    val dateRelease: String?,
    val webPage: String?,
    val storyLine: String?
) : Parcelable
