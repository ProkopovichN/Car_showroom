package com.example.car_showroom.Admin

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.car_showroom.Car_info_data
import com.example.car_showroom.R
import com.example.car_showroom.databinding.ItemCarAdminBinding
import com.example.car_showroom.databinding.ItemToAdminBinding
import com.squareup.picasso.Picasso

class RecycleAdapterAdminTO(private val ListTO : ArrayList<TO_data_admin>, val listener : Listener) : RecyclerView.Adapter<RecycleAdapterAdminTO.ItemHolder>() {

    class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemToAdminBinding.bind(itemView)
        fun bind(item: TO_data_admin, listener : Listener) = with(binding){
            binding.login.text = item.login
            binding.auto.text = item.auto
            binding.date.text = item.date
            binding.time.text = item.time.toString() + ":00"
            if (item.status == 2){
                binding.btnYes.visibility = View.GONE
                binding.btnNo.visibility = View.GONE
            }
            binding.btnYes.setOnClickListener{
                listener.OnBtnYes(item)
            }
            binding.btnNo.setOnClickListener{
                listener.OnBtnNo(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_to_admin, parent, false)
        return ItemHolder(itemView)
    }

    override fun getItemCount(): Int {
        return ListTO.size
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(ListTO[position], listener)
    }

    interface Listener{
        fun OnBtnYes(recItem : TO_data_admin)
        fun OnBtnNo(item: TO_data_admin)
    }

}