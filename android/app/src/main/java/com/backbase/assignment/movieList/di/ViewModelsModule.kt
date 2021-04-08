package com.backbase.assignment.movieList.di

import com.backbase.assignment.core.di.support.AssistedViewModelFactory
import com.backbase.assignment.core.di.support.MavericksViewModelComponent
import com.backbase.assignment.core.di.support.ViewModelKey
import com.backbase.assignment.movieList.viewModel.MovieDetailViewModel
import com.backbase.assignment.movieList.viewModel.MovieListViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.multibindings.IntoMap

@Module
@InstallIn(MavericksViewModelComponent::class)
interface ViewModelsModule {
    @Binds
    @IntoMap
    @ViewModelKey(MovieListViewModel::class)
    fun movieListViewModelFactory(factory: MovieListViewModel.Factory): AssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailViewModel::class)
    fun movieDetailViewModelFactory(factory: MovieDetailViewModel.Factory): AssistedViewModelFactory<*, *>
}