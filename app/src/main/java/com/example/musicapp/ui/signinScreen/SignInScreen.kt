package com.example.musicapp.ui.signinScreen


import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.musicapp.R
import com.example.musicapp.data.auth.Constant.ServerClient
import com.example.musicapp.data.auth.Resource
import com.example.musicapp.ui.navigation.graphs.AuthScreen
import com.example.musicapp.ui.navigation.graphs.Graph
import com.example.musicapp.viewmodel.AuthViewModel

import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.launch

@Composable
fun SignInScreen(
    viewModel: AuthViewModel,
    navController: NavController,
) {

    val googleSignInState = viewModel.googleState.collectAsState(initial = null)

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
            val account = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val result = account.getResult(ApiException::class.java)
                val credentials = GoogleAuthProvider.getCredential(result.idToken, null)
                viewModel.googleSignIn(credentials)
            } catch (ex: ApiException) {
                Log.e("Api exception", ex.message.toString())
            }
        }

    var email by rememberSaveable {
        mutableStateOf("")
    }
    var password by rememberSaveable {
        mutableStateOf("")
    }
    var passwordVisible by remember {
        mutableStateOf(false)
    }

    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val state = viewModel.loginState.collectAsState(initial = null)

    val focusManager = LocalFocusManager.current

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
                text = "Sign In",
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
                    if (state.value is Resource.Error) Text(text = stringResource(R.string.wrong_email))
                    else Text(text = stringResource(R.string.email))
                },
                isError = state.value is Resource.Error,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                )
            )
            OutlinedTextField(
                value = password,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { password = it },
                label = {
                    if (state.value is Resource.Error) Text(text = stringResource(R.string.wrong_password))
                    else Text(text = stringResource(R.string.password))
                },
                isError = state.value is Resource.Error,
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
                        viewModel.loginUser(email, password)
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
                    text = "Sign In",
                    color = Color.White,
                    style = MaterialTheme.typography.h2.copy(
                        fontWeight = FontWeight.Normal,
                        fontSize = 17.sp
                    ),
                    modifier = Modifier.padding(7.dp)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Divider(color = MaterialTheme.colors.primary, modifier = Modifier.weight(1f))
                Text(
                    text = "or",
                    style = MaterialTheme.typography.h1.copy(fontSize = 16.sp),
                    modifier = Modifier.padding(horizontal = 5.dp)
                )
                Divider(color = MaterialTheme.colors.primary, modifier = Modifier.weight(1f))
            }



            OutlinedButton(
                onClick = {
                    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .requestIdToken(ServerClient)
                        .build()

                    val googleSignInClient = GoogleSignIn.getClient(context, gso)

                    launcher.launch(googleSignInClient.signInIntent)
                },
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(1.dp, Color.LightGray),
                elevation = ButtonDefaults.elevation(2.dp)

            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Continue with Google",
                        color = Color.Black,
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 16.dp)
                            .wrapContentWidth(Alignment.CenterHorizontally)
                    )

                    Icon(
                        painter = painterResource(id = R.drawable.icon_google),
                        contentDescription = "Google Icon",
                        modifier = Modifier.size(40.dp),
                        tint = Color.Unspecified
                    )
                }
            }

        }
        Text(
            text = "New user? Sign up",
            color = Color.Gray,
            style = MaterialTheme.typography.h1.copy(fontSize = 17.sp),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 40.dp)
                .clickable {
                    navController.navigate(AuthScreen.SignUp.route)
                }
        )

        /*if (state.value?. == true || googleSignInState?.loading == true) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 40.dp),
                color = MaterialTheme.colors.primary
            )
        }*/
        state.value?.let {
            when (it) {
                is Resource.Success -> {
                    LaunchedEffect(key1 = it.data) {
                        scope.launch {
                            navController.popBackStack()
                            navController.navigate(Graph.HOME)
                            Toast.makeText(context, "Success", Toast.LENGTH_LONG).show()

                        }
                    }
                }
                is Resource.Error -> {
                    LaunchedEffect(key1 = it.message) {
                        scope.launch {
                            val error = it.message
                            Toast.makeText(context, "$error", Toast.LENGTH_LONG).show()
                        }
                    }
                }
                is Resource.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(top = 40.dp),
                        color = MaterialTheme.colors.primary
                    )
                }
            }
        }
        googleSignInState.value?.let {
            when (it) {
                is Resource.Success -> {
                    LaunchedEffect(key1 = it.data) {
                        scope.launch {
                            navController.popBackStack()
                            navController.navigate(Graph.HOME)
                            Toast.makeText(context, "Sign in success", Toast.LENGTH_LONG).show()

                        }
                    }
                }
                is Resource.Error -> {
                    LaunchedEffect(key1 = it.message) {
                        scope.launch {
                            val error = it.message
                            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
                        }
                    }
                }
                is Resource.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(top = 40.dp),
                        color = MaterialTheme.colors.primary
                    )
                }
            }
        }
    }
}