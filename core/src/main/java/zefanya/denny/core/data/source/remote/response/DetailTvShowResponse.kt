package zefanya.denny.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class DetailTvShowResponse(

    @field:SerializedName("genres")
    val genres: List<GenresItem?>? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("first_air_date")
    val firstAirDate: String? = null,

    @field:SerializedName("overview")
    val overview: String? = null,

    @field:SerializedName("poster_path")
    val posterPath: String? = null,

    @field:SerializedName("original_name")
    val originalName: String? = null,

    @field:SerializedName("homepage")
    val homepage: String? = null,
)


