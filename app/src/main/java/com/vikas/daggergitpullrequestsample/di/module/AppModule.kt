package com.vikas.daggergitpullrequestsample.di.module

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideNetworkModule(): NetworkModule {
        return NetworkModule()
    }
}