package clouds.space.mvi

interface CrewRepository {

    suspend fun getAllCrew(): List<Crew>
}

class SpaceXCrewRepository(
    private val service: CrewService
) : CrewRepository {

    override suspend fun getAllCrew(): List<Crew> = service.getAllCrew()
}

val crewRepository: CrewRepository = SpaceXCrewRepository(crewService)