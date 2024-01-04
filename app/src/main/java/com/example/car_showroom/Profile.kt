package com.example.car_showroom

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import java.util.Date
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.fragment.app.activityViewModels
import com.example.car_showroom.SaleDatabase.SaleDb
import com.example.car_showroom.TO_Database.To
import com.example.car_showroom.TO_Database.ToDb
import com.example.car_showroom.UsersDatabase.Users
import com.example.car_showroom.UsersDatabase.UsersDb
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.concurrent.thread


class Profile : Fragment() {

    private val liveData: DataClass by activityViewModels()
    lateinit var dbUs : UsersDb
    lateinit var dbTo : ToDb
    lateinit var dbSale : SaleDb
    lateinit var us : Users

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dbUs = UsersDb.getDb(requireContext())
        dbTo = ToDb.getDb(requireContext())
        dbSale = SaleDb.getDb(requireContext())
        thread {
            us = dbUs.getDao().retPassword(liveData.login.value.toString())
        }
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        val n_s = activity?.findViewById<TextView>(R.id.name_sur)
        val inf_car_up = activity?.findViewById<TextView>(R.id.info_car_up)
        val inf_car_down = activity?.findViewById<TextView>(R.id.info_car_down)
        val b_info_car = activity?.findViewById<Button>(R.id.btn_info_car)
        val b_to = activity?.findViewById<Button>(R.id.btn_to_TO)
        val t_date_to = activity?.findViewById<TextView>(R.id.date_TO)
        val t_time_to = activity?.findViewById<TextView>(R.id.time_TO)
        val t_status = activity?.findViewById<TextView>(R.id.status_TO)
        val t_status_sale = activity?.findViewById<TextView>(R.id.status_sale)
        val t_auto_sale = activity?.findViewById<TextView>(R.id.auto_sale)
        val card = activity?.findViewById<CardView>(R.id.card_to)
        val cardSale = activity?.findViewById<CardView>(R.id.card_sale)
        n_s!!.text = liveData.name.value + ' ' + liveData.surname.value
        thread {
            if (dbTo.getDao().checkTO(us.login) == 1) {
                var d: LocalDate
                d = LocalDate.now()
                val to_us = dbTo.getDao().getItem(us.login)
                val datess = LocalDate.parse(to_us.date, DateTimeFormatter.ISO_DATE)
                if (d > datess){
                    dbTo.getDao().delete(us.login)
                }
            }

                if (us.marka_auto == "null") {
                requireActivity().runOnUiThread {
                    inf_car_up!!.text = "Информация об автомобиле отсутсвует"
                    inf_car_down!!.visibility = (View.GONE)
                }
                }
                else {
                    requireActivity().runOnUiThread {
                        inf_car_up!!.text = "Ваш автомобиль:"
                        inf_car_down!!.visibility = (View.VISIBLE)
                        inf_car_down!!.text = us.marka_auto + " " + us.model_auto
                        b_info_car!!.text = "Обновить информацию об авто"
                    }
                }

            if (dbTo.getDao().checkTO(us.login) == 1){
                var btn_to_vis = false
                thread {
                    if (dbTo.getDao().getItem(us.login).status == 3) {
                        btn_to_vis = true
                    }
                    var item = dbTo.getDao().getItem(us.login)
                    requireActivity().runOnUiThread {
                        if (btn_to_vis){
                            b_to!!.visibility = (View.VISIBLE)
                        }
                        else {
                            b_to!!.visibility = (View.GONE)
                        }
                        t_date_to!!.text = "Дата:" + item.date
                        t_time_to!!.text = "Время:" + item.time.toString() + ":00"
                        if (item.status == 1){
                            t_status!!.text = "На рассмотрении"
                            t_status.setTextColor(this.getResources().getColorStateList(R.color.orange))
                        }
                        else if (item.status == 2){
                            t_status!!.text = "Одобрено"
                            t_status.setTextColor(this.getResources().getColorStateList(R.color.green))
                        }
                        else if (item.status == 3){
                            t_status!!.text = "Отклонено"
                            t_status.setTextColor(this.getResources().getColorStateList(R.color.red))
                        }
                    }
                }

            }
            else{
                requireActivity().runOnUiThread {
                    b_to!!.visibility = (View.VISIBLE)
                    card!!.backgroundTintList = this.getResources().getColorStateList(R.color.grey)
                    t_date_to!!.visibility = (View.GONE)
                    t_time_to!!.visibility = (View.GONE)
                    t_status!!.visibility = (View.GONE)
                }
            }

            if (dbSale.getDao().checkSale(us.login) == 1){
                var item = dbSale.getDao().getItem(us.login)
                requireActivity().runOnUiThread {
                    if (item.status == 2) {
                        t_status_sale!!.text = "Одобрено"
                        t_status_sale.setTextColor(
                            this.getResources().getColorStateList(R.color.green)
                        )
                    } else if (item.status == 1) {
                        t_status_sale!!.text = "На рассмотрении"
                        t_status_sale.setTextColor(
                            this.getResources().getColorStateList(R.color.orange)
                        )
                    } else {
                        t_status_sale!!.text = "Отклонено"
                        t_status_sale.setTextColor(
                            this.getResources().getColorStateList(R.color.red)
                        )
                    }
                    t_auto_sale!!.text = item.nameCar
                }
            }
            else {
                requireActivity().runOnUiThread {
                    cardSale!!.backgroundTintList =
                        this.getResources().getColorStateList(R.color.grey)
                    t_auto_sale!!.visibility = (View.GONE)
                    t_status_sale!!.visibility = (View.GONE)
                }
            }

        }
        b_info_car!!.setOnClickListener{
            val dialogBinding = layoutInflater.inflate(R.layout.dialog_add_info_car, null)
            val myDialog = Dialog(requireActivity())
            myDialog.setContentView(dialogBinding)
            myDialog.setCancelable(true)
            myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            myDialog.show()
            val t_marka = dialogBinding.findViewById<TextView>(R.id.text_marka)
            val t_model = dialogBinding.findViewById<TextView>(R.id.text_model)
            val btn = dialogBinding.findViewById<Button>(R.id.btn_add_info_dialog)
            btn.setOnClickListener{
                if (!TextUtils.isEmpty(t_marka!!.text.toString()) && !TextUtils.isEmpty(t_model!!.text.toString())){
                    Log.i("Dibug1", "${t_marka.text.toString()}")
                    val mr = t_marka.text.toString()
                    val ml = t_model.text.toString()
                    thread {
                        dbUs.getDao().updateMarkaUserCar(us.login, mr)
                        dbUs.getDao().updateModelUserCar(us.login, ml)
                        updateInfoCarClient()

                        myDialog.dismiss()
                    }
                    Toast.makeText(requireContext(), "Информация обновлена", Toast.LENGTH_SHORT).show()
                }
            }
        }
        b_to!!.setOnClickListener{
            val dialogBinding = layoutInflater.inflate(R.layout.dialog_add_to, null)
            val myDialog = Dialog(requireActivity())
            myDialog.setContentView(dialogBinding)
            myDialog.setCancelable(true)
            myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            myDialog.show()
            val btn = dialogBinding.findViewById<Button>(R.id.btn_add_to_yes)
            var selectedDate: String = "null"
            var selectedTime: Int = 0
            thread {
                var d: LocalDate
                var d2: LocalDate
                d2 = LocalDate.now()
                d = d2.plusDays(1)
                selectedDate = d.toString()
                var ind = 0
                Log.i("Dibug2", "${selectedDate}")
                var arTime = dbTo.getDao().getAllItems(selectedDate)
                var arTime1: Array<Int?> = arrayOfNulls(10 - arTime.size)
                var arTime2: Array<String?> = arrayOfNulls(10 - arTime.size)
                val adapterTime = ArrayAdapter<String>(
                    requireContext(),
                    android.R.layout.simple_spinner_dropdown_item,
                    arTime2
                )
                var ar: ArrayList<Int> = arrayListOf()
                for (i in arTime) {
                    ar.add(i.time)
                }
                for (i in 8..17) {
                    if (ar.indexOf(i) == -1) {
                        arTime1[ind] = i
                        arTime2[ind] = i.toString() + ":00"
                        ind++
                    }
                }
                requireActivity().runOnUiThread {
                    val spTime = dialogBinding.findViewById<Spinner>(R.id.item_time)

                    spTime.adapter = adapterTime
                    spTime.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {
                            selectedTime = arTime1[position]!!
                            Log.i("Dibug2", "${selectedTime}")
                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {
                            TODO("Not yet implemented")
                        }

                    }
                }

                var arDate: Array<String> =
                    arrayOf("null", "null", "null", "null", "null", "null", "null")

                var d1: LocalDate = d
                for (i in 1..7) {
                    arDate.set(i - 1, d1.toString())
                    d1 = d.plusDays(i.toLong())
                }
                requireActivity().runOnUiThread {
                    val spDate = dialogBinding.findViewById<Spinner>(R.id.item_date)
                    val adapter = ArrayAdapter<String>(
                        requireContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        arDate
                    )
                    spDate.adapter = adapter
                    spDate.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {
                            selectedDate = arDate[position]
                            thread {
                                ind = 0
                                arTime = dbTo.getDao().getAllItems(selectedDate)
                                Log.i("Dibug2", "${arTime.size}")
                                arTime1 = arrayOfNulls(10 - arTime.size)
                                arTime2 = arrayOfNulls(10 - arTime.size)
                                Log.i("Dibug2", "${arTime1.size}")
                                ar.clear()
                                for (i in arTime) {
                                    ar.add(i.time)
                                }
                                for (i in 8..17) {
                                    if (ar.indexOf(i) == -1) {
                                        arTime1[ind] = i
                                        arTime2[ind] = i.toString() + ":00"
                                        Log.i("Dibug2", "элемент - ${arTime2[ind]}")
                                        ind++
                                    }
                                }
                                requireActivity().runOnUiThread {
                                    adapterTime.notifyDataSetChanged()
                                }
                            }


                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {
                            TODO("Not yet implemented")
                        }

                    }
                }

                btn!!.setOnClickListener{
                    thread {
                        if (dbTo.getDao().checkTO(us.login) == 1){
                            dbTo.getDao().delete(us.login)
                        }
                        dbTo.getDao().insertItem(
                            To(
                                null,
                                us.login,
                                selectedDate,
                                us.marka_auto + " " + us.model_auto,
                                selectedTime,
                                1
                            )
                        )
                    }
                    myDialog.dismiss()
                }
            }
        }
    }

    fun updateInfoCarClient(){
        val inf_car_down = activity?.findViewById<TextView>(R.id.info_car_down)
        val inf_car_up = activity?.findViewById<TextView>(R.id.info_car_up)
        val b_info_car = activity?.findViewById<Button>(R.id.btn_info_car)
        thread {
            us = dbUs.getDao().retPassword(liveData.login.value.toString())
            requireActivity().runOnUiThread {
                inf_car_up!!.text = "Ваш автомобиль:"
                inf_car_down!!.visibility = (View.VISIBLE)
                inf_car_down!!.text = us.marka_auto + " " + us.model_auto
                b_info_car!!.text = "Обновить информацию об авто"
            }
        }
    }

    fun RestartFrag() {
        val mAct = (activity as MainActivity)
        mAct.repProfile()
    }

    companion object {
        fun newInstance() =
            Profile()

    }

}