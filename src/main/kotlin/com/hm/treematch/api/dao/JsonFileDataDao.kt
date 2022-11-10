package com.hm.treematch.api.dao

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.hm.treematch.api.model.*
import org.springframework.util.ResourceUtils
import java.io.File

class IdNotFound(id: Int, type: String = "") : IllegalArgumentException("$type id $id not found")

/** Internal classes to handle Json File DataStore ****/
/** Representation of DataStore  **/
internal data class DataStore(
    val questions: Map<QuestionId, Question>,
    val steps: Map<StepId, Step>,
    val results: Map<ResultId, TreeMatchResult>
)

class JsonFileDataDao(private val mapper: ObjectMapper, private val filePath: String? = "data/questions.json") :
    TreeMatchDao {

    private val dataStore: DataStore by lazy { FileDataStoreLoader(mapper, filePath).load().validate() }

    override fun getStep(id: StepId): Step = dataStore.steps[id] ?: throw IdNotFound(id)

    override fun getQuestion(id: QuestionId): Question = dataStore.questions[id] ?: throw IdNotFound(id)

    override fun getResult(id: ResultId): TreeMatchResult = dataStore.results[id] ?: throw IdNotFound(id)

    private fun DataStore.validate(): DataStore {
        steps.values.forEach { s ->
            when (s) {
                is QuestionStep -> questions[s.questionId] ?: throw IdNotFound(s.questionId, "question")
                is ResultStep -> results[s.resultId] ?: throw IdNotFound(s.resultId, "result")
                else -> {}
            }
        }
        return this
    }
}


internal class FileDataStoreLoader(
    private val mapper: ObjectMapper,
    private val filePath: String? = "data/questions.json"
) {

    fun load(): DataStore {

        val file: File = ResourceUtils.getFile("classpath:$filePath")
        val json: JsonNode = mapper.readTree(file)

        val questions: Map<Int, Question> = read<List<Question>>(json.get("questions")).associateBy { it.id }
        val results: Map<Int, TreeMatchResult> = read<List<TreeMatchResult>>(json.get("results")).associateBy { it.id }
        val steps: Map<Int, Step> = readSteps(json.get("steps")).associateBy { it.id }

        return DataStore(questions, steps, results)
    }

    private fun readSteps(stepsJson: JsonNode): List<Step> {
        return stepsJson.toList().map {
            when {
                it.has("question_id") -> read<QuestionStep>(it)
                else -> read<ResultStep>(it)
            }
        }
    }

    private inline fun <reified T> read(node: JsonNode): T {
        val parser: JsonParser = mapper.treeAsTokens(node)
        return mapper.readValue(parser)
    }


}

