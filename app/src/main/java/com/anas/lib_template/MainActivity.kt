package com.anas.lib_template

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.anas.lib_template.ui.theme.Shake_detectorTheme
import com.anasesh.shakedetector.ShakeDetector
import com.anasesh.shakedetector.ShakeListener
import com.anasesh.shakedetector.ShakeSensitivity

class MainActivity : ComponentActivity(), ShakeListener {
    private val shakeDetector by lazy{
        ShakeDetector(this,this,ShakeSensitivity.High)
    }

    override fun onStart() {
        super.onStart()
        shakeDetector.start()
    }

    override fun onStop() {
        super.onStop()
        shakeDetector.stop()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Shake_detectorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    override fun onShaked() {
       Log.i("SD" , "Shake Detected")
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Shake_detectorTheme {
        Greeting("Android")
    }
}