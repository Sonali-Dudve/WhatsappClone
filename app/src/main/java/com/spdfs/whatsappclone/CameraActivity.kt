//package com.spdfs.whatsappclone
//
//import android.app.Activity
//import android.content.ContentValues
//import android.content.Context
//import android.content.Intent
//import android.os.Build
//import android.os.Bundle
//import android.provider.MediaStore
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.camera.core.CameraSelector
//import androidx.camera.core.ImageCapture
//import androidx.camera.core.ImageCaptureException
//import androidx.camera.core.Preview
//import androidx.camera.lifecycle.ProcessCameraProvider
//import androidx.camera.view.PreviewView
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.material3.Button
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.platform.LocalLifecycleOwner
//import androidx.compose.ui.viewinterop.AndroidView
//import androidx.core.content.ContextCompat
//import androidx.lifecycle.viewmodel.compose.viewModel
//
//
//class CameraActivity : ComponentActivity() {
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//
//        setContent {
//
//         CameraPreview()
//        }
//    }
//
//}
//@Composable
//fun CameraPreview() {
//    val viewModel: ChatViewModel = viewModel()
//    val context = LocalContext.current
//    val previewView = PreviewView(context)
//    val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
//    val cameraProvider = cameraProviderFuture.get()
//
//    val imageCapture = remember {
//        ImageCapture.Builder().build()
//    }
//    val preview = Preview.Builder()
//        .build()
//
//    val cameraSelector = CameraSelector.Builder()
//        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
//        .build()
//
//    preview.setSurfaceProvider(previewView.surfaceProvider)
//
//    cameraProvider.bindToLifecycle(
//        LocalLifecycleOwner.current,
//        cameraSelector,
//        preview ,imageCapture
//    )
//
//
//
//    Box(contentAlignment = Alignment.BottomCenter, modifier = Modifier.fillMaxSize()) {
//        AndroidView(
//            modifier = Modifier.fillMaxSize(),
//            factory = { context ->
//
//
//                cameraProviderFuture.addListener({
//
//
//                }, ContextCompat.getMainExecutor(context))
//
//                previewView
//            }
//        )
//        Button(onClick = {
//            captureImage(viewModel,imageCapture, context)
//        }
//        ) {
//            Text(text = "Capture Image")
//        }
//    }
//}
//private fun captureImage(viewModel: ChatViewModel, imageCapture: ImageCapture, context: Context) {
//
//    val name = "CameraxImage.jpeg"
//    val contentValues = ContentValues().apply {
//        put(MediaStore.MediaColumns.DISPLAY_NAME, name)
//        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
//        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
//            put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image")
//        }
//    }
//    val outputOptions = ImageCapture.OutputFileOptions
//        .Builder(
//            context.contentResolver,
//            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//            contentValues
//        )
//        .build()
//    imageCapture.takePicture(
//        outputOptions,
//        ContextCompat.getMainExecutor(context),
//        object : ImageCapture.OnImageSavedCallback {
//            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
//                println("Successs")
//
//                viewModel.sendUserMessage(outputFileResults.savedUri.toString(), "")
//
//               (context as? Activity)?.finish()
//            }
//
//            override fun onError(exception: ImageCaptureException) {
//                println("Failed $exception")
//            }
//
//        })
//}
//
//
//
//
//
//
//
//
