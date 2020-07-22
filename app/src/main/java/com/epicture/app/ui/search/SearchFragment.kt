package com.epicture.app.ui.search

import android.app.AlertDialog
import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.epicture.app.R
import com.epicture.app.models.ImageImgurList
import com.epicture.app.services.ApiInterface
import com.epicture.app.services.RetrofitClient
import com.epicture.app.ui.DataAdapter
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchFragment : Fragment() {

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var imageView : ImageView
    lateinit var recyclerView : RecyclerView
    lateinit var gridLayoutManager : GridLayoutManager
    var arrayListImgurUri : java.util.ArrayList<Uri> = java.util.ArrayList()
    private lateinit var searchValue : CharSequence

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        searchViewModel =
            ViewModelProvider(this).get(SearchViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_search, container, false)
        val imglyt = inflater.inflate(R.layout.image_layout, container, false)
        imageView = imglyt.findViewById(R.id.imageView)
        recyclerView = root.findViewById(R.id.searchRecyclerView)
        gridLayoutManager = GridLayoutManager(this.requireActivity(), 2)
        recyclerView.layoutManager = gridLayoutManager

        //prepareData(this)
        val dataAdaptater = DataAdapter(this.requireActivity(), arrayListImgurUri, true)
        recyclerView.adapter = dataAdaptater


        val txtInptLayout = root.findViewById(R.id.textInputLayout) as TextInputLayout
        val editTextVar = txtInptLayout.editText
        editTextVar?.setOnKeyListener { v, keyCode, event ->
            if((event.action == KeyEvent.ACTION_DOWN)
                && (event.keyCode == KeyEvent.KEYCODE_ENTER)){
                //Do something, such as FAIRE POP LE TEXTE DE LA SEARCH
                searchValue = editTextVar.text
                prepareData(this, searchValue.toString())
                val mAlertDialog = AlertDialog.Builder(context)
                mAlertDialog.setMessage(searchValue)
                mAlertDialog.show()
                return@setOnKeyListener true
            }

            false
        }

        return root
    }
    private fun prepareData(value: Fragment, search: String) {
        val retrofitClient: RetrofitClient? = RetrofitClient()
        val apiInterface: ApiInterface? = retrofitClient?.createService(ApiInterface::class.java)
        val call: Call<ImageImgurList>? = apiInterface?.getImages(search)
        call?.enqueue(object : Callback<ImageImgurList> {
            override fun onResponse(
                call: Call<ImageImgurList>?,
                response: Response<ImageImgurList>?
            ) {
                if (response != null && response.isSuccessful) {
                    response.body()?.getImages()?.forEach {
                        arrayListImgurUri.add(Uri.parse(it.link.toString()))
                        Log.i("URL", it.link.toString())
                    }
                    arrayListImgurUri.forEach {
                        Log.i("TEST", it.toString())
                    }
                    val dataAdaptater = DataAdapter(value.requireActivity(), arrayListImgurUri, isClickable = false)
                    recyclerView.adapter = dataAdaptater
                }
            }
            override fun onFailure(call: Call<ImageImgurList>?, t: Throwable?) {
                Log.e("Error", "Couldn't get result for request search on gallery")
            }
        })
    }
}