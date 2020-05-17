package com.adammcneilly.pokedex

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import org.mockito.Mockito
import org.mockito.stubbing.OngoingStubbing

fun <T> whenever(methodCall: T): OngoingStubbing<T> = Mockito.`when`(methodCall)

class TestObserver<T> : Observer<T> {
    var observedValue: T? = null

    override fun onChanged(t: T) {
        observedValue = t
    }
}

fun <T> LiveData<T>.testObserver() = TestObserver<T>().also {
    observeForever(it)
}