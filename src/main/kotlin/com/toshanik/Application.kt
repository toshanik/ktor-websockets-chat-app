package com.toshanik

import com.toshanik.di.mainModule
import com.toshanik.lib.KoinPlugin
import io.ktor.server.application.*
import com.toshanik.plugins.*
//import io.ktor.application.*
import io.ktor.util.*
import kotlinx.coroutines.CoroutineScope
import org.koin.core.KoinApplication
import org.koin.ktor.ext.Koin
import io.ktor.events.EventDefinition
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.java.KoinJavaComponent
import org.koin.java.KoinJavaComponent.getKoin


import org.koin.ktor.ext.modules

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    install(KoinPlugin) {
        modules(mainModule)
    }
    configureSockets()
    configureRouting()
    configureSecurity()
    configureSerialization()
    configureMonitoring()
}