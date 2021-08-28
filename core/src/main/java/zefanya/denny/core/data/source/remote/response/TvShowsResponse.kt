package zefanya.denny.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class TvShowsResponse(

    @field:SerializedName("page")
    val page: Int,

    @field:SerializedName("total_pages")
    val totalPages: Int,

    @field:SerializedName("results")
    val results: List<ResultsItemTv>,

    @field:SerializedName("total_results")
    val totalResults: Int
)

data class ResultsItemTv(
    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("poster_path")
    val posterPath: String,

    @field:SerializedName("first_air_date")
    val firstAirDate: String,

    @field:SerializedName("popularity")
    val popularity: Double,


    @field:SerializedName("id")
    val id: Int
)
