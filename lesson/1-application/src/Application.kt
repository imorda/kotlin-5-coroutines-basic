import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.*

fun CoroutineScope.runApplication(
    runUI: suspend () -> Unit,
    runApi: suspend () -> Unit,
) {
    lateinit var launchApi: () -> Unit
    launchApi = {
        launch {
            try {
                runApi()
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                delay(1.seconds)
                launchApi()
            }
        }
    }
    launchApi()

    launch {
        runUI()
    }
}
