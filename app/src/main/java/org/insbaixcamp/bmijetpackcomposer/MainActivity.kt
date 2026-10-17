package org.insbaixcamp.bmijetpackcomposer

// IMPORTS: Llibreries necessàries per a Android, la interfície amb Jetpack Compose, icones i disseny
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.insbaixcamp.bmijetpackcomposer.ui.theme.BMIJetpackComposerTheme

// Activitat principal d'Android (punt d'entrada de l'aplicació)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // setContent: Defineix el contingut visual mitjançant Jetpack Compose (sense fitxers XML)
        setContent {
            // Aplica el tema visual per defecte de la teva app
            BMIJetpackComposerTheme {
                // Crida a la nostra pantalla principal
                BMIScreen()
            }
        }
    }
}

// PALETA DE COLORS ESTIL SYNTHWAVE / RETRO (Codis Hexadecimals ARGB)
val SynthwaveDarkBg = Color(0xFF120224)   // Violeta molts fosc per al fons general
val SynthwavePurpCard = Color(0xFF220A38) // Lila fosc per al fons del contenidor central
val NeonPink = Color(0xFFFF007F)          // Rosa neó brillant
val NeonCyan = Color(0xFF00F0FF)          // Blau cian neó
val NeonYellow = Color(0xFFFFE600)        // Groc neó per als valors i el títol
val NeonPurple = Color(0xFF9D00FF)        // Purpra neó intermedi

