package io.flowforge.core.dsl

import com.fasterxml.jackson.databind.ObjectMapper

@DslMarker annotation class WorkflowDslMarker

@WorkflowDslMarker
class WorkflowBuilder(private val name: String) {
    private val steps = mutableListOf<Map<String, Any>>()
    var description: String = ""

    fun serviceTask(name: String, impl: String) {
        steps.add(mapOf("type" to "service_task", "name" to name, "implementation" to impl))
    }
    fun userTask(name: String, assignee: String = "") {
        steps.add(mapOf("type" to "user_task", "name" to name, "assignee" to assignee))
    }
    fun gateway(name: String, type: String = "exclusive") {
        steps.add(mapOf("type" to "gateway", "name" to name, "gateway_type" to type))
    }
    fun timer(name: String, duration: String) {
        steps.add(mapOf("type" to "timer", "name" to name, "duration" to duration))
    }

    fun build() = mapOf("name" to name, "description" to description, "steps" to steps)
}

fun workflow(name: String, block: WorkflowBuilder.() -> Unit): Map<String, Any> =
    WorkflowBuilder(name).apply(block).build()

fun Map<String, Any>.toJson(): String = ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this)
