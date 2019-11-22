package com.rxhomework

import com.rxhomework.internal.forTest
import com.rxhomework.internal.forTestWithError
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test

class HwTest {

    @Before
    fun setup(){
        RxJavaPlugins.setIoSchedulerHandler {
            Schedulers.trampoline()
        }
    }

    // make sure that
    //     value at index 0 equals 5;
    //     value at index 2 equals 1;
    //     errors are not present;
    //     chain is completed
    @Test
    fun task6() {
         forTest()
             .test()
             .assertValueAt(0, 5)
             .assertValueAt(2, 1)
             .assertNoErrors()
             .assertComplete()
    }

    // make sure that
    //     values are the same with [1, 2, 3, 4];
    //     type of error is IllegalArgumentException
    @Test
    fun task7() {
         forTestWithError()
             .test()
             .assertResult(1, 2, 3, 4)
             .assertFailure(IllegalArgumentException::class.java)

    }
}