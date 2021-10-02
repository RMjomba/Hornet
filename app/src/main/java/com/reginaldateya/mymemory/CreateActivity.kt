package com.reginaldateya.mymemory

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.reginaldateya.mymemory.models.BoardSize
import com.reginaldateya.mymemory.utils.EXTRA_BOARD_SIZE

class CreateActivity : AppCompatActivity() {

    private lateinit var rvImagePicker: RecyclerView
    private lateinit var etGameName : EditText
    private lateinit var btnSave: Button

    private lateinit var boardSize : BoardSize
    private var numImagesRequired = -1
    private val chosenImageUris = mutableListOf<Uri>()
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        rvImagePicker = findViewById(R.id.rvImagePicker)
        etGameName = findViewById(R.id.etGameName)
        btnSave = findViewById(R.id.btnSave)



        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        boardSize = intent.getSerializableExtra(EXTRA_BOARD_SIZE) as BoardSize
        numImagesRequired = boardSize.getNumPairs()
        supportActionBar?.title = "Choose pics (0 / $numImagesRequired)"

        rvImagePicker.adapter = ImagePickerAdapter(this, chosenImageUris, boardSize, object: ImagePickerAdapter.ImageClickListener {
            override fun onPlaceHolderClicked() {
                launchIntentForPhotos()
            }

        } )
        rvImagePicker.setHasFixedSize(true)
        rvImagePicker.layoutManager = GridLayoutManager(this, boardSize.getWidth())

    }

    override fun onOptionsItemSelected(item : MenuItem) : Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
    private fun launchIntentForPhotos() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
    }
}