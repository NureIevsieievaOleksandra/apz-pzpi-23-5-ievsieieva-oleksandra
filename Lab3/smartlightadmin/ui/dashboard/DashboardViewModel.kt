package ua.nure.smartlightadmin.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ua.nure.smartlightadmin.repository.admin.AdminRepository
import ua.nure.smartlightadmin.repository.auth.dto.Role
import ua.nure.smartlightadmin.repository.group.GroupRepository
import ua.nure.smartlightadmin.repository.onError
import ua.nure.smartlightadmin.repository.onSuccess
import ua.nure.smartlightadmin.repository.toErrorMessage
import ua.nure.smartlightadmin.repository.token.TokenRepository
import ua.nure.smartlightadmin.repository.user.UserRepository
import ua.nure.smartlightadmin.ui.dashboard.Dashboard.Event.*

class DashboardViewModel(
    private val userRepository: UserRepository,
    private val tokenRepository: TokenRepository,
    private val groupRepository: GroupRepository,
    private val adminRepository: AdminRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(Dashboard.State())
    val state = _state.onStart {
        loadUsers()
        loadGroups()
        loadBackups()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = Dashboard.State()
    )

    private val _event = MutableSharedFlow<Dashboard.Event>()
    val event = _event.asSharedFlow()

    fun onAction(action: Dashboard.Action) = viewModelScope.launch {
        when (action) {
            Dashboard.Action.OnBack -> _event.emit(OnBack)
            is Dashboard.Action.OnNavigate -> _event.emit(OnNavigate(route = action.route))
            is Dashboard.Action.OnUserDelete -> {
                _state.update { s ->
                    s.copy(
                        selectedUser = action.userDto,
                        showConfirmDeleteUserDialog = true
                    )

                }
            }

            is Dashboard.Action.OnUserEdit -> {
                _state.update { s ->
                    s.copy(
                        selectedUser = if (state.value.selectedUser == null) action.user else null,
                    )
                }
            }

            Dashboard.Action.OnShowConfirmDeleteUserDialog -> {
                _state.update { s ->
                    s.copy(
                        showConfirmDeleteUserDialog = !s.showConfirmDeleteUserDialog
                    )
                }
            }

            Dashboard.Action.OnDeleteUser -> {
                state.value.selectedUser?.userId?.let { id ->
                    if (id != tokenRepository.userId) {
                        deleteUser(userId = id)
                    } else {
                        _state.update { s ->
                            s.copy(
                                selectedUser = null,
                                showConfirmDeleteUserDialog = false
                            )
                        }
                        println("Error: userId == current userId")
                    }
                }
            }

            is Dashboard.Action.OnSelectRole -> {
                state.value.selectedUser?.userId?.let { id ->
                    if (action.role == state.value.selectedUser?.role) {
                        _state.update { s ->
                            s.copy(
                                selectedUser = null,
                            )
                        }
                    } else {
                        updateUser(userId = id, role = action.role)
                    }
                }
            }

            is Dashboard.Action.OnIndexChange -> {
                _state.update { s ->
                    s.copy(
                        selectedIndex = action.index
                    )
                }
            }

            is Dashboard.Action.OnGroupEdit -> {
                _state.update { s ->
                    s.copy(
                        selectedGroup = if (s.selectedGroup == null) action.group else null,
                        groupAction = if (s.selectedGroup == null) GroupAction.Edit else GroupAction.Unknown
                    )
                }

            }

            is Dashboard.Action.OnGroupDelete -> {
                _state.update { s ->
                    s.copy(
                        selectedGroup = action.group,
                        groupAction = GroupAction.Delete,
                        showConfirmDeleteGroupDialog = true
                    )
                }
            }

            is Dashboard.Action.OnGroupDescriptionChange -> {
                _state.update { s ->
                    s.copy(
                        selectedGroup = s.selectedGroup?.copy(
                            description = action.description
                        )
                    )
                }
            }

            is Dashboard.Action.OnGroupNameChange -> {
                _state.update { s ->
                    s.copy(
                        selectedGroup = s.selectedGroup?.copy(
                            name = action.name
                        )
                    )
                }
            }

            Dashboard.Action.OnGroupUpdate -> {
                state.value.selectedGroup?.let { group ->
                    updateGroup(
                        groupId = group.groupId ?: -1,
                        name = group.name,
                        description = group.description
                    )
                }
            }

            Dashboard.Action.OnDeleteGroup -> {
                state.value.selectedGroup?.groupId?.let { id ->
                    deleteGroup(groupId = id)
                }

                _state.update { s ->
                    s.copy(
                        showConfirmDeleteGroupDialog = false
                    )
                }
            }

            Dashboard.Action.OnShowConfirmDeleteGroupDialog -> {
                _state.update { s ->
                    s.copy(
                        showConfirmDeleteGroupDialog = !s.showConfirmDeleteGroupDialog
                    )
                }
            }

            is Dashboard.Action.OnGroupAdd -> {
                addGroup(name = action.name, description = action.description)
            }

            Dashboard.Action.OnCreateBackup -> {
                createBackup()
            }

            is Dashboard.Action.OnDeleteBackup -> {
                _state.update { s ->
                    s.copy(
                        file = action.file,
                        showDeleteBackupConfirm = true
                    )
                }
            }
            is Dashboard.Action.OnRestoreBackupConfirm -> {
                _state.update { s ->
                    s.copy(
                        file = action.file,
                        showRestoreBackupConfirmDialog = action.file != null
                    )
                }
            }

            Dashboard.Action.OnConfirmDeleteBackup -> {
                _state.update { s ->
                    s.copy(
                        showDeleteBackupConfirm = false
                    )
                }
                state.value.file?.let { fileName ->
                    deleteBackup(fileName = fileName)
                }
            }

            Dashboard.Action.OnDismissDeleteBackup -> {
                _state.update { s ->
                    s.copy(
                        showDeleteBackupConfirm = false,
                        file = null
                    )
                }
            }

            Dashboard.Action.OnRestoreBackup -> {
                state.value.file?.let { file ->
                    restoreBackup(fileName = file)
                }
                _state.update { s ->
                    s.copy(
                        showRestoreBackupConfirmDialog = false
                    )
                }
            }
        }
    }

    private fun loadUsers() = viewModelScope.launch {
        userRepository.list(

        ).onSuccess { list ->
            _state.update { s ->
                s.copy(
                    users = list.sortedBy { it.userId }
                )
            }
        }.onError { error ->
            println(error.toErrorMessage())
        }
    }

    private fun loadGroups() = viewModelScope.launch {
        groupRepository.list(

        ).onSuccess { list ->
            _state.update { s ->
                s.copy(
                    groups = list.sortedBy { it.groupId }
                )
            }
        }.onError { error ->
            println(error.toErrorMessage())
        }
    }

    private fun deleteUser(userId: Long) = viewModelScope.launch {
        userRepository.delete(
            userId = userId
        ).onSuccess {
            _state.update { s ->
                s.copy(
                    selectedUser = null,
                    users = s.users?.filter { it.userId == userId },
                    showConfirmDeleteUserDialog = false
                )
            }

        }.onError { error ->
            println("Delete user error: id: $userId, error: ${error.toErrorMessage()}")
        }
    }

    private fun updateUser(userId: Long, role: Role) = viewModelScope.launch {
        if (tokenRepository.userId != userId) {
            userRepository.edit(
                userId = userId,
                role = role
            ).onSuccess { updatedUser ->
                _state.update { s ->
                    s.copy(
                        users = s.users?.map {
                            if (it.userId == userId) {
                                updatedUser
                            } else {
                                it
                            }
                        },
                        selectedUser = null,
                    )
                }
            }.onError { error ->
                println("Update user error: id: $userId, error: ${error.toErrorMessage()}")
            }
        } else {
            println("Error: userId == current userId")
        }
    }

    private fun updateGroup(groupId: Long, name: String? = null, description: String? = null) =
        viewModelScope.launch {
            groupRepository.update(
                groupId = groupId,
                name = name,
                description = description
            ).onSuccess { updatedGroup ->
                _state.update { s ->
                    s.copy(
                        groups = s.groups?.map {
                            if (it.groupId == updatedGroup.groupId) {
                                updatedGroup
                            } else {
                                it
                            }
                        },
                        selectedGroup = null,
                        groupAction = GroupAction.Unknown
                    )
                }
            }.onError { error ->
                println(error.toErrorMessage())
            }
        }

    private fun deleteGroup(groupId: Long) = viewModelScope.launch {
        groupRepository.delete(
            groupId = groupId
        ).onSuccess {
            _state.update { s ->
                s.copy(
                    groups = s.groups?.filter { it.groupId != groupId },
                    selectedGroup = null,
                    groupAction = GroupAction.Unknown
                )
            }
        }.onError { error ->
            println(error.toErrorMessage())
        }
    }

    private fun addGroup(name: String, description: String) = viewModelScope.launch {
        if (name.isNotEmpty() && description.isNotEmpty()) {
            groupRepository.create(
                name = name,
                description = description
            ).onSuccess { group ->
                _state.update { s ->
                    s.copy(
                        groups = (s.groups?.plus(group) ?: listOf(group)).sortedBy { it.groupId }
                    )
                }

            }
        }
    }

    private fun loadBackups() = viewModelScope.launch {
        adminRepository.list(

        ).onSuccess { list ->
            _state.update { s ->
                s.copy(
                    backups = list
                )
            }

        }.onError { error ->
            println(error.toErrorMessage())
        }
    }

    private fun createBackup() = viewModelScope.launch {
        adminRepository.create(

        ).onSuccess {
            loadBackups()
        }.onError { error ->
            println(error.toErrorMessage())
        }
    }

    private fun deleteBackup(fileName: String) = viewModelScope.launch {

        adminRepository.delete(
            fileName = fileName
        ).onSuccess {
            _state.update { s ->
                s.copy(
                    backups = s.backups?.filter { it.fileName != fileName },
                    file = null
                )
            }
        }.onError { error ->
            println(error.toErrorMessage())
        }
    }

    private fun restoreBackup(fileName: String) = viewModelScope.launch {
        adminRepository.restore(
            fileName = fileName
        ).onSuccess {
            _state.update { s ->
                s.copy(
                    file = null
                )
            }


        }.onError { error ->
            println(error.toErrorMessage())
        }
    }
}