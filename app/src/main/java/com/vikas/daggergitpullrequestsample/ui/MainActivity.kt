package com.vikas.daggergitpullrequestsample.ui

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.vikas.daggergitpullrequestsample.R
import com.vikas.daggergitpullrequestsample.base.BaseActivity
import javax.inject.Inject

/**
 * main single ui holder
 */
class MainActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: GitViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(GitViewModel::class.java)
    }
}
