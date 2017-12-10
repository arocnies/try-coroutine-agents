package com.anies.agent

import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.channels.Channel
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.reflect.KClass

data class EntityId(private val uuid: UUID)
data class Massage<out T>(val content: T, val sender: EntityId, val recipient: EntityId)

abstract class Agent {
    val channel = Channel<Massage<*>>()

    /**
     * The entry point for an agent. The agent is destroyed when this function returns.
     */
    abstract suspend fun run()
}

class Environment {
    val agentJobs = mutableListOf<Job>()

    fun launchAgent(agentClass: KClass<out Agent>) {
        agentJobs += launch {
            val agent = agentClass.constructors.first().call()
            agent.run()
        }
    }

    fun await() {
        runBlocking {
            agentJobs.forEach { it.join() }
        }
    }
}

fun main(args: Array<String>) {
    class PrintAgent : Agent() {
        suspend override fun run() {
            println("Hello!")
            while (true) {
                delay(1, TimeUnit.SECONDS)
                println("Foobar")
            }
        }
    }

    val env = Environment()
    env.launchAgent(PrintAgent::class)
    Thread.sleep(1000)
}