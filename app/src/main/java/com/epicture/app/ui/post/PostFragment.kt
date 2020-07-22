package com.epicture.app.ui.post

import android.app.AlertDialog
import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import android.util.Log
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
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class PostFragment : Fragment() {

    private lateinit var postViewModel: PostViewModel
    private lateinit var imageView : ImageView
    lateinit var recyclerView : RecyclerView
    lateinit var gridLayoutManager : GridLayoutManager
    var arrayListImgurUri : ArrayList<Uri> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        postViewModel =
            ViewModelProvider(this).get(PostViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_post, container, false)
        val imglyt = inflater.inflate(R.layout.image_layout, container, false)
        imageView = imglyt.findViewById(R.id.imageView)
        recyclerView = root.findViewById(R.id.postRecyclerView)
        gridLayoutManager = GridLayoutManager(this.requireActivity(), 2)
        recyclerView.layoutManager = gridLayoutManager

        prepareData(this)
        val dataAdaptater = DataAdapter(this.requireActivity(), arrayListImgurUri, false)
        recyclerView.adapter = dataAdaptater

        val randomBtn = root.findViewById<FloatingActionButton>(R.id.addBtn)
        randomBtn.setOnClickListener {
            popMsg("POST FROM GALLERY")
        }

        return root
    }

    private fun popMsg(msg : CharSequence) {
        val mAlertDialog = AlertDialog.Builder(context)
        mAlertDialog.setMessage(msg)
        mAlertDialog.show()
    }

    private fun prepareData(value: Fragment) {
        val retrofitClient: RetrofitClient? = RetrofitClient()
        val apiInterface: ApiInterface? = retrofitClient?.createService(ApiInterface::class.java)
        val call: Call<ImageImgurList>? = apiInterface?.getUserImages()
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
