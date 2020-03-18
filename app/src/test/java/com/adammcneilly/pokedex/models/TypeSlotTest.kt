package com.adammcneilly.pokedex.models

import com.adammcneilly.pokedex.network.models.TypeDTO
import com.adammcneilly.pokedex.network.models.TypeSlotDTO
import org.junit.Assert.assertEquals
import org.junit.Test

class TypeSlotTest {

    @Test
    fun mapFromTypeSlotDTO() {
        val testSlot = 1
        val testType = TypeDTO()
        val typeSlotDTO = TypeSlotDTO(slot = testSlot, type = testType)

        val typeSlot = typeSlotDTO.toTypeSlot()
        assertEquals(testSlot, typeSlot?.slot)
        assertEquals(testType.toType(), typeSlot?.type)
    }
}