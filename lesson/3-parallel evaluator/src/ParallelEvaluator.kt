import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.*

class ParallelEvaluator {
    suspend fun run(task: Task, n: Int, context: CoroutineContext) {
        val jobs = mutableListOf<Deferred<Unit>>()
        repeat(n) {
            jobs.add(
                CoroutineScope(context).async {
                    task.run(it)
                },
            )
        }

        jobs.forEach {
            try {
                it.await()
            } catch (e: Exception) {
                throw TaskEvaluationException(e)
            }
        }
    }
}
