package clouds.space.mvi

import kotlinx.serialization.Serializable

@Serializable
data class Crew(
    val name: String,
    val agency: String,
    val image: String,
    val wikipedia: String,
    val launches: List<String>,
    val status: String,
    val id: String,
)
