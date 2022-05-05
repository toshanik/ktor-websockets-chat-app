package com.toshanik.di

import com.toshanik.data.MessageDataSourceImpl
import com.toshanik.data.model.MessageDataSource
import com.toshanik.room.RoomsController
import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

val mainModule = module{
    single{
        KMongo.createClient()
            .coroutine
            .getDatabase("message_db_yt")
    }
    single<MessageDataSource>{
        MessageDataSourceImpl(get())
    }
    single{
        RoomsController(get())
    }
}