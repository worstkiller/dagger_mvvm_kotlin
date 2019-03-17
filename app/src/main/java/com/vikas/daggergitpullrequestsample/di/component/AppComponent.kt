package com.vikas.daggergitpullrequestsample.di.component

import android.app.Application
import com.vikas.daggergitpullrequestsample.base.GitApplication
import com.vikas.daggergitpullrequestsample.di.module.ViewModelModule
import com.vikas.daggergitpullrequestsample.di.module.ActivityModule
import com.vikas.daggergitpullrequestsample.di.module.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = arrayOf(
        ActivityModule::class,
        AppModule::class,
        ViewModelModule::class,
        AndroidSupportInjectionModule::class
    )
)
interface AppComponent {
    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(gitApplication: GitApplication)

}
