import kotlinx.coroutines.*

@OptIn(ExperimentalCoroutinesApi::class, DelicateCoroutinesApi::class)
class SequentialProcessor(private val handler: (String) -> String) : TaskProcessor {
    private val context = newSingleThreadContext("sequential-processor")
    override suspend fun process(argument: String): String {
        val job: Deferred<String> = CoroutineScope(context).async {
            handler(argument)
        }
        return job.await()
    }
}
