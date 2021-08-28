package zefanya.denny.core.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val idMovie: String,
    val title: String?,
    val year: Int?,
    val star: Double,
    val poster: String?,
    val favourite: Boolean
) : Parcelable
