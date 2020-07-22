package com.epicture.app.ui.upload

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.epicture.app.R
import kotlinx.android.synthetic.main.image_layout.*

class UploadFragment : Fragment() {

    private lateinit var uploadViewModel: UploadViewModel
    private val REQUEST_IMAGE_CAPTURE = 1
    private lateinit var vGroup : ViewGroup

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vGroup = container!!
        uploadViewModel =
            ViewModelProvider(this).get(UploadViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_upload, container, false)
        val buttonUpload = root.findViewById<Button>(R.id.buttonUpload)
        uploadViewModel.text.observe(this, Observer {
            buttonUpload.text = it
        })

        val clickListener = View.OnClickListener {
            uploadViewModel.textUpload.observe(this, Observer {
                buttonUpload.text = it
            })
            dispatchTakePictureIntent()
        }

        buttonUpload.setOnClickListener(clickListener)

        return root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data!!.extras!!.get("data") as Bitmap
            val imageView = this.view!!.findViewById<ImageView>(R.id.imageViewUpload)
                LayoutInflater.from(this.context)
                .inflate(R.layout.image_layout, vGroup, false)
            imageView.setImageBitmap(imageBitmap)
        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(this.requireContext().packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }
}