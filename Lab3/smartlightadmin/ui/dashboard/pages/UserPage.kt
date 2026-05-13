package ua.nure.smartlightadmin.ui.dashboard.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.stringResource
import smartlightadmin.composeapp.generated.resources.Res
import smartlightadmin.composeapp.generated.resources.usersTitle
import ua.nure.smartlightadmin.ui.dashboard.Dashboard
import ua.nure.smartlightadmin.ui.dashboard.compose.UserItem
import ua.nure.smartlightadmin.ui.theme.AppTheme

@Composable
fun UserPage(
    modifier: Modifier,
    state: Dashboard.State,
    onAction: (Dashboard.Action) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1F),
            verticalArrangement = Arrangement.spacedBy(AppTheme.dimension.small)
        ) {
            items(items = state.users ?: emptyList(), key = { it.userId ?: -1L }) { user ->
                UserItem(
                    modifier = Modifier,
                    user = user,
                    selectedUser = state.selectedUser,
                    onAction = onAction
                )
            }
            item {
                Spacer(modifier = Modifier.fillMaxWidth().height(AppTheme.dimension.normal))
            }
        }

    }

}