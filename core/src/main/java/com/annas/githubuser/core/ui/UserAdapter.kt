package com.annas.githubuser.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.annas.githubuser.core.R
import com.annas.githubuser.core.databinding.ItemUserBinding
import com.annas.githubuser.core.domain.model.User
import java.util.ArrayList

class UserAdapter : RecyclerView.Adapter<UserAdapter.ListViewHolder>() {

    private var listData = ArrayList<User>()
    var onItemClick: ((User) -> Unit)? = null

    fun setData(newListData: List<User>?) {
        if (newListData == null) return
        notifyItemRangeRemoved(0, listData.size)
        listData.clear()
        listData.addAll(newListData)
        notifyItemRangeInserted(0,listData.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false))

    override fun getItemCount() = listData.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemUserBinding.bind(itemView)
        fun bind(data: User) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(data.avatarUrl)
                    .into(imgAvatar)
                txtUsername.text = data.username
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[adapterPosition])
            }
        }
    }
}