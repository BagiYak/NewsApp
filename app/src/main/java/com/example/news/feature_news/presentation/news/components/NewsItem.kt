package com.example.news.feature_news.presentation.news.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.news.feature_news.domain.model.Article
import com.example.news.feature_news.presentation.util.Screen
import com.google.gson.Gson

@Composable
fun NewsItem(
    navController: NavHostController,
    article: Article,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .clickable {
                navController.navigate(
                    route = Screen.ArticleScreen.route +
                        "?article=${Gson().toJson(article)}"
                )
            }
    ) {
        Text(
            text = article.title?:"no title",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Text(text = article.author?:"no author", fontWeight = FontWeight.Light)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = article.description?:"no description")
    }
}