package com.spdfs.whatsappclone

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel


class ChatScreen : ComponentActivity() {

            override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            MaterialTheme {
                Surface {

                    // Call the CounterScreen composable
                    WhatsAppChatScreen()
                }
            }

        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WhatsAppChatScreen( ) {
    val viewModel : ChatViewModel = viewModel()
    val messages by viewModel.messages
    var inputText by remember { mutableStateOf(TextFieldValue("")) }


    /*    var message by remember { mutableStateOf(TextFieldValue("")) }

        var messages by remember { mutableStateOf(
            listOf(
                Message("Hey, how are you?", isSentByUser = false),
                Message("I'm good! How about you?", isSentByUser = true),
                Message("I'm doing great!", isSentByUser = false)
            )
        ) }*/

    Column(
        modifier = Modifier
            .fillMaxSize()

    ) {
        TopAppBar( modifier = Modifier.background(Color(0xFFDCF8C6), shape = MaterialTheme.shapes.medium),title = {

            GlideImage()

        })
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .padding(top = 8.dp),
            verticalArrangement = Arrangement.spacedBy(space = 8.dp),
            horizontalAlignment = Alignment.End
        ) {

            items(messages.size) { index ->
                val message = messages[index]
                MessageCard(message = message)
            }


//            items(messages.value) { message ->
//
//
//                if (message.isSentByUser) {
//
//                    MessageCard(message = message)
//                } else {
//                    MessageCard(message = message)
//                }
//            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Message input and send button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)

        ) {
            BasicTextField(
                value = inputText,
                onValueChange = { inputText = it },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Send
                ),
                modifier = Modifier
                    .weight(1f)
                    .background(Color.Gray.copy(alpha = 0.1f), shape = CircleShape)
                    .padding(16.dp),
                textStyle = TextStyle(fontWeight = FontWeight.Normal)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    if (inputText.text.isNotEmpty()) {
                        viewModel.sendUserMessage(inputText.text)


//                        // Add the user message to the list
//                        viewModel.addMessage(inputText.text, true)
//                        // Simulate bot response


                        inputText = TextFieldValue("")
                    }
                },
                modifier = Modifier.padding(start = 8.dp),
                shape = CircleShape
            ) {
                Text(text = "Send", color = Color.White)
            }
        }
    }
}

@Composable
fun GlideImage() {
Row {
    Image(
        modifier = Modifier
            .size(32.dp)
            .clip(CircleShape),
        painter = painterResource(id = R.drawable.dp),
        contentDescription = "CBDF"
    )

    Text(
        modifier = Modifier.padding(start = 12.dp),
        text = "Priya",
        color = MaterialTheme.colorScheme.tertiary,
        style = MaterialTheme.typography.bodyLarge
    )
}

}

@Composable
fun MessageCard(message: Message) {
    val alignment = if (message.isSentByUser) Alignment.End else Alignment.Start
    val backgroundColor = if (!message.isSentByUser) Color(0xFFDCF8C6) else Color.LightGray

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)

    ) {
        if (message.isSentByUser) {
            CircleShapeAvatar(alignment = Alignment.End)
        }

        Box(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .background(backgroundColor, shape = MaterialTheme.shapes.medium)
                .padding(12.dp)
        ) {
            Text(text = message.message)
        }


    }
}



@Composable
fun CircleShapeAvatar(alignment: Alignment.Horizontal) {
    Box(
        modifier = Modifier
            .width(200.dp)
            .background(Color.Gray, shape = CircleShape)


    )
}


