package uz.digital.reactiveprogramming.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.digital.reactiveprogramming.databinding.ItemLayoutBinding
import uz.digital.reactiveprogramming.model.VolumeInfo

class BookAdapter: ListAdapter<VolumeInfo, BookAdapter.RvViewHolder>(DiffCallBack()) {

    private class DiffCallBack : DiffUtil.ItemCallback<VolumeInfo>() {
        override fun areItemsTheSame(oldItem: VolumeInfo, newItem: VolumeInfo): Boolean {
            return oldItem.volumeInfo.title == newItem.volumeInfo.title
        }

        override fun areContentsTheSame(oldItem: VolumeInfo, newItem: VolumeInfo): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvViewHolder {
        return RvViewHolder(
            ItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RvViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class RvViewHolder(private val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(volumeInfo: VolumeInfo) {
            binding.apply {
                Glide.with(thumbImageView)
                    .load(volumeInfo.volumeInfo.imageLinks.smallThumbnail ?: "")
                    .circleCrop()
                    .into(thumbImageView)

                tvTitle.text = volumeInfo.volumeInfo.title
                tvDescription.text = volumeInfo.volumeInfo.description
                tvPublisher.text = volumeInfo.volumeInfo.publisher

                Log.d("Image@", "bind: ${volumeInfo.volumeInfo.imageLinks.smallThumbnail}")
            }
        }
    }
}