package com.toshanik.plugins

import com.toshanik.lib.inject
import com.toshanik.room.RoomsController
import com.toshanik.routes.chatSocket
import com.toshanik.routes.getAllMessages
import io.ktor.server.routing.*
import io.ktor.server.application.*

fun Application.configureRouting() {
    val roomController by inject<RoomsController>()
    install(Routing){
        chatSocket(roomController)
        getAllMessages(roomController)
    }

    /*routing {
        get("/") {
            call.respondText("Hello World!")
        }
    }*/
}
