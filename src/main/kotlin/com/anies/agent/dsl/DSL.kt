package com.anies.agent.dsl

import com.anies.agent.Agent
import com.anies.agent.Environment
import kotlin.reflect.KClass

fun environment(block: EnvironmentElement.() -> Unit) : EnvironmentElement {
    val envElement = EnvironmentElement()
    envElement.block()
    return envElement
}

class EnvironmentElement {
    val agents = mutableListOf<KClass<out Agent>>()

    fun agent(agentClass: KClass<out Agent>) {
        agents += agentClass
    }

    suspend fun run() {
        val env = Environment()
        for (agent in agents) {
            env.runAgent(agent)
        }
    }
}