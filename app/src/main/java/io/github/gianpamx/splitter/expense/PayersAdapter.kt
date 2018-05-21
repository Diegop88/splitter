package io.github.gianpamx.splitter.expense

import android.support.v7.recyclerview.extensions.AsyncListDiffer
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import io.github.gianpamx.splitter.R
import kotlinx.android.synthetic.main.expense_payer_item.view.*

class PayersAdapter : RecyclerView.Adapter<PayersAdapter.ViewHolder>() {
    private val differ = AsyncListDiffer<PayerModel>(this, NewCallback())

    override fun getItemCount() = differ.currentList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    fun replacePayers(payers: List<PayerModel>) {
        differ.submitList(payers)
    }

    class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.expense_payer_item, parent, false)) {
        fun bind(payer: PayerModel) {
            with(itemView) {
                nameTextView.text = payer.name
                amountTextView.text = payer.amount
            }
        }
    }

    class NewCallback : DiffUtil.ItemCallback<PayerModel>() {
        override fun areItemsTheSame(oldItem: PayerModel?, newItem: PayerModel?) = oldItem?.id == newItem?.id
        override fun areContentsTheSame(oldItem: PayerModel?, newItem: PayerModel?) = oldItem == newItem
    }
}
