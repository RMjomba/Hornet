package com.reginaldateya.mymemory

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.reginaldateya.mymemory.models.BoardSize
import kotlin.math.min


class ImagePickerAdapter(
    private val context : Context,
    private val imageUris : List<Uri>,
    private val boardSize : BoardSize,
    private val imageClickListener: ImageClickListener
) : RecyclerView.Adapter<ImagePickerAdapter.ViewHolder>() {

    interface ImageClickListener {
        fun onPlaceHolderClicked()

    }


    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : ImagePickerAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.card_image, parent, false)
        val cardWidth = parent.width / boardSize.getWidth()
        val cardHeight = parent.height / boardSize.getHeight()
        val cardSideLength = min(cardWidth, cardHeight)
        val layoutParams = view.findViewById<ImageView>(R.id.ivCustomImage).layoutParams
        layoutParams.width = cardSideLength
        layoutParams.height = cardSideLength
        return ViewHolder(view)
    }

    override fun getItemCount() = boardSize.getNumPairs()

     override fun onBindViewHolder(holder : ViewHolder, position : Int) {
        if (position < imageUris.size) {
            holder.bind(imageUris[position])
        } else {
            holder.bind()
        }
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        fun bind(uri : Uri) {
            val ivCustomImage = itemView.findViewById<ImageView>(R.id.ivCustomImage)
            ivCustomImage.setImageURI(uri)
            ivCustomImage.setOnClickListener(null)

        }

        fun bind() {

            val ivCustomImage = itemView.findViewById<ImageView>(R.id.ivCustomImage)
            ivCustomImage.setOnClickListener {
                imageClickListener.onPlaceHolderClicked()

            }
        }
    }


}