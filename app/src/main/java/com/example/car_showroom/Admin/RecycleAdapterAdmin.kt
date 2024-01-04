package com.example.car_showroom.Admin

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.car_showroom.Car_info_data
import com.example.car_showroom.R
import com.example.car_showroom.databinding.ItemCarAdminBinding
import com.example.car_showroom.databinding.ItemCarBinding
import com.squareup.picasso.Picasso

class RecycleAdapterAdmin(private val ListReciepe : ArrayList<Car_info_data>, val listener : Listener) : RecyclerView.Adapter<RecycleAdapterAdmin.ItemHolder>() {

    class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemCarAdminBinding.bind(itemView)
        fun bind(item: Car_info_data, listener : Listener) = with(binding){
            Picasso.get()
                .load(item.photo)
                .placeholder(R.drawable.fone1)
                .into(photoCar)
            priceCar.text = "${item.price} рублей"
            btnCar.setOnClickListener{
                listener.OnClick(item)
            }
            btnS.setOnClickListener{
                listener.OnSaled(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_car_admin, parent, false)
        return ItemHolder(itemView)
    }

    override fun getItemCount(): Int {
        return ListReciepe.size
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(ListReciepe[position], listener)
    }

    interface Listener{
        fun OnClick(recItem : Car_info_data)
        fun OnSaled(recItem : Car_info_data)
        fun OnLongClick(item: Car_info_data)
    }

}