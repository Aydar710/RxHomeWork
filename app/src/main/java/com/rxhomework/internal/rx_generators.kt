package com.rxhomework.internal

import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import java.lang.IllegalArgumentException
import java.lang.RuntimeException
import java.util.Date
import java.util.concurrent.TimeoutException

data class Permission(
    val name: String
)

data class User(
    val id: Long,
    val username: String,
    val permissions: Set<Permission>
)

data class Token(
    val token: String,
    val creationDate: Date,
    val expirationDate: Date
)

data class CurrentUser(
    val user: User,
    val token: Token
)

fun getUsersFromSource1(): Flowable<User> {
    return Flowable.fromIterable(
        listOf(
            User(
                1,
                "username1",
                setOf(
                    Permission(
                        "read_articles"
                    )
                )
            ),
            User(
                2,
                "username2",
                setOf(
                    Permission(
                        "write_articles"
                    )
                )
            ),
            User(
                3,
                "username3",
                setOf()
            )
        )
    ).subscribeOn(Schedulers.io())
}

fun getUsersFromSource2(): Flowable<User> {
    return Flowable.fromIterable(
        listOf(
            User(
                12572,
                "username12572",
                setOf(
                    Permission(
                        "execute"
                    )
                )
            ),
            User(
                211,
                "username211",
                setOf(
                    Permission(
                        "write_articles"
                    ),
                    Permission(
                        "read_keys"
                    )
                )
            ),
            User(
                4737,
                "username4737",
                setOf(
                    Permission(
                        "write_articles"
                    ),
                    Permission(
                        "read_keys"
                    ),
                    Permission(
                        "make_some_shit"
                    )
                )
            )
        )
    ).subscribeOn(Schedulers.io())
}

fun withError(): Flowable<Int> =
    Flowable.error<Int>(TimeoutException("What happens?")) // Поменял Exception, чтобы в 5 таске таймаут падал :)
        .startWithArray(1, 2, 3, 4)
        .subscribeOn(Schedulers.io())

fun forTest(): Flowable<Int> {
    return Flowable.fromArray(5, 2, 1, 4, 3)
        .subscribeOn(Schedulers.io())
}

fun forTestWithError(): Flowable<Int> {
    return Flowable.error<Int>(IllegalArgumentException("You cannot catch me!"))
        .startWithArray(1, 2, 3, 4)
        .subscribeOn(Schedulers.io())
}