package com.plcoding.nav3_guide.auth

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.scene.SceneStrategy
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.plcoding.nav3_guide.navigation.BottomSheetSceneStrategy
import com.plcoding.nav3_guide.navigation.Route
import com.plcoding.nav3_guide.screens.CustomBottomSheet
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthNavigation(
    onLogin: () -> Unit,
    modifier: Modifier = Modifier
) {
    val authBackStack = rememberNavBackStack(
        configuration = SavedStateConfiguration {
            serializersModule = SerializersModule {
                polymorphic(NavKey::class) {
                    subclass(Route.Auth.Login::class, Route.Auth.Login.serializer())
                    subclass(Route.Auth.Register::class, Route.Auth.Register.serializer())
                }
            }
        },
        Route.Auth.Login
    )
    val bottomSheetStrategy = remember { BottomSheetSceneStrategy<NavKey>() }
    val sharedAuthViewModel = viewModel { SharedAuthViewModel() }
    NavDisplay(
        backStack = authBackStack,
        modifier = modifier,
        sceneStrategies = listOf(bottomSheetStrategy),
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        transitionSpec = {
            slideInHorizontally { it } + fadeIn() togetherWith
                    slideOutHorizontally { -it } + fadeOut()
        },
        popTransitionSpec = {
            slideInHorizontally { -it } + fadeIn() togetherWith
                    slideOutHorizontally { it } + fadeOut()
        },
        predictivePopTransitionSpec = {
            slideInHorizontally { -it } + fadeIn() togetherWith
                    slideOutHorizontally { it } + fadeOut()
        },
        entryProvider = entryProvider {
            entry<Route.Auth.Login> {
                LoginScreen(
                    viewModel = viewModel { LoginViewModel() },
                    sharedAuthViewModel = sharedAuthViewModel,
                    onLogin = onLogin,
                    onBSClick = {
                        authBackStack.add(Route.Auth.SampleBottomSheet)
                    },
                    onRegisterClick = {
                        authBackStack.add(Route.Auth.Register)
                    }
                )
            }
            entry<Route.Auth.Register> {
                RegisterScreen(
                    viewModel = viewModel { RegisterViewModel() },
                    sharedAuthViewModel = sharedAuthViewModel,
                )
            }
            entry<Route.Auth.SampleBottomSheet>(
                metadata = BottomSheetSceneStrategy.bottomSheet()
            ) {
                CustomBottomSheet(onDismissed = authBackStack::removeLastOrNull)
            }
        }
    )
}