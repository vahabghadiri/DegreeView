package com.vahabghadiri.degreeviewsampleapp.weightcontrol

import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.vahabghadiri.degreeviewsampleapp.R

/**
 *
 * @author Vahab
 */
class WeightsRvAdapter(
    private val dataList: MutableList<WeightListItem>,
    private var mWeightsRvAdapterCallback: WeightsRvAdapterCallback
) : RecyclerView.Adapter<WeightsRvAdapter.WeightVH>() {

    fun updateItems(newDataList: List<WeightListItem>) {
        val diffCallback = WeightsRvAdapterDiffCallback(dataList, newDataList)
        val diffResult = DiffUtil.calculateDiff(diffCallback, false)
        dataList.clear()
        dataList.addAll(newDataList)
        diffResult.dispatchUpdatesTo(this@WeightsRvAdapter)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeightVH {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.weight_list_item, parent, false)
        return WeightVH(view, mWeightsRvAdapterCallback)
    }

    override fun getItemCount() = dataList.size

    override fun onBindViewHolder(holder: WeightVH, position: Int) {
        holder.bindData(dataList[position])
    }

    inner class WeightVH(itemView: View, mListener: WeightsRvAdapterCallback)
        : RecyclerView.ViewHolder(itemView) {
        private val tvWeight = itemView.findViewById<TextView>(R.id.tvWeight)

        fun bindData(mWeightListItem: WeightListItem) {
            tvWeight.text = mWeightListItem.weight.toString()
        }
    }

    interface WeightsRvAdapterCallback {
        fun onItemClick(data: WeightListItem)
    }


    inner class WeightsRvAdapterDiffCallback(
        private val oldList: List<WeightListItem>,
        private val newList: List<WeightListItem>
    ) : DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                newList[newItemPosition] == oldList[oldItemPosition]

        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                newList[newItemPosition].weight == oldList[oldItemPosition].weight
    }


}
