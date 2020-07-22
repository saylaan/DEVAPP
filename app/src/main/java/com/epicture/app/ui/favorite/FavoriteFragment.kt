package com.epicture.app.ui.favorite

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
import kotlinx.android.synthetic.main.image_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class FavoriteFragment : Fragment() {

    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var imageView : ImageView
    lateinit var recyclerView : RecyclerView
    lateinit var gridLayoutManager : GridLayoutManager
    var arrayListImgurUri : ArrayList<Uri> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        favoriteViewModel =
            ViewModelProvider(this).get(FavoriteViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_favorite, container, false)
        val imglyt = inflater.inflate(R.layout.image_layout, container, false)
        imageView = imglyt.findViewById(R.id.imageView)
        recyclerView = root.findViewById(R.id.favoriteRecyclerView)
        gridLayoutManager = GridLayoutManager(this.requireActivity(), 2)
        recyclerView.layoutManager = gridLayoutManager

//        val arrayListImgur = prepareData()
        prepareData(this)
        val dataAdaptater = DataAdapter(this.requireActivity(), arrayListImgurUri, true)
        recyclerView.adapter = dataAdaptater

        return root
    }

    private fun prepareData(value: Fragment) {
        val retrofitClient: RetrofitClient? = RetrofitClient()
        val apiInterface: ApiInterface? = retrofitClient?.createService(ApiInterface::class.java)
        val call: Call<ImageImgurList>? = apiInterface?.getUserFavorites()
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
//    private fun prepareData()/* : ArrayList<Uri>*/
//    {
//        val arrayListImgur = ArrayList<Uri>(10)
//        arrayListImgur.add(Uri.parse("https://i.imgur.com/bMO9Jl7.png"))
//        arrayListImgur.add(Uri.parse("https://i.imgur.com/tGbaZCY.jpg"))
//        arrayListImgur.add(Uri.parse("https://i.imgur.com/W6u72j4.jpg"))
//        arrayListImgur.add(Uri.parse("https://i.imgur.com/1NIWxFsb.jpg"))
//        arrayListImgur.add(Uri.parse("https://i.imgur.com/r6IjlWcb.jpg"))
//        arrayListImgur.add(Uri.parse("https://i.imgur.com/2R7QheBb.jpg"))
//        arrayListImgur.add(Uri.parse("https://i.imgur.com/PiZjQyDb.jpg"))
//        arrayListImgur.add(Uri.parse("https://i.imgur.com/ZjfYqZgb.jpg"))
//        arrayListImgur.add(Uri.parse("https://i.imgur.com/2mdrqDfb.jpg"))
//        return arrayListImgur
//    }

}