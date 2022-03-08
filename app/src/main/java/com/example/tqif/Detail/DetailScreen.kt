package com.example.tqif.Detail

import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tqif.MainViewModel
import com.example.tqif.TabItem
import com.example.tqif.UserList
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch


@OptIn(ExperimentalPagerApi::class)
@Composable
fun DetailScreen(
    username: String?,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    val tabs = listOf(TabItem.Following, TabItem.Follower)

    username?.let {
        mainViewModel.loadDetail(username)
    }
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
            )
        }
    ) {

        tabs.forEachIndexed { index, tab ->
            Tab(
                text = { Text(text = tab.title) },
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
            )
        }
    }

    HorizontalPager(
        count = tabs.size,
        state = pagerState,
    ) { page ->
        tabs[page].screen()
    }

}

@Composable
fun FollowingScreen(
    mainViewModel: MainViewModel = hiltViewModel()
) {
    when (val state = mainViewModel.detailViewState.value) {
        is DetailViewState.Success -> {
            UserList(users = state.following)
        }
    }
}

@Composable
fun FollowerScreen(
    mainViewModel: MainViewModel = hiltViewModel()
) {
    when (val state = mainViewModel.detailViewState.value) {
        is DetailViewState.Success -> {
            UserList(users = state.follower)
        }
    }
}
