@file:JvmName("Main")

package com.rxhomework

import com.rxhomework.internal.Permission
import com.rxhomework.internal.getUsersFromSource1
import com.rxhomework.internal.getUsersFromSource2
import com.rxhomework.internal.withError
import io.reactivex.Flowable
import io.reactivex.functions.BiFunction
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException
import kotlin.random.Random

// flatMap users to permissions,
// distinct their
fun task1(): Flowable<Permission> =
    getUsersFromSource1()
        .flatMap {
            Flowable.fromIterable(it.permissions)
        }
        .distinct()


// flatMap users to permissions with random delay,
// distinct their
fun task2(): Flowable<Permission> =
    getUsersFromSource2()
        .flatMap {
            Flowable.fromIterable(it.permissions)
                .delay(Random(100).nextLong(), TimeUnit.MILLISECONDS)
        }
        .distinct()


// concatMap users to permissions with random delay,
// distinct their
fun task3(): Flowable<Permission> =
    getUsersFromSource2()
        .concatMap {
            Flowable.fromIterable(it.permissions)
                .delay(Random(100).nextLong(), TimeUnit.MILLISECONDS)
        }


// map user from different sources to usernames,
// merge their
fun task4(): Flowable<String> =
    Flowable
        .merge(getUsersFromSource1(), getUsersFromSource2())
        .map {
            it.username
        }


// implement 2 times retrying
// with linear-grown delay,
// step equals 2 sec;
// on 3rd time throw error
fun task5(): Flowable<Int> {
    return withError()
        .retryWhen {
            Flowable.zip<Throwable, Int, Int>(
                it,
                Flowable.range(1, 3),
                BiFunction { i1, i2 ->
                    if (i1 is TimeoutException) {
                        i2
                    } else {
                        throw i1
                    }
                }
            )
        }.flatMap {
            val secTillRetry = 2 * it
            Flowable.just(it)
                .delay(secTillRetry.toLong(), TimeUnit.SECONDS)
        }
}

fun main() {
    task1().blockingForEach { println(it) }
    println()

    task2().blockingForEach { println(it) }
    println()

    task3().blockingForEach { println(it) }
    println()

    task4().blockingForEach { println(it) }
    println()

    task5().blockingSubscribe({
        println(it)
    }, {
        println(it)
    })
    println()
}