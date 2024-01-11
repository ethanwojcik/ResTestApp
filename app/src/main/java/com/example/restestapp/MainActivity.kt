package com.example.restestapp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.restestapp.ui.theme.ResTestAppTheme  // Replace with your actual theme if needed
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

class MainViewModel : ViewModel() {
    var accuracy = mutableStateOf(0f)

    fun loadModelAndMockTrain(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            // Mock the loading and training process
            accuracy.value = 0.90f // Hard-coded 90% accuracy for testing
            // can uncomment the below lines to perform actual model loading and training
            /*
            try {
                val tfliteModel: MappedByteBuffer = loadModelFile(context)
                val tflite = Interpreter(tfliteModel)
                val finalAccuracy = trainModel(tflite)
                accuracy.value = finalAccuracy
            } catch (e: Exception) {
                e.printStackTrace()
            }
            */
        }
    }

    private fun loadModelFile(context: Context): MappedByteBuffer {
        val fileDescriptor = context.assets.openFd("mnist_model.tflite")
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    private fun trainModel(tflite: Interpreter): Float {


//        // Define the inputs and outputs for the training operation
//        val inputs: Array<Any> = arrayOf(trainData.input)
//        val outputs: Map<Int, Any> = mapOf(0 to trainData.output)

        // Note: the tflite model should be prepared for retraining by setting the trainable flag for layers
//        // Run the training operation
//        tflite.runForMultipleInputsOutputs(inputs, outputs)
//
//        val accuracy = evaluateModel(tflite, trainData.validationData)
//        return accuracy
        return 0.99f // Placeholder accuracy
    }

//    private fun evaluateModel(tflite: Interpreter, validationData: ValidationData): Float {
//        // Pseudocode below, need to adapt to the
//
//        // Define the inputs for the evaluation operation
//        val inputs: Array<Any> = arrayOf(validationData.input)
//        val outputs: Map<Int, Any> = mapOf(0 to validationData.output)
//
//        // Run the evaluation operation
//        tflite.runForMultipleInputsOutputs(inputs, outputs)
//
//        // Calculate and return the accuracy based on the outputs
//        // ...
//        return calculatedAccuracy
//    }
}

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Start the mock training process to display the hard-coded accuracy
        viewModel.loadModelAndMockTrain(applicationContext)

        setContent {
            val accuracy = viewModel.accuracy.value
            ResTestAppTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    ShowAccuracy(accuracy)
                }
            }
        }
    }
}

@Composable
fun ShowAccuracy(accuracy: Float) {
    Text(text = "Accuracy: ${accuracy * 100}%")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ResTestAppTheme {
        ShowAccuracy(0.99f) // Assuming 99% accuracy for preview purposes
    }
}
