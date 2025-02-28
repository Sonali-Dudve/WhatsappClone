package com.spdfs.whatsappclone

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


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
    WhatsAppChatScreen()
}