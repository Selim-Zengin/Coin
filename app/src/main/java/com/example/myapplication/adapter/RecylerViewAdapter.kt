package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.CryptoModel
import kotlinx.android.synthetic.main.row_layout.view.*

class RecylerViewAdapter (private var cryptoList:ArrayList<CryptoModel>, private val listener:Listener):RecyclerView.Adapter<RecylerViewAdapter.RowHolder>() {

    interface Listener{
        fun onItemClick(cryptoModel: CryptoModel)
    }

    class RowHolder(view:View) : RecyclerView.ViewHolder(view) {

        fun bind(cryptoModel: CryptoModel,position: Int,listener:Listener){

            itemView.setOnClickListener {
                listener.onItemClick(cryptoModel)
            }

            itemView.textName.text=cryptoModel.currency
            itemView.textPrice.text=cryptoModel.price
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        var view= LayoutInflater.from(parent.context).inflate(R.layout.row_layout,parent,false)
        return RowHolder(view)
    }

    override fun getItemCount(): Int {
        return cryptoList.size
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        holder.bind(cryptoList[position],position,listener)
    }
}