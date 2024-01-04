package com.example.car_showroom.Admin

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.car_showroom.Car_info_data
import com.example.car_showroom.CarsDatabase.CarsDb
import com.example.car_showroom.DataClass
import java.time.Instant
import com.example.car_showroom.R
import com.example.car_showroom.TO_Database.ToDb
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import kotlin.concurrent.thread

class TOAdmin : Fragment(), RecycleAdapterAdminTO.Listener {

    var items : ArrayList<TO_data_admin> = ArrayList()
    lateinit var db : ToDb

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_t_o_admin, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?){

        db = ToDb.getDb(requireActivity())

        thread{
            var d: LocalDate = LocalDate.now()
            var d1 : LocalDate

            val items_tmp = db.getDao().getAllItem()
            for (i in items_tmp){
                d1 = LocalDate.parse(i.date, DateTimeFormatter.ISO_LOCAL_DATE)
                if (d.isAfter(d1)){
                    db.getDao().delete(i.login)
                }

                if (i.status != 3) {
                    items.add(TO_data_admin(i.id!!, i.login, i.date, i.auto, i.time, i.status))
                }
            }
            requireActivity().runOnUiThread {
                val recyclerView: RecyclerView? = view?.findViewById(R.id.rcView)
                recyclerView!!.layoutManager = GridLayoutManager(requireContext(), 1)
                recyclerView.adapter = RecycleAdapterAdminTO(items, this)
            }
        }

    }

    companion object {
        @JvmStatic
        fun newInstance() = TOAdmin()

    }

    @SuppressLint("MissingInflatedId")
    override fun OnBtnYes(recItem: TO_data_admin) {
        val dialogBinding = layoutInflater.inflate(R.layout.dialog_car_saled, null)
        val myDialog = Dialog(requireActivity())
        myDialog.setContentView(dialogBinding)
        myDialog.setCancelable(true)
        myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myDialog.show()
        val b_ye = dialogBinding.findViewById<Button>(R.id.b_yes)
        val b_no = dialogBinding.findViewById<Button>(R.id.b_no)
        val txt = dialogBinding.findViewById<TextView>(R.id.text_d)
        txt.text = "Принять на ТО"
        b_ye.setOnClickListener{
            thread{
                db.getDao().updateStateTO(2, recItem.login)
                myDialog.dismiss()
                updateData()
            }
        }
        b_no.setOnClickListener{
            myDialog.dismiss()
        }
    }

    override fun OnBtnNo(item: TO_data_admin) {
        val dialogBinding = layoutInflater.inflate(R.layout.dialog_car_saled, null)
        val myDialog = Dialog(requireActivity())
        myDialog.setContentView(dialogBinding)
        myDialog.setCancelable(true)
        myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myDialog.show()
        val b_ye = dialogBinding.findViewById<Button>(R.id.b_yes)
        val b_no = dialogBinding.findViewById<Button>(R.id.b_no)
        val txt = dialogBinding.findViewById<TextView>(R.id.text_d)
        txt.text = "Отказ в ТО"
        b_ye.setOnClickListener{
            thread{
                db.getDao().updateStateTO(3, item.login)
                myDialog.dismiss()
                updateData()
            }
        }
        b_no.setOnClickListener{
            myDialog.dismiss()
        }
    }

    fun updateData(){
        items.clear()
        thread{
            val items_tmp = db.getDao().getAllItem()
            for (i in items_tmp){
                if (i.status != 3) {
                    items.add(TO_data_admin(i.id!!, i.login, i.date, i.auto, i.time, i.status))
                }
            }
            requireActivity().runOnUiThread {
                val recyclerView: RecyclerView? = view?.findViewById(R.id.rcView)
                recyclerView?.adapter?.notifyDataSetChanged()
            }
        }
    }

}
