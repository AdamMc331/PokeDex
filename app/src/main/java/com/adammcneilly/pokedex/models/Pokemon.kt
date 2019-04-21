package com.adammcneilly.pokedex.models

data class Pokemon(
    val name: String? = null,
    val url: String? = null,
    val sprites: Sprites? = null,
    val types: List<TypeSlot>? = null
) {
    val sortedTypes: List<Type>
        get() = types?.sortedBy { it.slot }?.mapNotNull { it.type }.orEmpty()
}