package com.anies.agent

import java.util.*

data class EntityId(private val uuid: UUID)
data class Massage<out T>(val content: T, val sender: EntityId, val recipient: EntityId)

abstract class Agent {
    /**
     * The entry point for an agent.
     * The agent is destroyed when this function returns.
     */
    abstract suspend fun run()
}