package com.annas.githubuser.di

import com.annas.githubuser.core.domain.usecase.UserInteractor
import com.annas.githubuser.core.domain.usecase.UserUseCase
import com.annas.githubuser.detail.DetailViewModel
import com.annas.githubuser.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<UserUseCase> { UserInteractor(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}
