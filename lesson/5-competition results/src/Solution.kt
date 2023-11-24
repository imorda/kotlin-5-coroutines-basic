import kotlin.time.Duration
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.runningFold

fun Flow<Cutoff>.resultsFlow(): Flow<Results> {
    return this.runningFold(mutableMapOf<String, Duration>()) { acc, value ->
        acc[value.number] = value.time
        return@runningFold acc
    }.drop(1).map { Results(it.toMap()) }
}
