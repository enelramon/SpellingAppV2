package com.sagrd.spellingappv2.ui.palabra

import android.widget.Toast
import androidx.compose.foundation.layout.*

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.sagrd.spellingappv2.ui.componentes.ValidationText

import com.sagrd.spellingappv2.util.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordRegister(
    navHostController: NavHostController,
    viewModel: WordViewModel = hiltViewModel()
) {

    var wordError by remember {
        mutableStateOf(false)
    }
    var descriptionError by remember {
        mutableStateOf(false)
    }
    var validar = LocalContext.current
    val focusRequesterWord = FocusRequester()
    val focusRequesterDescription = FocusRequester()


    var error by remember {
        mutableStateOf(false)

    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Words Register",
                        fontFamily = FontFamily.Cursive,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 35.sp,
                        color = Color.White
                    )

                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navHostController.navigate(Screen.WordQuery.route)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "CONSULTA",
                            tint = Color.White
                        )
                    }
                }
            )
        },

        ) {
        Column(
            Modifier
                .padding(it)
                .padding(16.dp)
        ) {

            OutlinedTextField(
                value = viewModel.word,
                label = {
                    Text(
                        text = "Word",
                        fontStyle = FontStyle.Italic,
                        fontFamily = FontFamily.Cursive,
                    )
                },
                onValueChange = {
                    viewModel.word = it
                },
                isError = wordError,
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequesterWord),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null
                    )
                }
            )
            ValidationText(estado = wordError)

            OutlinedTextField(
                value = viewModel.description,
                label = {
                    Text(
                        text = "Description",
                        fontStyle = FontStyle.Italic,
                        fontFamily = FontFamily.Cursive,
                    )
                },
                onValueChange = {
                    viewModel.description = it
                },
                isError = descriptionError,
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequesterDescription),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null
                    )
                }
            )
            ValidationText(estado = descriptionError)


            OutlinedTextField(
                value = viewModel.imageUrl,
                label = {
                    Text(
                        text = "Image Url",
                        fontStyle = FontStyle.Italic,
                        fontFamily = FontFamily.Cursive,
                    )
                },
                onValueChange = {
                    viewModel.imageUrl = it
                },
                modifier = Modifier
                    .fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null
                    )
                },
            )

            Spacer(
                modifier = Modifier.width(20.dp)
            )
            Button(
                onClick = {
                    if (viewModel.word.isNullOrEmpty()) {
                        error = viewModel.word.isBlank()
                        Toast.makeText(
                            validar,
                            "The word field is empty!",
                            Toast.LENGTH_LONG
                        ).show()
                        focusRequesterWord.requestFocus()

                    } else if (viewModel.description.isNullOrEmpty()) {
                        error = viewModel.description.isBlank()
                        Toast.makeText(
                            validar,
                            "The description field is empty!",
                            Toast.LENGTH_LONG
                        ).show()
                        focusRequesterDescription.requestFocus()

                    } else {

                        viewModel.Guardar()
                        navHostController.navigate("NavegarConsulta")
                        Toast.makeText(validar, "Has been saved successfully!", Toast.LENGTH_LONG)
                            .show()
                        viewModel.word = ""
                        viewModel.description = ""
                        viewModel.imageUrl = ""
                    }
                },
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(),

                ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                )
                Text(
                    text = " Save",
                    fontSize = 20.sp,
                    fontFamily = FontFamily.Cursive,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }
}