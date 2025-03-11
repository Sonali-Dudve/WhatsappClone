package com.spdfs.whatsappclone

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.common.util.concurrent.ListenableFuture
import com.spdfs.whatsappclone.components.UserImage


class ChatScreen : ComponentActivity() {
    public var cameraClose = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userName = intent.getStringExtra("user_name")
        val image = intent.getStringExtra("user_image")



        setContent {

            MaterialTheme {
                Surface {

                    val imageCapture = remember {
                        ImageCapture.Builder().build()
                    }
                    // Call the CounterScreen composable
                    val userImage = image ?: ""
                    if (userName != null) {
                        WhatsAppChatScreen(context = this, username = userName, userImage = userImage ,cameraClose )
                    }


                }
            }

        }
    }



}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WhatsAppChatScreen(
    context: Context,
    username: String,
    userImage: String,
    cameraClose: Boolean
) {

    val viewModel: ChatViewModel = viewModel()

    val messages  by remember { mutableStateOf(viewModel.messages) }
    var inputText by remember { mutableStateOf(TextFieldValue("")) }
    val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
    val cameraProvider = cameraProviderFuture.get()

    if (viewModel.isCameraClosed.collectAsState().value.equals(true)){
         cameraPreview(cameraClose,viewModel,cameraProvider,cameraProviderFuture)
    }else{
        Column(
            modifier = Modifier
                .fillMaxSize()

        ) {
            cameraProvider.unbindAll()
            TopAppBar(modifier = Modifier
                .background(
                    Color(0xFFDCF8C6),
                    shape = MaterialTheme.shapes.medium
                )
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(0.dp),
                    clip = false
                ), title = {
                GlideImage(userName = username, userImage = userImage)
            })
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .padding(top = 8.dp),
                verticalArrangement = Arrangement.spacedBy(space = 8.dp),
                horizontalAlignment = Alignment.End
            ) {

                items(messages.value.size) { index ->
                    val message = messages.value[index]
                    MessageCard(message = message)
                }


            }
            Spacer(modifier = Modifier.height(8.dp))
            // Message input and send button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)

            ) {

                var visible by remember { mutableStateOf(false) }
                LaunchedEffect(inputText) {
                    visible = inputText.text.isNotEmpty()
                }
                Image(
                    modifier = Modifier
                        .size(50.dp)
                        .padding(top = 10.dp)
                        .padding(bottom = 10.dp),
                    painter = painterResource(id = R.drawable.baseline_add_24),
                    contentDescription = "",

                    )
                BasicTextField(
                    value = inputText,
                    onValueChange = { inputText = it
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Send
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .background(Color.Gray.copy(alpha = 0.1f), shape = CircleShape)
                        .padding(16.dp),
                    textStyle = TextStyle(fontWeight = FontWeight.Normal)
                )
                if(!visible){
                    Row(modifier = Modifier.padding(top = 10.dp)){
                        Image(
                            modifier = Modifier
                                .size(30.dp),
                            painter = painterResource(id = R.drawable.baseline_insert_drive_file_24),
                            contentDescription = ""
                        )


                        Image(
                            modifier = Modifier
                                .size(30.dp)
                                .clickable {
                                    //open camera
//                                val intent = Intent(context, CameraActivity::class.java)
//                                context.startActivity(intent)
//                                println("icon clicked")
//                                openCamera()
                                    viewModel.setCameraClosed(true)


                                },
                            painter = painterResource(id = R.drawable.baseline_photo_camera_24),
                            contentDescription = "",

                            )

                        Image(
                            modifier = Modifier
                                .size(30.dp),
                            painter = painterResource(id = R.drawable.baseline_mic_none_24),
                            contentDescription = "",

                            )
                    }

                }

                if(visible) {
                    Image(
                        modifier = Modifier
                            .size(50.dp)
                            .padding(top = 10.dp)
                            .padding(bottom = 10.dp)
                            .clickable {
                                if (inputText.text.isNotEmpty()) {
                                    viewModel.sendUserMessage(inputText.text, username)


                                    inputText = TextFieldValue("")
                                }
                            },
                        painter = painterResource(id = R.drawable.send),
                        contentDescription = "",
                    )
                }

            }
        }
    }

}








