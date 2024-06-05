package clouds.space.mvi

sealed interface CrewIntent {

    data object GetAllCrew : CrewIntent
}