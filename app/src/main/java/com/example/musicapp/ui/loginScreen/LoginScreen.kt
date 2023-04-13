package com.example.musicapp.ui.loginScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.musicapp.R

/*@Composable
fun LoginScreen(popUpScreen: () -> Unit, viewModel: LoginViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState

    BasicToolbar(AppText.login_details)

    Column([...]) {
        EmailField(uiState.email, viewModel::onEmailChange, Modifier.fieldModifier())

        [...]
    }
}
*/
@Composable
fun EmailField(value: String,  onNewValue: (String) -> Unit, modifier: Modifier = Modifier) {
    OutlinedTextField(
        singleLine = true,
        modifier = modifier,
        value = value,
        onValueChange = { onNewValue(it) },
        placeholder = { Text(stringResource(R.string.email)) },
        //leadingIcon = { [...] }
    )
}

@Composable
fun LoginScreen(
    onClick: () -> Unit,
    onSignUpClick: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.clickable { onClick() },
            text = "LOGIN",
            style = MaterialTheme.typography.h2
        )
        Text(
            modifier = Modifier.clickable { onSignUpClick() },
            text = "Sign Up",
            fontSize = MaterialTheme.typography.body1.fontSize,
            fontWeight = FontWeight.Medium
        )
    }
}