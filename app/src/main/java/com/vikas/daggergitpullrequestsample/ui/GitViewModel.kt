package com.vikas.daggergitpullrequestsample.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.vikas.daggergitpullrequestsample.di.module.NetworkModule
import javax.inject.Inject

/**
 * view model for fetching data from repo on behalf of ui
 */
class GitViewModel @Inject constructor(application: Application, networkModule: NetworkModule) :
    AndroidViewModel(application) {

}