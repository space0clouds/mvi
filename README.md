# MVI

MVI

view(model(intent()))

view → intent : ui events

```kotlin
sealed interface CrewIntent {
    data object GetAllCrew : CrewIntent
}
```

```kotlin
fun CrewListScreen(..) {
		LaunchedEffect(Unit) {
				viewModel.handleIntent(CrewIntent.GetAllCrew)
		}
}
```

intent → model : actions to manipulate model

```kotlin
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
```

model → view : new model to display

```kotlin
fun CrewListScreen(..) {
		val state by viewModel.state.collectAsState()
		
		when {
				state.isLoading -> { .. }
				state.crew.isNotEmpty() -> { 
						CrewList(..)
				}
		}
}
```