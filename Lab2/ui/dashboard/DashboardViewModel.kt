package ua.nure.smartlight.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ua.nure.smartlight.db.entity.LampEntity
import ua.nure.smartlight.repository.auth.dto.Role
import ua.nure.smartlight.repository.group.GroupRepository
import ua.nure.smartlight.repository.lamp.LampRepository
import ua.nure.smartlight.repository.onError
import ua.nure.smartlight.repository.onSuccess
import ua.nure.smartlight.repository.token.TokenRepository
import ua.nure.smartlight.ui.dashboard.Dashboard.Event.*
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val groupRepository: GroupRepository,
    private val tokenRepository: TokenRepository,
    private val lampRepository: LampRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(
        Dashboard.State(
            isAdmin = tokenRepository.role == Role.Admin
        )
    )
    val state = _state.onStart {
        observeGroups()
        loadGroups()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = Dashboard.State(
            isAdmin = tokenRepository.role == Role.Admin
        )
    )

    var observeGroupsJob: Job? = null
    var loadGroupsJob: Job? = null
    var observeLampsJob: Job? = null

    private val _event = MutableSharedFlow<Dashboard.Event>()
    val event = _event.asSharedFlow()

    fun onAction(action: Dashboard.Action) = viewModelScope.launch {

        when (action) {
            Dashboard.Action.OnBack -> {}
            is Dashboard.Action.OnNavigate -> {
                _event.emit(OnNavigate(route = action.route))
            }

            Dashboard.Action.OnAddGroupDialog -> {
                _state.update { s ->
                    s.copy(
                        addGroupDialogActive = !s.addGroupDialogActive
                    )
                }
            }

            is Dashboard.Action.OnGroupDescriptionChange -> {
                _state.update { s ->
                    s.copy(
                        newGroupDescription = action.desc,
                    )
                }
            }

            is Dashboard.Action.OnGroupTitleChange -> {
                _state.update { s ->
                    s.copy(
                        newGroupName = action.name,
                    )
                }
            }

            Dashboard.Action.OnAddGroup -> {
                _state.update { s ->
                    s.copy(
                        addGroupDialogActive = false
                    )
                }
                addGroup(
                    title = state.value.newGroupName,
                    description = state.value.newGroupDescription
                )
            }

            Dashboard.Action.OnDeleteGroup -> {
                _state.update { s ->
                    s.copy(
                        deleteGroupDialogActive = false
                    )
                }
                state.value.selectedGroup?.group?.groupId?.let { id ->
                    state.value.selectedGroup?.lamps?.forEach { lamp ->
                        deleteLamp(lampId = lamp.lampId)
                    }
                    deleteGroup(groupId = id)
                }

            }
            is Dashboard.Action.OnDeleteGroupDialog -> {
                _state.update { s ->
                    s.copy(
                        deleteGroupDialogActive = !s.deleteGroupDialogActive,
                        selectedGroup = action.group ?: s.selectedGroup
                    )
                }
            }
            is Dashboard.Action.OnEditGroupDialog -> {
                _state.update { s ->
                    s.copy(
                        selectedGroup = action.group,
                        newGroupName = action.group.group.name ?: "",
                        newGroupDescription = action.group.group.description ?: "",
                        addGroupDialogActive = true
                    )
                }
            }

            Dashboard.Action.OnSaveGroup -> {
                _state.update { s ->
                    s.copy(
                        addGroupDialogActive = false
                    )
                }
                state.value.selectedGroup?.group?.groupId?.let { id ->
                    editGroup(groupId = id, title = state.value.newGroupName, description = state.value.newGroupDescription)
                }
            }

            is Dashboard.Action.OnLampAddOrChange -> {
                _state.update { s ->
                    s.copy(
                        addLampDialogActive = false
                    )
                }
                addOrEditLamp(lamp = action.lamp)
            }

            is Dashboard.Action.OnAddLampDialogDialog -> {
                _state.update { s ->
                    s.copy(
                        addLampDialogActive = !s.addLampDialogActive,
                        selectedGroup = action.group,
                        selectedLamp = action.lamp
                    )
                }
            }

            is Dashboard.Action.OnDeleteLampDialog -> {
                _state.update { s ->
                    s.copy(
                        deleteLampDialogActive = !s.deleteLampDialogActive,
                        selectedLamp = action.lamp
                    )
                }
            }

            Dashboard.Action.OnDeleteLamp -> {
                state.value.selectedLamp?.let { lamp ->
                    deleteLamp(lampId = lamp.lampId)
                }
                _state.update { s ->
                    s.copy(
                        deleteLampDialogActive = false,
                        selectedLamp = null
                    )
                }
            }

            is Dashboard.Action.OnEditLampDialog -> {
                _state.update { s ->
                    s.copy(
                        selectedLamp = action.lamp,
                        selectedGroup = action.group,
                        addLampDialogActive = true
                    )
                }
            }
        }
    }

    private fun observeGroups() {
        observeGroupsJob?.cancel()
        observeGroupsJob = viewModelScope.launch {
            groupRepository.get().collect { list ->
                _state.update { s ->
                    s.copy(
                        groups = list
                    )
                }
            }
        }
    }

    private fun loadGroups() {
        loadGroupsJob?.cancel()
        loadGroupsJob = viewModelScope.launch {
            groupRepository.load()
        }
    }

    private fun addGroup(title: String, description: String) = viewModelScope.launch {
        groupRepository.create(
            title = title,
            description = description
        ).onSuccess {
            _state.update { s ->
                s.copy(
                    newGroupName = "",
                    newGroupDescription = ""
                )
            }
        }.onError {
            _state.update { s ->
                s.copy(
                    newGroupName = "",
                    newGroupDescription = ""
                )
            }
        }
    }

    private fun editGroup(groupId: Long, title: String, description: String) = viewModelScope.launch {
        groupRepository.edit(
            groupId = groupId,
            title = title,
            description = description
        ).onSuccess {
            _state.update { s ->
                s.copy(
                    selectedGroup = null,
                    newGroupName = "",
                    newGroupDescription = ""
                )
            }
        }.onError {
            _state.update { s ->
                s.copy(
                    selectedGroup = null,
                    newGroupName = "",
                    newGroupDescription = ""
                )
            }
        }
    }

    private fun deleteGroup(groupId: Long) = viewModelScope.launch {
        groupRepository.delete(
            groupId = groupId
        ).onSuccess {
            _state.update { s ->
                s.copy(
                    selectedGroup = null
                )
            }
        }
    }
    private fun addOrEditLamp(lamp: LampEntity) = viewModelScope.launch {
        lampRepository.editLamp(
            lampEntity = lamp
        )
    }

    private fun deleteLamp(lampId: Long) = viewModelScope.launch {
        lampRepository.deleteLamp(lampId = lampId)
    }
}