package com.rohan.rohannetworking.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.rohan.rohannetworking.Constants.BASE_IMAGE_URL
import com.rohan.rohannetworking.R

@Stable
@Composable
fun MovieListItem(modifier: Modifier = Modifier, title: String, posterPath: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            modifier = modifier
                .clip(RoundedCornerShape(16.dp)),
            model = ImageRequest.Builder(LocalContext.current).data("${BASE_IMAGE_URL}${posterPath}").crossfade(true).build(),
            contentDescription = null,
            placeholder = painterResource(id = R.drawable.outline_image_24)
        )
        Text(text = title)
    }
}