package org.insbaixcamp.bmijetpackcomposer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.insbaixcamp.bmijetpackcomposer.ui.theme.BMIJetpackComposerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BMIJetpackComposerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    BMIScreen()
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun BMIScreen(){
    var name by remember { mutableStateOf(value="") }
    var pes by remember { mutableStateOf(value=80) }
    var alçada by remember { mutableStateOf(value=180)}
    var bmi: Float by remember {mutableStateOf(value=0f)}


//Cambiar ordre resta i suma
    Column(){
        Text(text = "BMI Calculator")
        TextField(
            value = name,
            onValueChange = {name = it },
            label = { Text("Nom") }
        )
        Text(text = "Pes")
        Row(){
            Text(text = "$pes kg")
            Button(onClick = {
                pes-- }) {
                Text(text = "-")
            }
            Button(onClick = {pes++}) {
                Text(text = "+")
            }
        }

        Text(text = "Alçada: $alçada cm")

        Slider(

            value = alçada.toFloat(),
            onValueChange = { alçada = it.toInt() },
            valueRange = 100f..250f,


        )

        Button(
            onClick = {
                val alçadaMetres :Float = alçada.toFloat() / 100
                bmi = (pes / (alçadaMetres * alçadaMetres))
            }
        ){

        Text(text = "Calcular BMI")


        }

        if(bmi != 0f)

            Text(text = "El teu BMI es $bmi")

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
    BMIJetpackComposerTheme {
        BMIScreen()
    }
}