package com.example.musicapp.ui.signupScreen

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.musicapp.R
import com.example.musicapp.data.auth.Constant
import com.example.musicapp.ui.navigation.graphs.AuthScreen
import com.example.musicapp.ui.navigation.graphs.Graph
import com.example.musicapp.viewmodel.SignUpViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel,
    navController: NavController,
) {

    var email by rememberSaveable {
        mutableStateOf("")
    }
    var password by rememberSaveable {
        mutableStateOf("")
    }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val state = viewModel.signUpState.collectAsState(initial = null)
    val focusManager = LocalFocusManager.current

    var passwordVisible by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Sign Up",
                style = MaterialTheme.typography.h1.copy(fontSize = 30.sp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
            )
            OutlinedTextField(
                value = email,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { email = it },
                label = {
                    if (state.value?.isError?.isNotEmpty() == true) {
                        Text(text = stringResource(R.string.wrong_email))
                    } else {
                        Text(text = stringResource(R.string.email))
                    }
                },
                isError = state.value?.isError?.isNotEmpty() == true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),
                shape = RoundedCornerShape(8.dp),
            )
            OutlinedTextField(
                value = password,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { password = it },
                label = {
                    if (state.value?.isError?.isNotEmpty() == true) {
                        Text(text = stringResource(R.string.wrong_password))
                    } else {
                        Text(text = stringResource(R.string.password))
                    }
                },
                isError = state.value?.isError?.isNotEmpty() == true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                ),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = Color.Black,
                    cursorColor = MaterialTheme.colors.primary,
                ),
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            painter = painterResource(id = if (passwordVisible) R.drawable.baseline_visibility_24 else R.drawable.baseline_visibility_off_24),
                            contentDescription = null
                        )
                    }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
            )

            Button(
                onClick = {
                    scope.launch {
                        viewModel.registerUser(email, password)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, start = 30.dp, end = 30.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Black,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(15.dp)
            ) {
                Text(
                    text = "Sign Up",
                    color = Color.White,
                    style = MaterialTheme.typography.h2.copy(
                        fontWeight = FontWeight.Normal,
                        fontSize = 17.sp
                    ),
                    modifier = Modifier.padding(7.dp)
                )
            }
        }
        Text(
            text = "Already have an account? Sign in",
            color = Color.Gray,
            style = MaterialTheme.typography.h1.copy(fontSize = 17.sp),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 40.dp)
                .clickable {
                    navController.navigate(AuthScreen.Login.route)
                }
        )

        if (state.value?.isLoading == true) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 40.dp),
                color = MaterialTheme.colors.primary
            )
        }
        state.value?.let { state ->
            when {
                state.isSuccess?.isNotEmpty() == true -> {
                    LaunchedEffect(key1 = state.isSuccess) {
                        scope.launch {
                            val success = state.isSuccess
                            navController.navigate(AuthScreen.Login.route)
                            Toast.makeText(context, "$success", Toast.LENGTH_LONG).show()
                        }
                    }
                }
                state.isError?.isNotBlank() == true -> {
                    LaunchedEffect(key1 = state.isError) {
                        scope.launch {
                            val error = state.isError
                            Toast.makeText(context, "$error", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }

    }


}