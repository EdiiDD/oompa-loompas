package com.edy.oompaloompas.presentation.ui.components.row

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.edy.oompaloompas.R
import com.edy.oompaloompas.presentation.detailoompaloompa.ProfessionOompaLoompa
import com.edy.oompaloompas.presentation.home.OompaLoompaState

@Composable
fun ItemFavoriteOompaLoompa(
    oompaLoompa: OompaLoompaState,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(12.dp)
            )
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(12.dp)
            )
            .size(
                width = 125.dp,
                height = 145.dp
            )
    ) {
        Image(
            painter = rememberAsyncImagePainter(oompaLoompa.image),
            contentDescription = stringResource(R.string.content_description_image_oompa_loompa),
            modifier = Modifier
                .fillMaxSize()
                .clip(shape = RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(12.dp))
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .graphicsLayer { alpha = 0.99F }
                .drawWithContent {
                    val colors = listOf(Color.Transparent, Color.White)
                    drawContent()
                    drawRect(
                        brush = Brush.verticalGradient(colors),
                        blendMode = BlendMode.DstOver,
                    )
                }
                .padding(
                    horizontal = 12.dp,
                    vertical = 4.dp
                )

        ) {
            Text(
                text = oompaLoompa.lastName,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = oompaLoompa.firstName,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ItemFavoriteOompaLoompaPreview() {
    ItemFavoriteOompaLoompa(
        oompaLoompa = OompaLoompaState(
            firstName = "Carlos",
            lastName = "Pepote",
            profession = ProfessionOompaLoompa.Metalworker,
            image = "https://s3.eu-central-1.amazonaws.com/napptilus/level-test/2.jpg",
            isFavorite = true,
            id = 1
        )
    )
}
