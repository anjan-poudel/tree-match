package com.hm.treematch.api.dao

import com.hm.treematch.api.model.*

/**
 * using a single dao definition instead of one per entity to keep it simple
 */
interface TreeMatchDao {

    fun getStep(id: StepId): Step

    fun getQuestion(id: QuestionId): Question

    fun getResult(id: ResultId): TreeMatchResult

}