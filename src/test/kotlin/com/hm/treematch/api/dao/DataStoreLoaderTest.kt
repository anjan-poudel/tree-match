package com.hm.treematch.api.dao

import com.hm.treematch.api.model.QuestionStep
import com.hm.treematch.api.model.ResultStep
import com.hm.treematch.api.util.treeMatchObjectMapper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class DataStoreLoaderTest {

    @Test
    fun `happy path`() {
        val ds = FileDataStoreLoader(treeMatchObjectMapper(), "data/test-questions.json").load()

        Assertions.assertTrue(ds.questions.isNotEmpty())
        Assertions.assertTrue(ds.results.isNotEmpty())
        //basic test to validate both types of steps are processed correctly
        Assertions.assertTrue(ds.steps.filter { it.value is QuestionStep }.isNotEmpty())
        Assertions.assertTrue(ds.steps.filter { it.value is ResultStep }.isNotEmpty())
    }
}