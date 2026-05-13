package ua.nure.smartlight.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import ua.nure.smartlight.repository.token.TokenRepository
import ua.nure.smartlight.ui.settings.compose.ThemeEntity
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val tokenRepository: TokenRepository,
) : ViewModel() {
    val isDarkTheme = tokenRepository.themeFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = ThemeEntity.System
        )
}