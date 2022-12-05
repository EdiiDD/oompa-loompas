package com.edy.oompaloompas.presentation.ui.components.row

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.edy.oompaloompas.R
import com.edy.oompaloompas.presentation.detailoompaloompa.ProfessionOompaLoompa
import com.edy.oompaloompas.presentation.home.OompaLoompaState

@Composable
fun ItemOompaLoompa(
    oompaLoompa: OompaLoompaState,
    onClickFavorite: () -> Unit,
    onClickUnfavorite: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .height(IntrinsicSize.Max)
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(12.dp)
            )
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(12.dp)) {

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically

        ) {
            Image(
                painter = rememberAsyncImagePainter(oompaLoompa.image),
                contentDescription = stringResource(R.string.content_description_image_oompa_loompa),
                modifier = Modifier
                    .size(55.dp)
                    .clip(
                        shape = RoundedCornerShape(8.dp)
                    ),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .padding(start = 12.dp),
                verticalArrangement = Arrangement.Center

            ) {
                Text(
                    text = oompaLoompa.lastName,
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = oompaLoompa.firstName,
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween

        ) {
            Row(
                modifier = Modifier
                    .fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically) {


                Icon(
                    painter = when (oompaLoompa.profession) {
                        ProfessionOompaLoompa.Brewer -> painterResource(R.drawable.ic_beer)
                        ProfessionOompaLoompa.Developer -> painterResource(R.drawable.ic_developer)
                        ProfessionOompaLoompa.Gemcutter -> painterResource(R.drawable.ic_diamond)
                        ProfessionOompaLoompa.Medic -> painterResource(R.drawable.ic_medic)
                        ProfessionOompaLoompa.Metalworker -> painterResource(R.drawable.ic_helmet)
                    },
                    contentDescription = stringResource(R.string.content_description_favorite),
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(start = 12.dp),
                    tint = MaterialTheme.colorScheme.primary)

                val profession = when (oompaLoompa.profession) {
                    ProfessionOompaLoompa.Brewer -> stringResource(R.string.profession_brewer)
                    ProfessionOompaLoompa.Developer -> stringResource(R.string.profession_developer)
                    ProfessionOompaLoompa.Gemcutter -> stringResource(R.string.profession_gemcutter)
                    ProfessionOompaLoompa.Medic -> stringResource(R.string.profession_medic)
                    ProfessionOompaLoompa.Metalworker -> stringResource(R.string.profession_metalworker)
                }

                Text(
                    text = profession,
                    modifier = Modifier

                        .padding(
                            vertical = 8.dp,
                            horizontal = 8.dp
                        ),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Icon(
                imageVector = when (oompaLoompa.isFavorite) {
                    true -> Icons.Filled.Favorite
                    false -> Icons.Filled.FavoriteBorder
                },
                contentDescription = stringResource(R.string.content_description_favorite),
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable {
                    when (oompaLoompa.isFavorite) {
                        true -> onClickUnfavorite()
                        false -> onClickFavorite()
                    }
                }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ItemFavOompaLoompaPreview() {
    ItemOompaLoompa(
        oompaLoompa = OompaLoompaState(
            firstName = "Carlos",
            lastName = "Pepote",
            profession = ProfessionOompaLoompa.Developer,
            image = "https://s3.eu-central-1.amazonaws.com/napptilus/level-test/2.jpg",
            isFavorite = true,
            id = 1
        ),
        onClickFavorite = {},
        onClickUnfavorite = {}
    )
}

@Preview(showBackground = true)
@Composable
fun ItemUnfavoriteOompaLoompaPreview() {
    ItemOompaLoompa(
        oompaLoompa = OompaLoompaState(
            firstName = "Carlos",
            lastName = "Pepote",
            profession = ProfessionOompaLoompa.Metalworker,
            image = "https://s3.eu-central-1.amazonaws.com/napptilus/level-test/2.jpg",
            isFavorite = false,
            id = 1
        ),
        onClickFavorite = {},
        onClickUnfavorite = {}
    )
}