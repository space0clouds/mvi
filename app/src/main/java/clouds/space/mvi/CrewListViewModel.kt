package clouds.space.mvi

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CrewListViewModel(private val repository: CrewRepository = crewRepository) : ViewModel() {

    private val _state = MutableStateFlow(CrewListViewState())
    val state: StateFlow<CrewListViewState> = _state

    fun handleIntent(intent: CrewIntent) {
        viewModelScope.launch {
            when (intent) {
                CrewIntent.GetAllCrew -> getAllCrew()
            }
        }
    }

    private suspend fun getAllCrew() {
        delay(500L)

        try {
            _state.update {
                it.copy(
                    isLoading = false,
                    crew = repository.getAllCrew()
                )
            }
        } catch (e: Exception) {
            Log.d("howsweet", e.localizedMessage ?: "networking failed :(")

            _state.update { it.copy(exception = e) }
        }
    }
}

sealed interface CrewIntent {
    data object GetAllCrew : CrewIntent
}

data class CrewListViewState(
    val isLoading: Boolean = true,
    val crew: List<Crew> = emptyList(),
    val exception: Throwable? = null,
)