package com.adammcneilly.pokedex.di

import com.adammcneilly.pokedex.database.PokedexDatabase

interface StorageGraph {
    val database: PokedexDatabase
}