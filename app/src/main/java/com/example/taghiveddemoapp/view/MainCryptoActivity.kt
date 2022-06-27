package com.example.taghiveddemoapp.view

import android.app.Activity
import android.app.ProgressDialog
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taghiveddemoapp.R
import com.example.taghiveddemoapp.adapter.CryptoAdapter
import com.example.taghiveddemoapp.adapter.OnItemClickListenerCommunicator
import com.example.taghiveddemoapp.databinding.ActivityMainBinding
import com.example.taghiveddemoapp.model.CryptoResponseItem
import com.example.taghiveddemoapp.utils.NetworkResult
import com.example.taghiveddemoapp.viewModule.MainCryptoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainCryptoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainCryptoViewModel: MainCryptoViewModel by viewModels()

    private lateinit var cryptoAdapter: CryptoAdapter
    private lateinit var pDialog: ProgressDialog
    private lateinit var arrayList: ArrayList<CryptoResponseItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        arrayList = ArrayList()
        setupUI()
        getData()


    }

    private fun setupUI() {
        pDialog = ProgressDialog(this)
        binding.swipeContainer.setColorSchemeColors(Color.WHITE)
        binding.swipeContainer.setProgressBackgroundColorSchemeColor(
            ContextCompat.getColor(
                this,
                R.color.black
            )
        )

        binding.rcvList.layoutManager = LinearLayoutManager(this)
        cryptoAdapter = CryptoAdapter(applicationContext, arrayListOf(), communicator)

        binding.rcvList.addItemDecoration(
            DividerItemDecoration(
                binding.rcvList.context,
                (binding.rcvList.layoutManager as LinearLayoutManager).orientation
            )
        )

        binding.rcvList.adapter = cryptoAdapter

        binding.swipeContainer.setOnRefreshListener { callRefreshLogic() }

    }

    private fun getData() {
        mainCryptoViewModel.getCryptoList()
        mainCryptoViewModel.finalCryptoResponse.observe(this, Observer {


            when (it) {
                is NetworkResult.Success -> {
                    hidePDialog()
                    hidePDialog()
                    it.data?.let {
                        arrayList = it
                        renderList(it)
                    }
                }
                is NetworkResult.Error -> {
                    hidePDialog()
                    it.message?.let { message ->
                        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
                    }
                }

                is NetworkResult.Loading -> {
                    showdialog()
                }
            }
        })


    }

    private fun renderList(dataList: ArrayList<CryptoResponseItem>) {

        cryptoAdapter.addData(dataList)
        cryptoAdapter.notifyDataSetChanged()
    }

    private fun hidePDialog() {
        if (pDialog.isShowing()) {
            val context = (pDialog.getContext() as ContextWrapper).baseContext
            if (context is Activity) {
                if (!context.isFinishing) pDialog.dismiss()
            } else pDialog.dismiss()
        }
    }

    @Suppress("DEPRECATION")
    private fun showdialog() {

        pDialog.setMessage("Loading...")
        pDialog.setCancelable(false)
        pDialog.setCanceledOnTouchOutside(false)
        pDialog.show()
    }

    private val communicator = object : OnItemClickListenerCommunicator {
        override fun getOnItem(position: Int) {
            val intent = Intent(applicationContext, CryptoDetailsActivity::class.java)
            intent.putExtra("currency_symbol", arrayList[position].symbol)
            intent.putExtra("base_asset", arrayList[position].baseAsset)
            intent.putExtra("quote_asset", arrayList[position].quoteAsset)
            startActivity(intent)
        }
    }


    private fun callRefreshLogic() {
        getData()
        binding.swipeContainer.isRefreshing = false
    }

}