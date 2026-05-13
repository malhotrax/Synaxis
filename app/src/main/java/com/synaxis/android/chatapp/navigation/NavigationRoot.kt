package com.synaxis.android.chatapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.synaxis.android.chatapp.feature.auth.presentation.login.LoginScreen
import com.synaxis.android.chatapp.feature.auth.presentation.signup.SignUpScreen
import com.synaxis.android.chatapp.feature.forget_password.ForgetPasswordScreen
import com.synaxis.android.chatapp.feature.home.HomeScreen
import com.synaxis.android.chatapp.feature.user.presentation.search.SearchScreen
import com.synaxis.android.chatapp.feature.user.presentation.profile.update.full_name.UpdateFullName
import com.synaxis.android.chatapp.feature.user.presentation.profile.update.username.UpdateUsername
import com.synaxis.android.chatapp.navigation.route.Routes

@Composable
fun NavigationRoot() {
    val backStack = rememberNavBackStack(Routes.Login)

    NavDisplay(
        backStack = backStack,
        onBack = {
            backStack.removeLastOrNull()
        },
        entryProvider = entryProvider {
            entry<Routes.Home> {
                HomeScreen(
                    navigateToSearch = {
                        backStack.add(Routes.Search)
                    },
                    navigateToUpdateUsername = {
                        backStack.add(Routes.UpdateUsername)
                    },
                    navigateToUpdateFullName = {
                        backStack.add(Routes.UpdateFullName)
                    }
                )
            }
            entry<Routes.ForgetPassword> { ForgetPasswordScreen() }
            entry<Routes.Login> {
                LoginScreen(
                    navigateToHome = {
                        backStack.clear()
                        backStack.add(Routes.Home)
                    },
                    navigateToSignUp = {
                        backStack.add(Routes.SignUp)
                    },
                    navigateToForgetPassword = {
                        backStack.add(Routes.ForgetPassword)
                    }
                )
            }
            entry<Routes.SignUp> {
                SignUpScreen(
                    navigateToHome = {
                        backStack.clear()
                        backStack.add(Routes.Home)
                    },
                    navigateToLogin = {
                        backStack.removeLastOrNull()
                    }
                )
            }

            entry<Routes.Search> { SearchScreen() }
            entry<Routes.UpdateUsername> { UpdateUsername(navigateBack = { backStack.removeLastOrNull() }) }
            entry<Routes.UpdateFullName> { UpdateFullName(navigateBack = { backStack.removeLastOrNull() }) }
        }
    )
}

