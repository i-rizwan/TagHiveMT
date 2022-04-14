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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taghiveddemoapp.R
import com.example.taghiveddemoapp.adapter.CryptoAdapter
import com.example.taghiveddemoapp.adapter.OnItemClickListenerCommunicator
import com.example.taghiveddemoapp.databinding.ActivityMainBinding
import com.example.taghiveddemoapp.model.CryptoResponseItem
import com.example.taghiveddemoapp.utils.Resource
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

        getData()

        binding.swipeContainer.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(this, R.color.black))
        binding.swipeContainer.setColorSchemeColors(Color.WHITE)
        binding.swipeContainer.setOnRefreshListener { callRefreshLogic() }
    }


    private fun getData() {
        mainCryptoViewModel.getCryptos()
        mainCryptoViewModel.cryptoValue.observe(this) { event ->
            event.getContentIfNotHandled()?.let { response ->
                when (response) {
                    is Resource.Success -> {
                        hidePDialog()
                        response.data?.let {
                            arrayList = response.data
                            cryptoAdapter =
                                CryptoAdapter(applicationContext, arrayList, communicator)
                            binding.rcvList.layoutManager = LinearLayoutManager(this)
                            binding.rcvList.adapter = cryptoAdapter
                        }
                    }

                    is Resource.Error -> {
                        hidePDialog()
                        response.message?.let { message ->
                            Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
                        }
                    }

                    is Resource.Loading -> {
                        showdialog()
                    }
                }
            }
        }


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
        pDialog = ProgressDialog(this)
        pDialog.setMessage("Loading...")
        pDialog.setCancelable(false)
        pDialog.setCanceledOnTouchOutside(false)
        pDialog.show()
    }

    private val communicator = object : OnItemClickListenerCommunicator {
        override fun getOnItem(position: Int) {
            val intent = Intent(applicationContext, MainCryptoActivity::class.java)
            intent.putExtra("SYMBOL", arrayList[position].symbol)
            intent.putExtra("BASEASSET", arrayList[position].baseAsset)
            intent.putExtra("QUOTEASSET", arrayList[position].quoteAsset)
            startActivity(intent)
        }
    }
    private fun callRefreshLogic() {
        arrayList.clear()
        getData()
        cryptoAdapter = CryptoAdapter(applicationContext, arrayList, communicator)
        binding.rcvList.adapter = cryptoAdapter
        binding.swipeContainer.isRefreshing = false
    }

}