// @Composable indica que aquesta funció dibuixa una part de la interfície d'usuari
@Composable
fun BMIScreen() {
    // ESTATS (@runtime remember / mutableStateOf): Variables que, quan canvia el seu valor,
    // fan que la interfície es redibuixi automàticament actualitzant les dades en pantalla.
    var name by remember { mutableStateOf("") }              // Text del nom de l'usuari
    var weight by remember { mutableStateOf(80) }            // Pes en KG (inicialitzat a 80)
    var height by remember { mutableStateOf(180f) }          // Alçada en CM (inicialitzat a 180.0)
    var bmiResult by remember { mutableStateOf<Float?>(null) } // Valor de l'IMC calculat (null fins que es calcula)

    // GRADIENTS DE COLOR DISSENYATS PER AL FONS I ELS BOTONS
    // Gradient vertical estil retro de tres tons de fosc
    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF0B0117),
            Color(0xFF24083B),
            Color(0xFF120224)
        )
    )

    // Gradient horitzontal neó per al botó de càlcul (Rosa -> Púrpura -> Blau)
    val buttonGradient = Brush.horizontalGradient(
        colors = listOf(NeonPink, NeonPurple, NeonCyan)
    )

    // Surface: Contenidor base del sistema Compose que ocupa tota la pantalla
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        // Box: Permet superposar elements o centrar-los
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundGradient) // Aplica el gradient de fosc
                .padding(20.dp),                 // Marges de seguretat al voltant
            contentAlignment = Alignment.Center  // Centra la targeta al mig exactament
        ) {
            // Column: Apila els elements fills de dalt a baix (verticalment)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(CutCornerShape(topStart = 20.dp, bottomEnd = 20.dp)) // Forma amb dues vores tallades
                    .background(SynthwavePurpCard)                             // Color de la targeta
                    .border(
                        width = 2.dp,
                        brush = Brush.horizontalGradient(listOf(NeonPink, NeonCyan)), // Vora neó en gradient
                        shape = CutCornerShape(topStart = 20.dp, bottomEnd = 20.dp)
                    )
                    .padding(20.dp), // Espaiat intern entre el límit de la targeta i el contingut
                horizontalAlignment = Alignment.CenterHorizontally, // Centra tots els continguts horitzontalment
                verticalArrangement = Arrangement.spacedBy(16.dp)   // Espai vertical automàtic entre cada element
            ) {
                // --- TÍTOL D'ESTIL SYNTHWAVE ---
                Text(
                    text = "★ BMI CALCULATOR ★",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black,
                    fontFamily = FontFamily.Monospace, // Tipografia estil pantalla de terminal/retro
                    color = NeonYellow,
                    letterSpacing = 2.sp               // Espaiat entre lletres
                )

                // Línia horitzontal de separació estil neó
                HorizontalDivider(thickness = 2.dp, color = NeonPink)

                // --- CAMP DE TEXT PER INTRODUIR EL NOM ---
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it }, // Actualitza l'estat 'name' quan l'usuari escriu
                    label = { Text("> NOM DE L'USUARI", color = NeonCyan, fontSize = 11.sp) },
                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = NeonPink) },
                    singleLine = true, // Evita que es facin salts de línia
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFF18052E),
                        unfocusedContainerColor = Color(0xFF18052E),
                        focusedBorderColor = NeonCyan,
                        unfocusedBorderColor = NeonPink,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                // --- CONTENIDOR DE SELECCIÓ DE PES ---
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF1A0630))
                        .border(1.5.dp, NeonPink, RoundedCornerShape(12.dp))
                        .padding(14.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("[ PES ]", color = NeonCyan, fontSize = 12.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                        Text("$weight KG", color = NeonYellow, fontSize = 30.sp, fontWeight = FontWeight.ExtraBold, fontFamily = FontFamily.Monospace)

                        // Row: Posiciona els botons de sumar i restar un al costat de l'altre (horitzontalment)
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier.padding(top = 4.dp)
                        ) {
                            // Botó de decrementar pes
                            IconButton(
                                onClick = { if (weight > 1) weight-- }, // Resta 1 al pes (mínim 1)
                                modifier = Modifier
                                    .clip(CutCornerShape(6.dp))
                                    .background(NeonPink)
                            ) {
                                Icon(Icons.Default.Remove, contentDescription = "Restar", tint = Color.Black)
                            }

                            // Botó d'incrementar pes
                            IconButton(
                                onClick = { weight++ }, // Suma 1 al pes
                                modifier = Modifier
                                    .clip(CutCornerShape(6.dp))
                                    .background(NeonCyan)
                            ) {
                                Icon(Icons.Default.Add, contentDescription = "Sumar", tint = Color.Black)
                            }
                        }
                    }
                }

                // --- CONTENIDOR DE SELECCIÓ D'ALÇADA ---
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF1A0630))
                        .border(1.5.dp, NeonCyan, RoundedCornerShape(12.dp))
                        .padding(14.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("[ ALÇADA ]", color = NeonPink, fontSize = 12.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                        Text("${height.toInt()} CM", color = NeonYellow, fontSize = 30.sp, fontWeight = FontWeight.ExtraBold, fontFamily = FontFamily.Monospace)

                        // Slider: Barra lliscant per triar l'alçada entre 100 cm i 220 cm
                        Slider(
                            value = height,
                            onValueChange = { height = it }, // Actualitza l'estat 'height' en moure la barra
                            valueRange = 100f..220f,         // Rang de valors permessos
                            colors = SliderDefaults.colors(
                                thumbColor = NeonYellow,
                                activeTrackColor = NeonPink,
                                inactiveTrackColor = Color(0xFF3D135E)
                            )
                        )
                    }
                }

                // --- BOTÓ PRINCIPAL DE CÀLCUL ---
                Button(
                    onClick = {
                        // Fórmulal de l'IMC: Pes (kg) / (Alçada en metres)^2
                        val heightInMeters = height / 100f
                        bmiResult = weight / (heightInMeters * heightInMeters)
                    },
                    contentPadding = PaddingValues(),
                    shape = CutCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .background(buttonGradient, shape = CutCornerShape(10.dp)) // Aplica el gradient rosa-blau
                        .border(1.dp, NeonYellow, CutCornerShape(10.dp))
                ) {
                    Text(
                        text = "⚡ CALCULAR IMC ⚡",
                        color = Color.White,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Black,
                        fontFamily = FontFamily.Monospace,
                        letterSpacing = 1.2.sp
                    )
                }

                // --- MOSTRAR EL RESULTAT (Només s'executa quan 'bmiResult' conté un valor) ---
                bmiResult?.let { result ->
                    // Classificació del valor d'IMC segons el resultat
                    val statusText = when {
                        result < 18.5f -> "SOTSPES"
                        result < 25f -> "PES NORMAL"
                        result < 30f -> "SOBREPES"
                        else -> "OBESITAT"
                    }

                    // Caixeta d'alerta per mostrar el resultat
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(NeonPink.copy(alpha = 0.2f)) // Fons rosa semitransparent
                            .border(1.dp, NeonYellow, RoundedCornerShape(8.dp))
                            .padding(10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "IMC: ${String.format("%.1f", result)} ($statusText)", // Formateja el decimal a 1 xifra
                            color = NeonYellow,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}