package zefanya.denny.architecturecomponent.ui.setting

import androidx.lifecycle.ViewModel
import zefanya.denny.core.domain.usecase.CatalogUseCase

class SettingViewModel(private val catalogUseCase: CatalogUseCase) : ViewModel() {

    fun deleteMovie() = catalogUseCase.deleteMovie()

    fun deleteTvShow() = catalogUseCase.deleteTvShow()

    fun deleteDetailMovie() = catalogUseCase.deleteDetailMovie()

    fun deleteDetailTvShow() = catalogUseCase.deleteDetailTvShow()

    fun deleteFavouriteMovie() = catalogUseCase.deleteFavoriteMovie()

    fun deleteFavouriteTvShow() = catalogUseCase.deleteFavoritTvShow()


}