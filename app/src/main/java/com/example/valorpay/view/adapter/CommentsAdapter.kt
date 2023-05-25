package com.example.valorpay.view.adapter
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.valorpay.R
import com.example.valorpay.databinding.CommentsItemsBinding
import com.example.valorpay.model.Comments
import com.example.valorpay.util.AppConstant


class CommentsAdapter(private val comments: Comments, private val buildContext: Context) : RecyclerView.Adapter<CommentsAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, p1: Int) {
        holder.binding.tvCommentsName.text =comments[p1].name
        holder.binding.tvCommentsBody.text = comments[p1].body

        Glide.with(buildContext)
            .load(AppConstant.SampleImageUrl)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .centerCrop()
            .into(holder.binding.ivUserComments)

        holder.binding.tvTimeStamp.text="${p1} "+buildContext.resources.getString(R.string.hours)
        holder.binding.tvLikes.text="${p1} "+buildContext.resources.getString(R.string.likes)
    }


    override fun getItemCount(): Int {
        return comments.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CommentsItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    inner class ViewHolder(val binding: CommentsItemsBinding) : RecyclerView.ViewHolder(binding.root)

}