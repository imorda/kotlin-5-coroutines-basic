import kotlin.time.Duration
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

fun Flow<Cutoff>.resultsFlow(): Flow<Results> {
    val intermediateMap = mutableMapOf<String, Duration>()

    return flow {
        this@resultsFlow.collect {
            intermediateMap[it.number] = it.time
            this@flow.emit(Results(intermediateMap.toMap())) // make immutable copy
        }
    }
}
