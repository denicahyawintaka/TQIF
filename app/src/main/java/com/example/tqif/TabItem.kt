package com.example.tqif

import androidx.compose.runtime.Composable
import com.example.tqif.Detail.FollowerScreen
import com.example.tqif.Detail.FollowingScreen

typealias ComposableFun = @Composable () -> Unit

sealed class TabItem(var title: String, var screen: ComposableFun) {
    object Following : TabItem("Following", { FollowingScreen() })
    object Follower : TabItem("Follower", { FollowerScreen() })
}
