package com.vikas.daggergitpullrequestsample.data.remote

import com.vikas.daggergitpullrequestsample.data.models.GitPullReqModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * api web service interface for retrofit
 */
interface WebService {

    //git pull requests list api
    @GET("repos/{company}/{repo}/pulls")
    fun getOpenPullRequests(
        @Path("company") company: String,
        @Path("repo") repo: String,
        @Query("state") state: String,
        @Query("page") page: Int
    ): Observable<List<GitPullReqModel>>

}