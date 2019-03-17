package com.vikas.daggergitpullrequestsample.data.models

//data class to hold the api data
data class GitPullReqModel(
    val url: String,
    val id: Int,
    val number: Int,
    val state: String,
    val title: String,
    val user: GitUserProfile
)

data class GitUserProfile(val avatar_url: String)