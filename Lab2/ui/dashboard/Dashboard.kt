package ua.nure.smartlight.ui.dashboard

import ua.nure.smartlight.db.entity.Group
import ua.nure.smartlight.db.entity.LampEntity
import ua.nure.smartlight.navigation.Screen

object Dashboard {
    sealed interface Event {
        data class OnNavigate(val route: Screen) : Event
        data object OnBack : Event
    }

    sealed interface Action {
        data object OnBack : Action
        data class OnNavigate(val route: Screen) : Action
        data object OnAddGroupDialog : Action
        data class OnDeleteGroupDialog(val group: Group? = null) : Action
        data class OnGroupTitleChange(val name: String) : Action
        data class OnGroupDescriptionChange(val desc: String) : Action
        data object OnAddGroup : Action
        data object OnDeleteGroup : Action
        data class OnDeleteLampDialog(val lamp: LampEntity?) : Action
        data class OnEditGroupDialog(val group: Group) : Action
        data class OnAddLampDialogDialog(val group: Group?, val lamp: LampEntity?) : Action
        data object OnSaveGroup : Action
        data class OnLampAddOrChange(val lamp: LampEntity) : Action
        data object OnDeleteLamp : Action
        data class OnEditLampDialog(val group: Group, val lamp: LampEntity) : Action
    }

    data class State(
        val inProgress: Boolean = false,
        val isAdmin: Boolean = false,
        val groups: List<Group>? = null,
        val addGroupDialogActive: Boolean = false,
        val deleteGroupDialogActive : Boolean = false,
        val deleteLampDialogActive: Boolean = false,
        val newGroupName: String = "",
        val newGroupDescription: String = "",
        val selectedGroup: Group? = null,
        val selectedLamp: LampEntity? = null,
        val addLampDialogActive: Boolean = false,
    )
}