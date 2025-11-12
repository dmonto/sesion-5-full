package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.Post
import com.example.myapplication.ApiClient
import com.example.myapplication.ui.theme.MyApplicationTheme
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        var apiResult by remember { mutableStateOf<String?>(null) }
                        val scope = rememberCoroutineScope()
                        
                        Greeting("Curso Tabnine")
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = {
                            Log.d("ButtonClick", "Botón presionado!")
                            scope.launch {
                                try {
                                    val post = ApiClient.client.get("https://jsonplaceholder.typicode.com/posts/1").body<Post>()
                                    apiResult = "Título: ${post.title}"
                                    Log.d("APIResponse", post.toString())
                                } catch (e: Exception) {
                                    apiResult = "Error: ${e.message}"
                                    Log.e("APIError", "Error al llamar a la API", e)
                                }
                            }
                        }) {
                            Text("Llamar a API REST")
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        apiResult?.let { 
                            Text(it) 
                        }
                    }
                    // Lanzar coroutine
                    LaunchedEffect(Unit) {
                        Log.d("KotlinTabnine", "Hola, Tabnine en Kotlin!")
                        delay(1000L)
                        Log.d("KotlinTabnine", "Coroutine completa después de 1 segundo")
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hola $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Greeting("Android")
    }
}