@Composable
fun GlideImage(userName: String, userImage: String) {
    Row {


        UserImage(userImage)

        Text(
            modifier = Modifier.padding(12.dp),
            text = userName,
            color = MaterialTheme.colorScheme.tertiary,
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.width(120.dp))

        Image(
            modifier = Modifier
                .size(50.dp)
                .padding(2.dp)
                .padding(end = 4.dp),
            painter = painterResource(id = R.drawable.baseline_videocam_24),
            contentDescription = "",

            )
        Spacer(modifier = Modifier.width(4.dp))
        Image(
            modifier = Modifier
                .size(50.dp)
                .padding(end = 4.dp),
            painter = painterResource(id = R.drawable.baseline_call_24),
            contentDescription = ""
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

            if (message.message.startsWith("content://")) {  // Check if the message is a valid content URI
                val uri = Uri.parse(message.message)
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(uri.toString())
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(200.dp)
                        .padding(0.dp)


                )
//                // Ensure you have a valid context and the URI is accessible
//                val painter = // Additional options for debugging
//                    rememberAsyncImagePainter( // Optional: Adds a smooth transition effect when the image loads
//                        ImageRequest.Builder(LocalContext.current).data(data = uri).apply(block = fun ImageRequest.Builder.() {
//                            // Additional options for debugging
//                            crossfade(true) // Optional: Adds a smooth transition effect when the image loads
//                        }).build()
//                    )
//
//                Image(
//                    painter = painter,
//                    contentDescription = "Captured Image",
//                    modifier = Modifier.fillMaxWidth(),
//                    contentScale = ContentScale.Crop // Adjust the content scale as necessary
//                )
            } else {
                Text(text = message.message) // For non-image content, display the text
            }

        }


    }
}
@Composable
fun cameraPreview(
    cameraClose: Boolean,
    viewModel: ChatViewModel,
    cameraProvider: ProcessCameraProvider,
    cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
) {

    val context = LocalContext.current
    val previewView = PreviewView(context)


   // var cameraOff by remember { mutableStateOf(true) }
    val imageCapture = remember {
        ImageCapture.Builder().build()
    }
    val preview = Preview.Builder()
        .build()

    val cameraSelector = CameraSelector.Builder()
        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
        .build()

    preview.setSurfaceProvider(previewView.surfaceProvider)

    cameraProvider.bindToLifecycle(
        LocalLifecycleOwner.current,
        cameraSelector,
        preview ,imageCapture
    )



    Box(contentAlignment = Alignment.BottomCenter, modifier = Modifier.fillMaxSize()) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->


                cameraProviderFuture.addListener({


                }, ContextCompat.getMainExecutor(context))

                previewView
            }
        )
        Button(onClick = {

            captureImage(viewModel,imageCapture, context,cameraClose)
           // cameraOff = false

        }
        ) {
            Text(text = "Capture Image")
        }
    }

}
private fun captureImage(
    viewModel: ChatViewModel,
    imageCapture: ImageCapture,
    context: Context,
    cameraClose: Boolean
) {
  //  cameraClose = false
    val name = "CameraxImage_${System.currentTimeMillis()}.jpeg"
    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, name)
        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
            put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image")
        }
    }
    val outputOptions = ImageCapture.OutputFileOptions
        .Builder(
            context.contentResolver,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        )
        .build()
    imageCapture.takePicture(
        outputOptions,
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                println("Successs")
                println(outputFileResults.savedUri)

                viewModel.sendUserMessage(outputFileResults.savedUri.toString(), "")
                viewModel.setCameraClosed(false)

            }

            override fun onError(exception: ImageCaptureException) {
                println("Failed $exception")
            }

        })

}

@Composable
fun CircleShapeAvatar(alignment: Alignment.Horizontal) {
    Box(
        modifier = Modifier
            .width(200.dp)
            .background(Color.Gray, shape = CircleShape)


    )
}


