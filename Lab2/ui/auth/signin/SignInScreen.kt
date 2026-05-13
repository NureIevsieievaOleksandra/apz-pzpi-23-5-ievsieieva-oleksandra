package ua.nure.smartlight.ui.auth.signin

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import ua.nure.smartlight.R
import ua.nure.smartlight.ui.composable.SLButton
import ua.nure.smartlight.ui.composable.SLInputField
import ua.nure.smartlight.ui.composable.SmartLightScreen
import ua.nure.smartlight.ui.theme.AppTheme

@Composable
fun SignInScreen(
    viewModel: SignInViewModel,
    navController: NavController
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.event.collect {
            when(it) {
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
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            model = R.drawable.smart_light,
            contentDescription = null
        )
        SLInputField(
            modifier = Modifier.fillMaxWidth()
                .padding(
                    vertical = AppTheme.dimension.normal,
                    horizontal = AppTheme.dimension.normal
                ),
            label = stringResource(R.string.name),
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
            label = stringResource(R.string.password),
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
            text = stringResource(R.string.signIn)
        ) {
            onAction(SignIn.Action.OnSignIn)
        }
    }
}

@Preview
@Composable
fun SignInPreview(modifier: Modifier = Modifier) {
    AppTheme() {
        SignInScreenContent(
            state = SignIn.State()
        ) { }
    }

}