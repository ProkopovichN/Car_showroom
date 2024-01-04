package com.example.car_showroom.Admin

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.car_showroom.Car_info_data
import com.example.car_showroom.CarsDatabase.CarsDb
import com.example.car_showroom.R
import com.example.car_showroom.SaleDatabase.SaleDb
import kotlin.concurrent.thread

class SaleAdmin : Fragment(), RecycleAdapterAdminSale.Listener {

    var items : ArrayList<SaleDataAdmin> = ArrayList()
    lateinit var db : SaleDb
    lateinit var db1 : CarsDb

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sale_admin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = SaleDb.getDb(requireActivity())
        db1 = CarsDb.getDb(requireActivity())

        thread{
            val items_tmp = db.getDao().getAllItems()
            for (i in items_tmp){
                if (i.status != 2 && i.status != 3) {
                    items.add(SaleDataAdmin(i.id!!, i.photo, i.nameCar, i.user, i.status, i.id_car))
                }
            }
            requireActivity().runOnUiThread {
                val recyclerView: RecyclerView? = view?.findViewById(R.id.rcView)
                recyclerView!!.layoutManager = GridLayoutManager(requireContext(), 1)
                recyclerView.adapter = RecycleAdapterAdminSale(items, this)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = SaleAdmin()
    }

    override fun OnBtnYes(recItem: SaleDataAdmin) {
        val dialogBinding = layoutInflater.inflate(R.layout.dialog_car_saled, null)
        val myDialog = Dialog(requireActivity())
        myDialog.setContentView(dialogBinding)
        myDialog.setCancelable(true)
        myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myDialog.show()
        val b_ye = dialogBinding.findViewById<Button>(R.id.b_yes)
        val b_no = dialogBinding.findViewById<Button>(R.id.b_no)
        val txt = dialogBinding.findViewById<TextView>(R.id.text_d)
        txt.text = "Продать автомобиль?"
        b_ye.setOnClickListener{
            thread{
                db.getDao().updateStateSale(2, recItem.user)
                db1.getDao().delete(recItem.id_car)
                myDialog.dismiss()
                updateData()
            }
        }
        b_no.setOnClickListener{
            myDialog.dismiss()
        }
    }

    override fun OnBtnNo(item: SaleDataAdmin) {
        val dialogBinding = layoutInflater.inflate(R.layout.dialog_car_saled, null)
        val myDialog = Dialog(requireActivity())
        myDialog.setContentView(dialogBinding)
        myDialog.setCancelable(true)
        myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myDialog.show()
        val b_ye = dialogBinding.findViewById<Button>(R.id.b_yes)
        val b_no = dialogBinding.findViewById<Button>(R.id.b_no)
        val txt = dialogBinding.findViewById<TextView>(R.id.text_d)
        txt.text = "Отклонить заявку?"
        b_ye.setOnClickListener{
            thread{
                db.getDao().updateStateSale(3, item.user)
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
            val items_tmp = db.getDao().getAllItems()
            for (i in items_tmp){
                if (i.status != 2 && i.status != 3) {
                    items.add(SaleDataAdmin(i.id!!, i.photo, i.nameCar, i.user, i.status, i.id_car))
                }
            }
            requireActivity().runOnUiThread {
                val recyclerView: RecyclerView? = view?.findViewById(R.id.rcView)
                recyclerView?.adapter?.notifyDataSetChanged()
            }
        }
    }
}

