package com.example.musicapp.ui.mainScreen

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.musicapp.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ErrorScreen(
    pullRefreshState: PullRefreshState,
    refreshing: Boolean,
    errorMessage: String,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 60.dp)
            .pullRefresh(pullRefreshState)
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            ErrorImage(modifier = Modifier.fillMaxSize())
            Text(
                text = stringResource(id = R.string.error_message),
                style = MaterialTheme.typography.h1,
                color = Color.Red
            )
            Text(
                modifier = Modifier.padding(top = 10.dp, start = 20.dp, end = 20.dp),
                text = errorMessage,
                textAlign = TextAlign.Center,
                lineHeight = 25.sp,
                style = MaterialTheme.typography.subtitle1,
            )

        }

        PullRefreshIndicator(
            refreshing = refreshing,
            state = pullRefreshState,
            modifier = Modifier
                .align(Alignment.TopCenter)
        )
    }
}


@Composable
fun ErrorImage(modifier: Modifier = Modifier) {

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Circle(0.05f, 250.dp)
        Circle(0.2f, 200.dp)
        Circle(0.5f, 150.dp)
        Circle(1f, 100.dp)

        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.error_icon),
            contentDescription = "Error",
            tint = Color.Red,
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .border(10.dp, Color.Red, CircleShape)
                .background(Color.White)

        )
    }

}

@Composable
fun Circle(alpha: Float, size: Dp) {
    Canvas(modifier = Modifier.size(size), onDraw = {
        drawCircle(alpha = alpha, color = Color.Red)
    })
}