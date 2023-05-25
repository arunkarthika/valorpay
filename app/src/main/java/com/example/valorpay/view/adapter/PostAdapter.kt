package com.example.valorpay.view.adapter
import android.content.Context
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.valorpay.R
import com.example.valorpay.databinding.PostItemsBinding
import com.example.valorpay.model.Post
import com.example.valorpay.util.AppConstant
import java.util.*


class PostAdapter(private val postList: Post, private val buildContext: Context, private val clickListener: PostItemClickListener) : RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvPostName.text =postList[position].title
        holder.binding.tvPostBody.text = postList[position].body

        Glide.with(buildContext)
            .load(AppConstant.SampleImageUrl)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .centerCrop()
            .into(holder.binding.ivPost)
        val lastSeenDateTime = Date()
        val lastSeenAgo = getLastSeenAgo(lastSeenDateTime)
        holder.binding.tvPostDate.text=lastSeenAgo
        holder.binding.tvComments.setOnClickListener {
            clickListener.onItemClick(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PostItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return postList.size
    }


    private fun getLastSeenAgo(dateTime: Date): CharSequence {
        val now = System.currentTimeMillis()
        return DateUtils.getRelativeTimeSpanString(dateTime.time, now, DateUtils.MINUTE_IN_MILLIS)
    }

    inner class ViewHolder(val binding: PostItemsBinding) : RecyclerView.ViewHolder(binding.root)

    interface PostItemClickListener {
        fun onItemClick(position: Int)
    }
}