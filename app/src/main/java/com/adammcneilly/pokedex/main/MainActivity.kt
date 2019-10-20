package com.adammcneilly.pokedex.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.adammcneilly.pokedex.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
    }
}