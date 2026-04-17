package com.plcoding.nav3_guide.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Task
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.plcoding.nav3_guide.screens.ChangeSettingScreen
import com.plcoding.nav3_guide.screens.SettingsScreen
import com.plcoding.nav3_guide.screens.TodoDetailScreen
import com.plcoding.nav3_guide.screens.TodoListScreen
import androidx.compose.material3.adaptive.navigation3.ListDetailSceneStrategy
import androidx.compose.material3.adaptive.navigation3.rememberListDetailSceneStrategy
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.scene.SinglePaneSceneStrategy

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier
) {
    val navigationState = rememberNavigationState(
        startRoute = Route.TodoList,
        topLevelRoutes = TOP_LEVEL_DESTINATIONS.keys
    )
    val navigator = remember {
        Navigator(navigationState)
    }
    val resultStore = rememberResultStore()
    val strategy = rememberListDetailSceneStrategy<NavKey>()
    Scaffold(
        modifier = modifier,
        bottomBar = {
            TodoNavigationBar(
                selectedKey = navigationState.topLevelRoute,
                onSelectKey = {
                    navigator.navigate(it)
                }
            )
        }
    ) { innerPadding ->
        NavDisplay(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            onBack = navigator::goBack,
            sceneStrategies = listOf(strategy),
            entries = navigationState.toEntries(
                entryProvider {
                    entry<Route.TodoList>(
                        metadata = ListDetailSceneStrategy.listPane(
                            detailPlaceholder = {
                                DetailsPlaceholder()
                            }
                        )
                    ) {
                        TodoListScreen(
                            onTodoClick = {
                                navigator.navigate(Route.TodoDetail(it))
                            }
                        )
                    }
                    entry<Route.TodoFavorites>(
                        metadata = ListDetailSceneStrategy.listPane(
                            detailPlaceholder = {
                                DetailsPlaceholder()
                            }
                        )
                    ) {
                        TodoListScreen(
                            onTodoClick = {
                                navigator.navigate(Route.TodoDetail(it))
                            }
                        )
                    }
                    entry<Route.TodoDetail>(
                        metadata = ListDetailSceneStrategy.detailPane()
                    ) {
                        TodoDetailScreen(
                            todo = it.todo
                        )
                    }
                    entry<Route.Settings> {
                        SettingsScreen(
                            resultStore = resultStore,
                            onChangeSettingClick = {
                                navigator.navigate(Route.ChangeSetting)
                            }
                        )
                    }
                    entry<Route.ChangeSetting> {
                        ChangeSettingScreen(
                            resultStore = resultStore,
                            onSave = {
                                navigator.goBack()
                            }
                        )
                    }
                }
            )
        )
    }
}

@Composable
fun DetailsPlaceholder() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                modifier = Modifier.size(196.dp),
                imageVector = Icons.Default.Task,
                contentDescription = null
            )
            Text(text = "Pick a TODO")
        }
    }
}