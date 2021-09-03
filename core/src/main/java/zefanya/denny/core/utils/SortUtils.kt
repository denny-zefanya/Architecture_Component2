package zefanya.denny.core.utils

import androidx.sqlite.db.SimpleSQLiteQuery

object SortUtils {
    const val MOST_POPULAR = "mostpopular"
    const val LEAST_POPULAR = "leastpopular "
    const val RANDOM = "Random"
    fun getSortedQuery(filter: String, isFav: Boolean, source: String): SimpleSQLiteQuery {
        val isFavInt = if (isFav) 1 else 0
        val simpleQuery =
            StringBuilder().append("SELECT * FROM $source WHERE favourite = 1 or favourite = (1 and $isFavInt) ")
        when (filter) {
            MOST_POPULAR -> {
                simpleQuery.append("ORDER BY star DESC")
            }
            LEAST_POPULAR -> {
                simpleQuery.append("ORDER BY star ASC")
            }
            RANDOM -> {
                simpleQuery.append("ORDER BY RANDOM()")
            }
        }
        return SimpleSQLiteQuery(simpleQuery.toString())
    }
}