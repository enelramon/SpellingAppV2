package com.sagrd.spellingappv2.ui.practica

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sagrd.spellingappv2.ui.palabra.WordViewModel

import com.sagrd.spellingappv2.util.Screen

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PracticaScreen(
    navHostController: NavHostController,
    viewModel: WordViewModel = hiltViewModel(),
    palabraId: Int? = 0,
    onSpeech:(String) -> Unit
    //speech : SpeechViewModel = hiltViewModel()
) {
    var palabra = viewModel.GetPalabra(palabraId ?: 0)
    val context = LocalContext.current
    var show by rememberSaveable { mutableStateOf(false) }

    //var speech : SpeechViewModel
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { show = true }, 
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "",
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
            .padding(it)
            .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(palabra.imagenUrl)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier.size(300.dp, 300.dp)
                )
                Text(
                    text = palabra.descripcion,
                    modifier = Modifier.size(300.dp, 70.dp),
                )
            }


            Button(
                onClick =
                {
                    navHostController.navigate(Screen.PracticaScreen.route + "/${palabra.palabraId + 1}")
                },
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(horizontal = 20.dp, vertical = 0.dp),
            ) {
                Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "", tint = MaterialTheme.colorScheme.onPrimary)
            }


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = palabra.palabra,
                modifier = Modifier.height(30.dp),
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.height(0.dp))
                IconButton(
                    onClick = { onSpeech(palabra.palabra)
                    }
                ) {
                    //ICON DE SONIDO (PREFERIBLEMENTE, UNA VOCINA)
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Audio Icon"
                    )
                }


            }
        }


        /*val lis = viewModel.listado.collectAsState(initial = emptyList()).value

        var palabra = viewModel.GetPalabra(palabras = lis)

        var index : Int = 0*/

    }
    DialogModel(
        show = show,
        { show = false },
        { show = false; navHostController.navigate(Screen.ResumenScreen.route) }
    )
}

@Composable
fun DialogModel(
    show: Boolean,
    onDismiss: () -> Unit,
    onAccept: () -> Unit
) {
    if (show) {
        AlertDialog(
            onDismissRequest = { onDismiss() },

            confirmButton = {
                Button(
                    onClick = { onAccept() },
                    modifier = Modifier.safeContentPadding(),
                ) {
                    Text(text = "Yes")
                }
            },
            dismissButton = {
                Button(
                    onClick = { onDismiss() },
                    modifier = Modifier.safeContentPadding(),
                ) {
                    Text(text = "No")
                }
            },
            title = {

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "",
                        tint = Color.Red,
                        modifier = Modifier.size(50.dp)
                    )
                }


            },
            text = {
                Text(
                    text = "Are you sure you want to stop practicing?",
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp,
                )
            },
        )
    }
}

