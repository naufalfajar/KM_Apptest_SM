package id.naufalfajar.km_interntest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.naufalfajar.km_interntest.databinding.ItemUserLayoutBinding
import id.naufalfajar.km_interntest.model.Data
import id.naufalfajar.km_interntest.model.Users

class ThirdScreenAdapter(private val onClickListener: (data: Data) -> Unit)
    : RecyclerView.Adapter<ThirdScreenAdapter.UserViewHolder>(){

    private val diffCallback = object : DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun updateData(users: Users?) = differ.submitList(users?.data)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class UserViewHolder(private val binding: ItemUserLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Data) {
            binding.apply {
                firstname.text = item.firstName
                lastname.text = item.lastName
                email.text = item.email
                Glide.with(itemView.context)
                    .load(item.avatar)
                    .into(civUser)
                cvUser.setOnClickListener {
                    onClickListener.invoke(item)
                }
            }
        }
    }

}