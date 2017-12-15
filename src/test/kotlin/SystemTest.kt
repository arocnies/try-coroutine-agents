import com.anies.agent.Agent
import com.anies.agent.dsl.environment
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Test
import java.util.concurrent.TimeUnit

class SystemTest {
    @Test
    fun testCreateEnv() {
        runBlocking {
            environment {
                agent(HelloAgent::class)
            }.run()
            delay(5, TimeUnit.SECONDS)
        }
    }
}

class HelloAgent : Agent() {
    suspend override fun run() {
        while (true) {
            println("Hello agents!")
            delay(1, TimeUnit.SECONDS)
        }
    }
}