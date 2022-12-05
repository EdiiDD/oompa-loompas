package com.edy.oompaloompas.presentation.ui.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.edy.oompaloompas.R
import com.edy.oompaloompas.core.error.ErrorEntity

@Composable
fun BaseErrorScreen(
    error: ErrorEntity,
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.inversePrimary),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        val string = when (error) {
            ErrorEntity.ApiError.BadRequest,
            ErrorEntity.ApiError.ParseError,
            ErrorEntity.ApiError.ServerError,
            ErrorEntity.ApiError.ServiceUnavailable,
            ErrorEntity.ApiError.NotFound,
            -> stringResource(R.string.error_api_notified)
            ErrorEntity.ApiError.ConnectivityError -> stringResource(R.string.error_api_connectivity_error)
            ErrorEntity.ApiError.TimeOutError -> stringResource(R.string.error_api_try_again)
            is ErrorEntity.ApiError.UnauthorizedError -> stringResource(R.string.error_api_unauthorized_error)


            ErrorEntity.FileError.NotFound -> stringResource(R.string.error_file_not_found)
            ErrorEntity.FileError.ReadError -> stringResource(R.string.error_file_read_error)
            ErrorEntity.LocalDataBaseError.MissingLocalStorageError -> stringResource(R.string.error_local_database_missing)
            ErrorEntity.Unknown -> stringResource(R.string.error_api_notified)
        }

        Text(
            text = string,
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp, horizontal = 12.dp)
        )
    }
}

@Composable
fun BaseLoadingScreen() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading))
    val progress by animateLottieCompositionAsState(composition)
    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = MaterialTheme.colorScheme.inversePrimary),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        LottieAnimation(composition = composition, progress = progress)
    }
}

@Preview
@Composable
fun BaseErrorScreenPreview() {
    BaseErrorScreen(error = ErrorEntity.ApiError.ConnectivityError)
}

@Preview
@Composable
fun BaseLoadingScreenPreview() {
    BaseLoadingScreen()
}