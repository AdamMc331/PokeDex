package com.adammcneilly.pokedex

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import org.mockito.Mockito
import org.mockito.stubbing.OngoingStubbing

fun <T> whenever(methodCall: T): OngoingStubbing<T> = Mockito.`when`(methodCall)

class TestObserver<T> : Observer<T> {
    var observedValue: T? = null
    var observedValues: MutableList<T?> = mutableListOf()

    override fun onChanged(t: T) {
        observedValue = t
        observedValues.add(t)
    }
}

fun <T> LiveData<T>.testObserver() = TestObserver<T>().also {
    observeForever(it)
}