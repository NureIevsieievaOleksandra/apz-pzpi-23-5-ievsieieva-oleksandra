package ua.nure.smartlightadmin.ui.dashboard

import org.jetbrains.compose.resources.StringResource
import smartlightadmin.composeapp.generated.resources.Admin
import smartlightadmin.composeapp.generated.resources.Groups
import smartlightadmin.composeapp.generated.resources.Res
import smartlightadmin.composeapp.generated.resources.Users
import ua.nure.data.admin.BackupDto
import ua.nure.smartlightadmin.navigation.Screen
import ua.nure.smartlightadmin.repository.auth.dto.Role
import ua.nure.smartlightadmin.repository.group.dto.GroupDto
import ua.nure.smartlightadmin.repository.user.dto.UserDto

object Dashboard {
    sealed interface Event {
        data class OnNavigate(val route: Screen) : Event
        data object OnBack : Event
    }

    sealed interface Action {
        data object OnBack : Action
        data class OnNavigate(val route: Screen) : Action
        data class OnUserDelete(val userDto: UserDto) : Action
        data class OnGroupDelete(val group: GroupDto) : Action
        data class OnUserEdit(val user: UserDto) : Action
        data class OnGroupEdit(val group: GroupDto) : Action
        data object OnShowConfirmDeleteUserDialog : Action
        data object OnShowConfirmDeleteGroupDialog : Action
        data object OnDeleteUser : Action
        data object OnDeleteGroup: Action
        data class OnSelectRole(val role: Role) : Action
        data class OnIndexChange(val index: Int): Action
        data class OnGroupNameChange(val name: String) : Action
        data class OnGroupDescriptionChange(val description: String) : Action
        data object OnGroupUpdate : Action
        data class OnGroupAdd(val name: String, val description: String) : Action
        data object OnCreateBackup : Action
        data class OnRestoreBackupConfirm(val file: String? = null) : Action
        data object OnRestoreBackup : Action
        data class OnDeleteBackup(val file: String) : Action
        data object OnConfirmDeleteBackup : Action
        data object OnDismissDeleteBackup : Action
    }

    data class State(
        val inProgress: Boolean = false,
        val users: List<UserDto>? = null,
        val groups: List<GroupDto>? = null,
        val selectedUser: UserDto? = null,
        val selectedGroup: GroupDto? = null,
        val groupAction: GroupAction = GroupAction.Unknown,
        val showConfirmDeleteUserDialog: Boolean = false,
        val showConfirmDeleteGroupDialog: Boolean = false,
        val pagerItems: List<DashboardPagerItem> = listOf(
            DashboardPagerItem(
                title = Res.string.Users,
                index = 0
            ),
            DashboardPagerItem(
                title = Res.string.Groups,
                index = 1
            ),
            DashboardPagerItem(
                title = Res.string.Admin,
                index = 2
            ),
        ),
        val selectedIndex: Int = 0,
        val backups: List<BackupDto>? = null,
        val showDeleteBackupConfirm: Boolean = false,
        val file: String? = null,
        val showRestoreBackupConfirmDialog: Boolean = false
    )
}

data class DashboardPagerItem(
    val title: StringResource,
    val index: Int,
)

enum class GroupAction {
    Delete, Edit, Unknown
}