package com.vikas.daggergitpullrequestsample.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vikas.daggergitpullrequestsample.di.scope.ViewModelKey
import com.vikas.daggergitpullrequestsample.ui.GitViewModel
import com.vikas.daggergitpullrequestsample.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(GitViewModel::class)
    internal abstract fun bindViewModel(userViewModel: GitViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}