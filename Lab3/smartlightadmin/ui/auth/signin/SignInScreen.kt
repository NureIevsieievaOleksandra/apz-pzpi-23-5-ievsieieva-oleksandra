package ua.nure.smartlightadmin.ui.auth.signin

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import smartlightadmin.composeapp.generated.resources.Res
import smartlightadmin.composeapp.generated.resources.name
import smartlightadmin.composeapp.generated.resources.password
import smartlightadmin.composeapp.generated.resources.signIn
import smartlightadmin.composeapp.generated.resources.smart_light
import ua.nure.smartlightadmin.ui.composable.SLInputField
import ua.nure.smartlightadmin.LocalPlatformProvider
import ua.nure.smartlightadmin.Platform
import ua.nure.smartlightadmin.PlatformType
import ua.nure.smartlightadmin.ui.composable.SLButton
import ua.nure.smartlightadmin.ui.composable.SmartLightScreen
import ua.nure.smartlightadmin.ui.theme.AppTheme

@Composable
fun SignInScreen(
    viewModel: SignInViewModel,
    navController: NavController
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.event.collect {
            when (it) {
                SignIn.Event.OnBack -> navController.navigateUp()
                is SignIn.Event.OnNavigate -> navController.navigate(route = it.route)
            }
        }
    }

    SignInScreenContent(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun SignInScreenContent(
    state: SignIn.State,
    onAction: (SignIn.Action) -> Unit
) {
    SmartLightScreen {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .size(300.dp),
            painter = painterResource(Res.drawable.smart_light),
            contentDescription = null
        )

        SLInputField(
            modifier = Modifier.fillMaxWidth()
                .padding(
                    vertical = AppTheme.dimension.normal,
                    horizontal = AppTheme.dimension.normal
                ),
            label = stringResource(Res.string.name),
            value = state.name,
            errorText = state.loginError,
            onValueChange = {
                onAction(SignIn.Action.OnNameChange(name = it))
            }
        )

        SLInputField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppTheme.dimension.normal)
            ,
            label = stringResource(Res.string.password),
            value = state.password,
            isPassword = true,
            onValueChange = {
                onAction(SignIn.Action.OnPasswordChange(password = it))
            }
        )

        SLButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 34.dp,
                    horizontal = AppTheme.dimension.normal
                ),
            text = stringResource(Res .string.signIn)
        ) {
            onAction(SignIn.Action.OnSignIn)
        }

    }
}

@Preview
@Composable
fun SignInScreenPreview() {
    CompositionLocalProvider(
        LocalPlatformProvider provides object: Platform {
            override val name: String
                get() = ""
            override val type: PlatformType
                get() = PlatformType.WEB
        }
    ) {
        AppTheme {
            Box(modifier = Modifier.background(color = AppTheme.color.background)) {
                SignInScreenContent(
                    state = SignIn.State(),
                ) {}
            }
        }
    }
}