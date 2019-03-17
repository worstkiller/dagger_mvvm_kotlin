package com.vikas.daggergitpullrequestsample.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vikas.daggergitpullrequestsample.R
import com.vikas.daggergitpullrequestsample.base.BaseActivity
import com.vikas.daggergitpullrequestsample.data.models.GitPullReqModel
import com.vikas.daggergitpullrequestsample.helpers.EndlessScrollListener
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

/**
 * main single ui holder
 */
class MainActivity : BaseActivity() {

    private val TAG = MainActivity::class.java.canonicalName
    private lateinit var viewModel: GitViewModel
    private lateinit var adapter: GitOpenPullRequestAdapter
    private lateinit var disposableApiReq: Disposable
    private var pager = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(GitViewModel::class.java)
        setUpRecyclerView()
    }

    /**
     * setting up recyclerview
     */
    private fun setUpRecyclerView() {
        adapter = GitOpenPullRequestAdapter()
        val layoutManager = LinearLayoutManager(applicationContext)
        rvSearchItems.layoutManager = layoutManager
        rvSearchItems.adapter = adapter
        rvSearchItems.pagination(object : EndlessScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                getPendingListData(etSearchInput.text.toString(), pager)
                Log.d(TAG, "PAGE=".plus(page))
            }
        })
    }

    /**
     * this calls view model to get api data and updates the ui accordingly
     */
    private fun getPendingListData(input: String, page: Int) {
        viewModel.getOpenPullRequests(input.split("/"), page).observe(this, Observer<List<GitPullReqModel>> {
            hideProgress()
            it?.let {
                if (it.isNotEmpty()) this@MainActivity.pager++
                adapter.updateData(it)
            }
        })
    }

    override fun onStart() {
        super.onStart()
        //getting click observable and input observable
        val inputObservable = inputObservable()
        val clickObservable = clickObservable()
        val searchPullReObservable = Observable.merge(inputObservable, clickObservable)
        disposableApiReq = searchPullReObservable
            .observeOn(AndroidSchedulers.mainThread())
            .filter {
                if (isFormatValid()) {
                    btnGo.visibility = View.VISIBLE
                } else {
                    //reset the values
                    pager = 1
                    adapter.clearData()
                    btnGo.visibility = View.GONE
                }
                isFormatValid()
            }.debounce(500, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                showProgress()
                getPendingListData(it, pager)
            }
            .subscribe()
    }

    /**
     * observable for button click on which api will get trigger
     */
    private fun clickObservable(): Observable<String> {
        return Observable.create<String> { emitter ->
            btnGo.setOnClickListener {
                emitter.onNext(etSearchInput.text.toString())
            }
            emitter.setCancellable { btnGo.setOnClickListener(null) }
        }
    }

    /**
     * observable the input added by user, validate and enable the call button
     */
    private fun inputObservable(): Observable<String> {
        return Observable.create<String> { emitter ->
            val textWatcher = object : TextWatcher {

                override fun afterTextChanged(s: Editable?) = Unit

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

                override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    s?.toString()?.let { emitter.onNext(it) }
                }
            }

            etSearchInput.addTextChangedListener(textWatcher)

            emitter.setCancellable {
                etSearchInput.removeTextChangedListener(textWatcher)
            }
        }
    }

    /**
     * a basic check to handle the input
     */
    private fun isFormatValid() = Pattern.compile("^\\S.*\\/.\\S{0}.*").matcher(etSearchInput.text!!).matches()

    private fun showProgress() {
        rvSearchItems.visibility = View.GONE
        pbProgress.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        rvSearchItems.visibility = View.VISIBLE
        pbProgress.visibility = View.GONE
    }

    override fun onStop() {
        super.onStop()
        disposableApiReq.dispose()
    }

}

private fun RecyclerView.pagination(endlessScrollingListener: EndlessScrollListener) {
    this.addOnScrollListener(endlessScrollingListener)
}
