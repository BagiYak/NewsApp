package com.example.news.feature_news.presentation.article.components

import android.annotation.SuppressLint
import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.news.R
import com.example.news.feature_news.domain.model.Article
import com.example.news.feature_news.presentation.article.ArticleEvent
import com.example.news.feature_news.presentation.article.ArticleState
import com.example.news.feature_news.presentation.article.ArticleViewModel
import com.example.news.feature_news.presentation.news.NewsEndpoint
import com.example.news.feature_news.presentation.news.NewsEvent
import com.example.news.feature_news.presentation.news.SavedNewsViewModel
import com.example.news.feature_news.presentation.util.LoadImage
import com.example.news.feature_news.presentation.util.Screen
import com.google.gson.Gson
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ArticleScreen(
    navController: NavHostController,
    viewModel: ArticleViewModel,
    savedNewsViewModel: SavedNewsViewModel,
    article: Article,
) {

    viewModel.state.value = ArticleState(article)
    val state = viewModel.state

    var imageState: MutableState<Bitmap?> = remember{ mutableStateOf(null) }
    LoadImage(
        url = article.urlToImage?:"",
        bitmapState = imageState
    )
    val img = imageState.value

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if(state.value.article?.id == null) {
                        viewModel.onEvent(ArticleEvent.SaveArticle)
                        savedNewsViewModel.onEvent(NewsEvent.GetSavedNews)
                    } else {
                        viewModel.onEvent(ArticleEvent.DeleteArticle)
                        savedNewsViewModel.onEvent(NewsEvent.GetSavedNews)
                    }
                    navController.navigateUp()
                },
                backgroundColor = MaterialTheme.colors.primary,
                modifier = Modifier.padding(bottom = 60.dp)
            ) {
                Icon(
                    painter = painterResource(
                        id = if(state.value.article?.id != null) {
                            (R.drawable.ic_bookmark_added)
                        } else {
                            (R.drawable.ic_bookmark)
                        }
                    ),
                    contentDescription = "Save article"
                )
            }
        },
    ) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
        ) {
            state.value.article?.let {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    item {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = article.title?:"no title",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        }
                        Divider()
                        Spacer(modifier = Modifier.height(16.dp))

                        if (img != null) {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .shadow(16.dp, RoundedCornerShape(16.dp))
                                    .border(
                                        width = 1.dp,
                                        color = Color.Transparent,
                                        shape = RoundedCornerShape(16.dp)
                                    )
                            ) {
                                Image(
                                    bitmap = img.asImageBitmap(),
                                    contentDescription = "image",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(128.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = article.author?:"no author",
                                modifier = Modifier.fillMaxWidth(),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Light
                            )
                        }
                        Spacer(modifier = Modifier.height(0.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = article.publishedAt?:"no published date",
                                modifier = Modifier.fillMaxWidth(),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Light
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = article.url?:"no url link",
                                modifier = Modifier.fillMaxWidth(),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Light
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = article.description?:"no description",
                                modifier = Modifier.fillMaxWidth(),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Light
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = article.content?:"no content",
                                modifier = Modifier.fillMaxWidth(),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Light
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                }
                Spacer(modifier = Modifier.height(32.dp))
            }
            if(state.value.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}