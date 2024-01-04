package com.example.car_showroom.Admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.car_showroom.CarsDatabase.Cars
import com.example.car_showroom.CarsDatabase.CarsDb
import com.example.car_showroom.R
import kotlin.concurrent.thread


class AddCarAdmin : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_car_admin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){

        val db = CarsDb.getDb(requireActivity())
        val photo = activity?.findViewById<TextView>(R.id.photo)
        val marka = activity?.findViewById<TextView>(R.id.marka)
        val model = activity?.findViewById<TextView>(R.id.model)
        val price = activity?.findViewById<TextView>(R.id.price)
        val power = activity?.findViewById<TextView>(R.id.power)
        val odometr = activity?.findViewById<TextView>(R.id.odometr)
        val year = activity?.findViewById<TextView>(R.id.year)
        val privod = activity?.findViewById<TextView>(R.id.privod)
        val c_pass = activity?.findViewById<TextView>(R.id.c_passengers)
        val color = activity?.findViewById<TextView>(R.id.color)
        val btn = activity?.findViewById<TextView>(R.id.btn_add)

        btn?.setOnClickListener{
            thread {
                db.getDao().insertItem(
                    Cars(
                        null,
                        photo?.text.toString(),
                        marka?.text.toString(),
                        model?.text.toString(),
                        price?.text.toString().toInt(),
                        power?.text.toString().toInt(),
                        odometr?.text.toString(),
                        year?.text.toString().toInt(),
                        privod?.text.toString(),
                        c_pass?.text.toString().toInt(),
                        color?.text.toString()
                    )
                )
            }
            Toast.makeText(requireActivity(), "Машина добавлена", Toast.LENGTH_SHORT).show()
            Thread.sleep(700)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = AddCarAdmin()

    }

}
