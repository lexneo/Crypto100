package com.lexneoapps.crypto100.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lexneoapps.crypto100.R
import com.lexneoapps.crypto100.data.remote.model.Data
import com.lexneoapps.crypto100.databinding.ItemFragmentHomeBinding

class CoinAdapter() :
    PagingDataAdapter<Data, CoinAdapter.MyViewHolder>(CoinDiffCallback()) {

    class MyViewHolder(private val binding: ItemFragmentHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Data) {
            binding.apply {
                nameTextView.text = data.name
                symbolTextView.text = data.symbol
                dateAddedTextView.text = data.shortDate
                priceTextView.text =
                    root.resources.getString(R.string.price_text, data.quote.USD.priceFormatted.toString())
                marketCapTextView.text =
                    root.resources.getString(R.string.cap_text, data.quote.USD.capInBil.toString())
                marketCapPercentTextView.text = root.resources.getString(
                    R.string.cap_percent_text,
                    data.quote.USD.capPercentage.toString()
                )
                val rankInt = data.cmc_rank
                if (rankInt<10){
                    rankTextView.text = root.resources.getString(
                        R.string.format_single_digit,
                        rankInt.toString()
                    )
                }else rankTextView.text = data.cmc_rank.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemFragmentHomeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentData = getItem(position)
        if (currentData != null) {
            holder.bind(currentData)
        }
    }

}

private class CoinDiffCallback : DiffUtil.ItemCallback<Data>() {
    override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
        return oldItem == newItem
    }
}
