package com.vikas.daggergitpullrequestsample.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vikas.daggergitpullrequestsample.data.models.GitPullReqModel
import com.vikas.daggergitpullrequestsample.data.remote.WebService
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * view model for fetching data from repo on behalf of ui
 */
class GitViewModel @Inject constructor(application: Application, private val webservice: WebService) :
    AndroidViewModel(application) {

    private val TAG: String = GitViewModel::class.java.canonicalName!!

    /**
     * call this to get the live data for git repo open pull requests
     */
    fun getOpenPullRequests(inputRepo: List<String>, page: Int): LiveData<List<GitPullReqModel>> {
        val liveRepo = MutableLiveData<List<GitPullReqModel>>()
        webservice.getOpenPullRequests(inputRepo[0], inputRepo[1], "open", page)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : Observer<List<GitPullReqModel>> {
                override fun onComplete() {}

                override fun onSubscribe(d: Disposable) {}

                override fun onNext(t: List<GitPullReqModel>) {
                    liveRepo.value = t
                    Log.d(TAG, " onNext for api call")
                }

                override fun onError(e: Throwable) {
                    liveRepo.value = null
                    Log.d(TAG, " onError for api call")
                }
            })
        return liveRepo
    }
}