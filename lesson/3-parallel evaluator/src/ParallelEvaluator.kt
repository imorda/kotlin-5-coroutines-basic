import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.*

class ParallelEvaluator {
    suspend fun run(task: Task, n: Int, context: CoroutineContext) {
        val exceptionHandler = CoroutineExceptionHandler { _, e ->
            throw TaskEvaluationException(e)
        }

        val jobs = mutableListOf<Deferred<Unit>>()
        repeat(n) {
            jobs.add(
                CoroutineScope(context + exceptionHandler).async {
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
