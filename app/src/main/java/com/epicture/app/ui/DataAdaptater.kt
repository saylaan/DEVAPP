package com.epicture.app.ui

import android.app.AlertDialog
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.epicture.app.R


class DataAdapter(
    private val context: Context,
    imageUrls: ArrayList<Uri>,
    private val isClickable : Boolean )
    : RecyclerView.Adapter<DataAdapter.ViewHolder?>() {

    private val imageUrls: ArrayList<Uri>

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        i: Int
    ): ViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.image_layout, viewGroup, false)
        return ViewHolder(view)
    }

    /**
     * gets the image url from adapter and passes to Glide API to load the image
     *
     * @param viewHolder
     * @param i
     */
    override fun onBindViewHolder(
        viewHolder: ViewHolder,
        i: Int
    ) {
        if (isClickable) {
            viewHolder.img.setOnClickListener {
                val mAlertDialog = AlertDialog.Builder(context)
                mAlertDialog.setMessage("CLICK")
                mAlertDialog.show()
            }
        }
        Glide.with(context).load(imageUrls[i]).into(viewHolder.img)
    }

    override fun getItemCount() : Int
    {
        return imageUrls.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var img: ImageView

        init {
            img = view.findViewById(R.id.imageView)
        }
    }

    init {
        this.imageUrls = imageUrls
    }
}