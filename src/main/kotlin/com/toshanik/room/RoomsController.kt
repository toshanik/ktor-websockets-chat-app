package com.toshanik.room

import com.toshanik.data.model.Message
import com.toshanik.data.model.MessageDataSource
import io.ktor.websocket.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.concurrent.ConcurrentHashMap

class RoomsController(
    private val messageDataSource: MessageDataSource
){
    private val members = ConcurrentHashMap<String,Member>()

    fun onJoin(
        username: String,
        sessionId: String,
        socket: WebSocketSession
    ){
        if(members.containsKey(username)){
            throw MemberAlreadyExistsException()
        }
        members[username] = Member(
            username = username,
            sessionId = sessionId,
            socket = socket
        )
    }

    suspend fun sendMessage(senderUsername: String, message: String){
        members.forEach { member ->
            val messageEntity = Message(
                text = message,
                username = senderUsername,
                timestamp = System.currentTimeMillis()
            )
            messageDataSource.insertMessage(messageEntity)

            val parsedMessage = Json.encodeToString(messageEntity)
            member.value.socket.send(Frame.Text(parsedMessage))
        }
    }

    suspend fun getAllMessages(): List<Message> {
        return messageDataSource.getAllMessages()
    }

    suspend fun tryDisconnect(username: String){
        members[username]?.socket?.close()
        if(members.containsKey(username)){
            members.remove(username)
        }
    }
}