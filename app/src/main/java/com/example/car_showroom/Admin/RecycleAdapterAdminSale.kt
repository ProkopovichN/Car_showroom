package com.example.car_showroom.Admin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.car_showroom.R
import com.example.car_showroom.databinding.ItemCarBronAdminBinding
import com.squareup.picasso.Picasso

class RecycleAdapterAdminSale(private val ListSale : ArrayList<SaleDataAdmin>, val listener : RecycleAdapterAdminSale.Listener) : RecyclerView.Adapter<RecycleAdapterAdminSale.ItemHolder>() {

    class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemCarBronAdminBinding.bind(itemView)
        fun bind(item: SaleDataAdmin, listener : Listener) = with(binding){
            Picasso.get()
                .load(item.image)
                .placeholder(R.drawable.fone1)
                .into(carImage)

            binding.carName.text = item.nameCar
            binding.userName.text = item.user
            binding.btnSaleCar.setOnClickListener{
                listener.OnBtnYes(item)
            }
            binding.btnCancelCar.setOnClickListener{
                listener.OnBtnNo(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_car_bron_admin, parent, false)
        return ItemHolder(itemView)
    }

    override fun getItemCount(): Int {
        return ListSale.size
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(ListSale[position], listener)
    }

    interface Listener{
        fun OnBtnYes(recItem : SaleDataAdmin)
        fun OnBtnNo(item: SaleDataAdmin)
    }

}