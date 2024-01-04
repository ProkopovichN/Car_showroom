package com.example.car_showroom

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.car_showroom.CarsDatabase.CarsDb
import com.example.car_showroom.SaleDatabase.Sale
import com.example.car_showroom.SaleDatabase.SaleDb
import com.squareup.picasso.Picasso
import kotlin.concurrent.thread


class ListFragment : Fragment(), RecycleAdapter.Listener {

    var items : ArrayList<Car_info_data> = ArrayList()
    lateinit var db_s : SaleDb
    private val liveData: DataClass by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        val db = CarsDb.getDb(requireActivity())
        db_s = SaleDb.getDb(requireActivity())
        thread{
            val items_tmp = db.getDao().getAllItems()
            for (i in items_tmp){
                items.add(Car_info_data(i.id!!, i.photo, i.marka, i.model, i.price, i.power, i.odometr, i.year, i.privod, i.c_passengers, i.color))
            }

        requireActivity().runOnUiThread {
            val recyclerView: RecyclerView? = view?.findViewById(R.id.rcView)
            recyclerView!!.layoutManager = GridLayoutManager(requireContext(), 1)
            recyclerView.adapter = RecycleAdapter(items, this)
        }
            }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ListFragment()
    }

    @SuppressLint("MissingInflatedId")
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
        btn.setOnClickListener{
            thread {
                if (db_s.getDao().checkSale(liveData.login.value!!) >= 1){
                    db_s.getDao().delete(liveData.login.value!!)
                }
                db_s.getDao().insertItem(
                    Sale(
                        null,
                        recItem.photo,
                        (recItem.marka + " " + recItem.model),
                        liveData.login.value!!,
                        1,
                        recItem.id
                    )
                )
            }
            myDialog.dismiss()
        }
    }

    override fun OnLongClick(item: Car_info_data) {

    }


}