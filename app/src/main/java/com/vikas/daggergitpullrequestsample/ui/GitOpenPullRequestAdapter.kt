package com.vikas.daggergitpullrequestsample.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vikas.daggergitpullrequestsample.R
import com.vikas.daggergitpullrequestsample.data.models.GitPullReqModel

/**
 * adapter class to bind data to ui for open pull request list
 */
class GitOpenPullRequestAdapter : RecyclerView.Adapter<GitOpenPullRequestAdapter.ViewHolder>() {

    private val pendingList = ArrayList<GitPullReqModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_search_items, parent, false))
    }

    override fun getItemCount() = pendingList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(pendingList.get(position))
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val ivGitProfilePic = itemView.findViewById<ImageView>(R.id.ivGitProfilePic)
        private val tvPullReqTitle = itemView.findViewById<TextView>(R.id.tvPullReqTitle)
        private val tvPullReqNo = itemView.findViewById<TextView>(R.id.tvPullReqNo)

        /**
         * set ui data here
         */
        fun setData(get: GitPullReqModel) {

            Glide.with(ivGitProfilePic.context)
                .load(get.user.avatar_url)
                .placeholder(R.drawable.ic_github_logo)
                .optionalCircleCrop()
                .into(ivGitProfilePic)

            tvPullReqNo.text = "#".plus(get.number)
            tvPullReqTitle.text = get.title
        }
    }

    /**
     * updates the adapter with new data
     */
    public fun updateData(list: List<GitPullReqModel>) {
        this.pendingList.addAll(list)
        notifyDataSetChanged()
    }

    /**
     * updates the adapter with new data
     */
    public fun clearData() {
        this.pendingList.clear()
        notifyDataSetChanged()
    }
}