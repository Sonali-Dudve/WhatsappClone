package com.spdfs.whatsappclone

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class ChatViewModel : ViewModel() {

    // List of messages
    private val _messages = mutableStateListOf<Message>()
    val messages: State<List<Message>> = mutableStateOf(_messages)


    private val _isCameraClosed = MutableStateFlow(false)
    val isCameraClosed: StateFlow<Boolean> = _isCameraClosed

    fun setCameraClosed(value: Boolean) {
        _isCameraClosed.value = value
    }

    init {
        // Adding initial fake messages
        addMessage("Hi , How are You?", true) // User message
        addMessage("I am good neha , what about you", false) // Bot reply
    }

    fun addMessage(content: String, isUser: Boolean) {
        _messages.add(Message(content, isUser))
    }

    fun sendUserMessage(message: String, userName : String) {
        addMessage(message, true)
        // Simulate bot response after a delay
        android.os.Handler().postDelayed({

            if(userName == "User 1"){
                if(message.contains("I am Fine")){
                    addMessage("how work is going", false)
                }else if(message == "Its quite nice , what about you"){
                    addMessage("Too much work here !!!!", false)
                }else {
                    addMessage("sry i dont know !!!!", false)
                }
            }else if(userName == "Shreya"){
                if(message.contains("I am Awesome")){
                    addMessage("how work is going", false)
                }else if(message == "Its quite nice , what about you"){
                    addMessage("Too much work here !!!!", false)
                }else {
                    addMessage("sry i dont know !!!!", false)
                }
            }else if(userName == "Ricky"){
                if(message.contains("I am True")){
                    addMessage("how work is going", false)
                }else if(message == "Its quite nice , what about you"){
                    addMessage("Too much work here !!!!", false)
                }else {
                    addMessage("sry i dont know !!!!", false)
                }
            }else {
                addMessage("sry i dont know !!!!", false)
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