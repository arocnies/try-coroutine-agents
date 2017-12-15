package com.anies.agent

import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.launch
import kotlin.reflect.KClass

class Environment {
    val agentJobs = mutableListOf<Job>()

    /**
     * Runs a new agent within a coroutine.
     * Agents are constructed using their first constructor.
     */
    fun runAgent(agentClass: KClass<out Agent>) {
        agentJobs += launch {
            val agent = agentClass.constructors.first().call()
            agent.run()
            // Agent returned from run() is no longer needed.
        }
    }

    suspend fun join() = agentJobs.forEach { it.join() }
}
