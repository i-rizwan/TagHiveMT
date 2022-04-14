package com.example.taghiveddemoapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.taghiveddemoapp.R
import com.example.taghiveddemoapp.model.CryptoResponseItem

class CryptoAdapter(
    context: Context,
    private val cryptolist: ArrayList<CryptoResponseItem>,
    private val communicator: OnItemClickListenerCommunicator
) :
    RecyclerView.Adapter<CryptoAdapter.CryptoHolder>() {
    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoHolder {

        val rowView: View = layoutInflater.inflate(R.layout.raw_crypto, parent, false)
        return CryptoHolder(rowView)


    }

    fun addData(list: ArrayList<CryptoResponseItem>) {
        cryptolist.addAll(list)

    }

    override fun onBindViewHolder(holder: CryptoHolder, position: Int) {
        val items = cryptolist[position]
        holder.txtsymbol.text = items.baseAsset
        holder.txtsymbolc.text = "/ " + items.quoteAsset
        holder.txtprice.text = items.lastPrice
        holder.txtvolume.text = items.volume
        holder.txtcryptoname.text = items.baseAsset

        holder.linearlayout.setOnClickListener {
            communicator.getOnItem(position)
        }

    }

    override fun getItemCount(): Int {
        return cryptolist.size
    }

    inner class CryptoHolder(view: View) : RecyclerView.ViewHolder(view) {
        var txtsymbol = view.findViewById(R.id.txtsymbol) as TextView
        var txtsymbolc = view.findViewById(R.id.txtsymbolc) as TextView
        var txtprice = view.findViewById(R.id.txtprice) as TextView
        var txtvolume = view.findViewById(R.id.txtvolume) as TextView
        val txtcryptoname = view.findViewById(R.id.txtcryptoname) as TextView
        val linearlayout = view.findViewById(R.id.linearlayout) as LinearLayout
    }
}

interface OnItemClickListenerCommunicator {
    fun getOnItem(position: Int)
}