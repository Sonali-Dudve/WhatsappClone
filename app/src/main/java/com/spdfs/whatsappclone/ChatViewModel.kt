package com.spdfs.whatsappclone

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class ChatViewModel : ViewModel() {

    private val questions = mutableListOf(
        "What about yesterday?",
        "Can you tell me what inside your head?",
        "Lately, I've been wondering if I can really do anything, do you?",
        "You know fear is often just an illusion, have you ever experienced it?",
        "If you were me, what would you do?"
    )

    val conversation: MutableStateFlow<List<ChatUiModel.Message>>
        get() = _conversation

    private val _conversation = MutableStateFlow(
        listOf(ChatUiModel.Message.initConv)
    )

    // just show you how I initialize the conversation ;)


    fun sendChat(msg: String) {

        // wrap the msg as ChatUiModel.Message and assign the author as 'me'
        val myChat = ChatUiModel.Message(msg, ChatUiModel.Author.me)
        viewModelScope.launch {

            // add myChat to the conversation
            _conversation.emit(_conversation.value + myChat)

            // add 1s delay to make it seem more realistic
            delay(1000)

            // lastly, add a random question to conversation
            _conversation.emit(_conversation.value + getRandomQuestion())
        }
    }

    private fun getRandomQuestion(): ChatUiModel.Message {

        // throw a random question, and also define a default message to display when run out of questions.
        val question = if (questions.isEmpty()) {
            "no further questions, please leave me alone"
        } else {
            questions.random()
        }

        // remove the question when it is not empty
        if (questions.isNotEmpty()) questions.remove(question)

        // wrap the q as ChatUiModel.Message and assign the author as 'bot'
        return ChatUiModel.Message(
            text = question,
            author = ChatUiModel.Author.bot
        )
    }
}