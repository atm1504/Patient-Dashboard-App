package com.atm1504.gosocio.ui.SubmittedReports

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.atm1504.gosocio.R
import com.atm1504.gosocio.api.Report
import com.atm1504.gosocio.utils.utils

class SubmittedReportsAdapter : RecyclerView.Adapter<SubmittedReportsAdapter.ViewHolder>() {
    private var reportsList: List<Report> = listOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SubmittedReportsAdapter.ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.reports_card, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return reportsList.size
    }

    fun setSumittedReportsList(list: List<Report>) {
        this.reportsList = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.coins.text = reportsList.get(position).coins.toString()
        holder.road_name.text = reportsList.get(position).road_name
        holder.current_status.text = reportsList.get(position).current_status
        holder.complain.text = reportsList.get(position).complain
        holder.card.setOnClickListener {
            val bundle = bundleOf(
                "id" to reportsList.get(position).id
            )
            holder.itemView.findNavController().navigate(R.id.nav_report_details, bundle)
//            Log.d("KHANKI","Id is " + reportsList.get(position).id)
        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val road_name: TextView = itemView.findViewById(R.id.road_name)
        val current_status: TextView = itemView.findViewById(R.id.current_status)
        val complain: TextView = itemView.findViewById(R.id.complain)
        val coins: TextView = itemView.findViewById(R.id.coins)
        val card :CardView=itemView.findViewById(R.id.submitted_report_card)
    }
}