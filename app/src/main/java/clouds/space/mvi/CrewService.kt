package clouds.space.mvi

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class SpaceXClient {

    private val client by lazy {
        HttpClient(CIO) {
            install(DefaultRequest) {
                url {
                    protocol = URLProtocol.HTTPS

                    host = "api.spacexdata.com"
                }
            }

            install(Logging) {
                logger = Logger.ANDROID
                level = LogLevel.ALL
            }

            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }
    }

    operator fun invoke() = client
}

class CrewService(private val client: SpaceXClient) {

    suspend fun getAllCrew(): List<Crew> = client()
        .get { url(path = "/v4/crew") }
        .body<List<Crew>>()
}

val crewService = CrewService(SpaceXClient())