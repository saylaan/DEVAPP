package com.epicture.app.ui.home

import android.app.AlertDialog
import android.net.Uri
import com.epicture.app.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.epicture.app.models.ImageImgurList
import com.epicture.app.services.ApiInterface
import com.epicture.app.services.RetrofitClient
import com.epicture.app.ui.DataAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var imageView : ImageView
    lateinit var recyclerView : RecyclerView
    lateinit var gridLayoutManager : GridLayoutManager
    var arrayListImgurUri : ArrayList<Uri> = ArrayList()

//    /* Geoff */
//    private var api : Api  = App.instance?.getApi()!!
//    /* Geoff */
/*
    private lateinit var itemSelector: Selector
    // Use the 'by activityViewModels()' Kotlin property delegate
    // from the fragment-ktx artifact
    private val model: SharedViewModel by activityViewModels()
*/

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val imglyt = inflater.inflate(R.layout.image_layout, container, false)
        imageView = imglyt.findViewById(R.id.imageView)
        recyclerView = root.findViewById(R.id.homeRecyclerView)
        gridLayoutManager = GridLayoutManager(this.requireActivity(), 2)
        recyclerView.layoutManager = gridLayoutManager

        prepareData(this)
        var dataAdaptater = DataAdapter(this.requireActivity(), arrayListImgurUri, isClickable = false)
        recyclerView.adapter = dataAdaptater

        val randomBtn = root.findViewById<FloatingActionButton>(R.id.randomBtn)
        randomBtn.setOnClickListener {
            prepareData(this)
            dataAdaptater = DataAdapter(this.requireActivity(), arrayListImgurUri, isClickable = false)
            recyclerView.adapter = dataAdaptater
            popMsg("RANDOM")
        }

        val latestBtn = root.findViewById<FloatingActionButton>(R.id.latestBtn)
        latestBtn.setOnClickListener {
            prepareDataLatest(this)
            dataAdaptater = DataAdapter(this.requireActivity(), arrayListImgurUri, isClickable = false)
            recyclerView.adapter = dataAdaptater
            popMsg("LATEST")
        }
        return root
    }

    private fun popMsg(msg : CharSequence) {
        val mAlertDialog = AlertDialog.Builder(context)
        mAlertDialog.setMessage(msg)
        mAlertDialog.show()
    }
    private fun prepareDataLatest(value: Fragment) {
        val retrofitClient: RetrofitClient? = RetrofitClient()
        val apiInterface: ApiInterface? = retrofitClient?.createService(ApiInterface::class.java)
        val call: Call<ImageImgurList>? = apiInterface?.getLatestImages()
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
                        Log.i("LATEST", it.toString())
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
    private fun prepareData(value: Fragment) {
        val retrofitClient: RetrofitClient? = RetrofitClient()
        val apiInterface: ApiInterface? = retrofitClient?.createService(ApiInterface::class.java)
        val call: Call<ImageImgurList>? = apiInterface?.getAllImages()
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