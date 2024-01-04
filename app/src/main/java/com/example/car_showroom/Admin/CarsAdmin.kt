package com.example.car_showroom.Admin

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.car_showroom.Car_info_data
import com.example.car_showroom.CarsDatabase.CarsDb
import com.example.car_showroom.R
import com.example.car_showroom.RecycleAdapter
import com.example.car_showroom.UsersDatabase.UsersDb
import com.squareup.picasso.Picasso
import kotlin.concurrent.thread


class CarsAdmin : Fragment(), RecycleAdapterAdmin.Listener{

    var items : ArrayList<Car_info_data> = ArrayList()
    lateinit var db : CarsDb

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_cars_admin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){

        db = CarsDb.getDb(requireActivity())

        thread{
            val items_tmp = db.getDao().getAllItems()
            for (i in items_tmp){
                items.add(Car_info_data(i.id!!, i.photo, i.marka, i.model, i.price, i.power, i.odometr, i.year, i.privod, i.c_passengers, i.color))
            }
            requireActivity().runOnUiThread {
                val recyclerView: RecyclerView? = view?.findViewById(R.id.rcView)
                recyclerView!!.layoutManager = GridLayoutManager(requireContext(), 1)
                recyclerView.adapter = RecycleAdapterAdmin(items, this)
            }
        }

        val btn = activity?.findViewById<TextView>(R.id.btn_add_car)

        btn?.setOnClickListener{
            val mAct = (activity as MainActivityAdmin)
            mAct.replaceFragment(4)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = CarsAdmin()
    }

    override fun OnClick(recItem: Car_info_data) {
        val dialogBinding = layoutInflater.inflate(R.layout.dialog_car_info, null)
        val myDialog = Dialog(requireActivity())
        myDialog.setContentView(dialogBinding)
        myDialog.setCancelable(true)
        myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myDialog.show()
        val photo = dialogBinding.findViewById<ImageView>(R.id.photo_d)
        val marka = dialogBinding.findViewById<TextView>(R.id.marka_d)
        val model = dialogBinding.findViewById<TextView>(R.id.model_d)
        val price = dialogBinding.findViewById<TextView>(R.id.price_d)
        val power = dialogBinding.findViewById<TextView>(R.id.power_d)
        val odometr = dialogBinding.findViewById<TextView>(R.id.odometr_d)
        val year = dialogBinding.findViewById<TextView>(R.id.year_d)
        val privod = dialogBinding.findViewById<TextView>(R.id.privod_d)
        val c_pass = dialogBinding.findViewById<TextView>(R.id.c_pass_d)
        val color = dialogBinding.findViewById<TextView>(R.id.color_d)
        val btn = dialogBinding.findViewById<Button>(R.id.btn_buy)
        btn.visibility = View.GONE
        Picasso.get()
            .load(recItem.photo)
            .placeholder(R.drawable.fone1)
            .into(photo)
        marka?.text = "Марка:${recItem.marka}"
        model?.text = "Модель:${recItem.model}"
        price?.text = "Цена:${recItem.price} рублей"
        power?.text = "Мощность:${recItem.power} лс"
        odometr?.text = "Пробег:${recItem.odometr} км"
        year?.text = "Год:${recItem.year}"
        privod?.text = "Привод:${recItem.privod}"
        c_pass?.text = "Вместимость:${recItem.count_passengers}"
        color?.text = "Цвет:${recItem.color}"
    }

    @SuppressLint("MissingInflatedId")
    override fun OnSaled(recItem: Car_info_data) {
        val dialogBinding = layoutInflater.inflate(R.layout.dialog_car_saled, null)
        val myDialog = Dialog(requireActivity())
        myDialog.setContentView(dialogBinding)
        myDialog.setCancelable(true)
        myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myDialog.show()
        val b_ye = dialogBinding.findViewById<Button>(R.id.b_yes)
        val b_no = dialogBinding.findViewById<Button>(R.id.b_no)
        b_ye.setOnClickListener{
            thread{
                db.getDao().delete(recItem.id)
                myDialog.dismiss()
                updateDate()
            }
        }
        b_no.setOnClickListener{
            myDialog.dismiss()
        }
    }

    override fun OnLongClick(item: Car_info_data) {

    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateDate(){
        thread{
            val items_tmp = db.getDao().getAllItems()
            for (i in items_tmp){
                items.add(Car_info_data(i.id!!, i.photo, i.marka, i.model, i.price, i.power, i.odometr, i.year, i.privod, i.c_passengers, i.color))
            }
            }
            requireActivity().runOnUiThread {
                val rView: RecyclerView? = view?.findViewById(R.id.rcView)
                rView?.adapter?.notifyDataSetChanged()
            }

    }

}
