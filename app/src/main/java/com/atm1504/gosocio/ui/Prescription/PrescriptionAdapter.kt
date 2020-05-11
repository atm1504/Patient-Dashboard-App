package com.atm1504.gosocio.ui.Prescription

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.atm1504.gosocio.R
import com.atm1504.gosocio.api.PrescriptionResponse


class PrescriptionAdapter : RecyclerView.Adapter<PrescriptionAdapter.ViewHolder>() {
    private var presList: List<Prescription> = listOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PrescriptionAdapter.ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_prescription, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return presList.size
    }

    fun setPrescriptionList(list: List<Prescription>) {
        this.presList = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.symptoms.text = presList.get(position).symptoms.toString()
        holder.medicine.text = presList.get(position).medicine.toString()
        holder.dose.text = presList.get(position).dose.toString()
        holder.date.text = presList.get(position).date.toString()
        holder.card.setOnClickListener {

        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val symptoms: TextView = itemView.findViewById(R.id.pres_symptoms)
        val medicine: TextView = itemView.findViewById(R.id.pres_medicine)
        val dose: TextView = itemView.findViewById(R.id.pres_dose)
        val date: TextView = itemView.findViewById(R.id.pres_date)
        val card :CardView=itemView.findViewById(R.id.pres_cardview)
    }
}