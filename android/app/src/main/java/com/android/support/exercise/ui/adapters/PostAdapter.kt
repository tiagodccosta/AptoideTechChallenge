package com.android.support.exercise.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.support.exercise.data.Post

class PostAdapter : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    private var postList: List<Post> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setPosts(posts: List<Post>) {
        postList = posts
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_2, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = postList[position]
        holder.bind(post)
    }

    override fun getItemCount(): Int = postList.size

    override fun getItemId(position: Int): Long {
        return postList[position].id.toLong()
    }

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleView: TextView = itemView.findViewById(android.R.id.text1)
        private val idView: TextView = itemView.findViewById(android.R.id.text2)

        fun bind(post: Post) {
            "Post: ${post.id}".also { titleView.text = it }
            idView.text = post.title
        }
    }
}
