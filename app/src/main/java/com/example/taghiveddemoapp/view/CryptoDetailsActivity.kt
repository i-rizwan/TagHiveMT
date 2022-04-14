package com.example.taghiveddemoapp.view

import android.app.Activity
import android.app.ProgressDialog
import android.content.ContextWrapper
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.example.taghiveddemoapp.R
import com.example.taghiveddemoapp.databinding.ActivityCryptoDetailsBinding
import com.example.taghiveddemoapp.utils.Status
import com.example.taghiveddemoapp.viewModule.MainCryptoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CryptoDetailsActivity : AppCompatActivity() {

    lateinit var binding: ActivityCryptoDetailsBinding

    lateinit var pDialog: ProgressDialog
    private lateinit var symbol: String
    private lateinit var baseasset: String
    private lateinit var quoteasset: String
    private lateinit var tooltext: String
    private val mainCryptoViewModel: MainCryptoViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crypto_details)
        binding = ActivityCryptoDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        pDialog = ProgressDialog(this)

        symbol = intent.getStringExtra("currency_symbol")!!
        baseasset = intent.getStringExtra("base_asset")!!
        quoteasset = intent.getStringExtra("quote_asset")!!
        setUIAndData()
        getDetailFromNetwork(symbol)
    }

    fun setUIAndData() {
        //   setSupportActionBar(binding.toolBar.mainToolBar)


        binding.toolBar.back.setOnClickListener { finish() }
        binding.toolBar.back.isVisible = true
        tooltext = baseasset.uppercase() + "/" + quoteasset.uppercase()
        binding.toolBar.txtname.text = tooltext
    }

    private fun getDetailFromNetwork(symbol: String) {
        mainCryptoViewModel.getCryptoDetails(symbol)
        mainCryptoViewModel.cryptoGetDetailResponse.observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    hidePDialog()
                    it.data?.let { CryptoResponseDetails ->
                        binding.txtcname.setText(CryptoResponseDetails.baseAsset)
                        binding.txtnamec.setText("Name: ")
                        binding.txtcopenprice.setText("Open Price : " + CryptoResponseDetails.openPrice)
                        binding.txtclowprice.setText("Low Price : " + CryptoResponseDetails.lowPrice)
                        binding.txtchighprice.setText("High Price : " + CryptoResponseDetails.highPrice)
                        binding.txtclastprice.setText("Last Price : " + CryptoResponseDetails.lastPrice)
                        binding.txtcvolume.setText("Volume : " + CryptoResponseDetails.volume)
                    }
                }
                Status.LOADING -> {
                    showDialog()
                }
                Status.ERROR -> {
                    hidePDialog()
                    it.message?.let { message ->
                        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })


    }

    private fun hidePDialog() {
        if (pDialog.isShowing()) {
            val context = (pDialog.getContext() as ContextWrapper).baseContext
            if (context is Activity) {
                if (!context.isFinishing) pDialog.dismiss()
            } else pDialog.dismiss()
        }
    }

    private fun showDialog() {

        pDialog.setMessage("Loading...")
        pDialog.setCancelable(false)
        pDialog.setCanceledOnTouchOutside(false)
        pDialog.show()
    }
}