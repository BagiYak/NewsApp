package com.example.news.feature_news.presentation.news.components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bumptech.glide.Glide
import com.example.news.feature_news.domain.model.Article
import com.example.news.feature_news.presentation.util.LoadImage
import com.example.news.feature_news.presentation.util.Screen
import com.google.gson.Gson

@Composable
fun NewsItem(
    navController: NavHostController,
    article: Article,
    modifier: Modifier = Modifier
) {
    var imageState: MutableState<Bitmap?> = remember{ mutableStateOf(null) }
    LoadImage(
        url = article.urlToImage?:"",
        bitmapState = imageState
    )
    val img = imageState.value

    Column(
        modifier = modifier.padding(16.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = {
                    // navigate to article detail screen
                    navController.navigate(
                        route = Screen.ArticleScreen.route +
                                "?article=${Gson().toJson(article)}"
                    )
                })
                .shadow(16.dp, RoundedCornerShape(16.dp))
                .border(
                    width = 1.dp,
                    color = Color.Transparent,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                if (img != null) {
                    Column {
                        Image(
                            bitmap = img.asImageBitmap(),
                            contentDescription = "image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(128.dp)
                        )
                    }
                }
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Text(
                            text = article.title?:"no title",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Text(
                            text = article.author?:"no author",
                            modifier = Modifier.fillMaxWidth(),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Light
                        )
                    }
                }
            }
        }
    }
}