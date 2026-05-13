package ua.nure.smartlight.ui.analytics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ua.nure.smartlight.repository.analytics.AnalyticsRepository
import ua.nure.smartlight.repository.onSuccess
import ua.nure.smartlight.ui.analytics.Analytics.Event
import ua.nure.smartlight.ui.settings.Settings
import javax.inject.Inject

@HiltViewModel
class AnalyticsViewModel @Inject constructor(
    private val analyticsRepository: AnalyticsRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(Analytics.State())
    val state = _state.onStart {
        loadAnalytics()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = Analytics.State()
    )

    private val _event = MutableSharedFlow<Analytics.Event>()
    val event = _event.asSharedFlow()

    fun onAction(action: Analytics.Action) = viewModelScope.launch {
        when(action) {
            Analytics.Action.OnBack -> _event.emit(Event.OnBack)
            is Analytics.Action.OnNavigate -> _event.emit(Event.OnNavigate(route = action.route))
        }
    }

    private fun loadAnalytics() = viewModelScope.launch {
        if(_state.value.analytics == null) {
            analyticsRepository.loadAnalytics(

            ).onSuccess { analytics ->
                _state.update { s ->
                    s.copy(
                        analytics = analytics
                    )
                }
            }
        }
    }
}