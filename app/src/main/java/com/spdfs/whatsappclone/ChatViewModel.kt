package com.spdfs.whatsappclone

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel


class ChatViewModel : ViewModel() {

    // List of messages
    private val _messages = mutableStateListOf<Message>()
    val messages: State<List<Message>> = mutableStateOf(_messages)

    init {
        // Adding initial fake messages
        addMessage("Hi Priya , How are You?", true) // User message
        addMessage("I am good neha , what about you", false) // Bot reply
    }

    fun addMessage(content: String, isUser: Boolean) {
        _messages.add(Message(content, isUser))
    }

    fun sendUserMessage(message: String) {
        addMessage(message, true)
        // Simulate bot response after a delay
        android.os.Handler().postDelayed({
            if(message.contains("I am Fine")){
                addMessage("how work is going", false)
            }else if(message == "Its quite nice , what about you"){
            addMessage("Too much work here !!!!", false)
            }
            //addMessage("I'm a bot, responding to your message.", false)
        }, 1000)  // Delay of 1 second
    }
}

data class Message(
    var message: String, var isSentByUser : Boolean
)




@Preview
@Composable
fun PreviewChatScreen() {
    //WhatsAppChatScreen()
}