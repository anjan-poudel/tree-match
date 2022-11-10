package com.hm.treematch.api.dao

import com.hm.treematch.api.util.treeMatchObjectMapper
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class JsonFileDataDaoTest {

    private val dao = JsonFileDataDao(treeMatchObjectMapper(), "data/test-questions.json")

    @Test
    fun getStep() {
        assertNotNull(dao.getQuestion(1))
        assertThrows(IdNotFound::class.java) { dao.getQuestion(-1) }
    }

    @Test
    fun getQuestion() {
        assertNotNull(dao.getStep(1))
        assertThrows(IdNotFound::class.java) { dao.getStep(-1) }
    }

    @Test
    fun getResult() {
        assertNotNull(dao.getResult(1))
        assertThrows(IdNotFound::class.java) { dao.getResult(-1) }
    }

    @Test
    fun validate() {
        //@todo
    }
}