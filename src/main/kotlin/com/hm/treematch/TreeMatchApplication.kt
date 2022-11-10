package com.hm.treematch

import com.hm.treematch.api.dao.JsonFileDataDao
import com.hm.treematch.api.dao.TreeMatchDao
import com.hm.treematch.api.util.treeMatchObjectMapper
import com.hm.treematch.api.web.service.TreeMatchServiceImpl
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.web.filter.CommonsRequestLoggingFilter

@SpringBootApplication
class TreeMatchApplication {

    @Bean
    fun requestLoggingFilter() = CommonsRequestLoggingFilter()

    @Bean
    @Primary
    fun objectMapper()= treeMatchObjectMapper()

    @Bean
    fun treeMatchDao() : TreeMatchDao = JsonFileDataDao(objectMapper())
    @Bean
    fun getTreeMatchService() = TreeMatchServiceImpl(treeMatchDao())
}

fun main(args: Array<String>) {
    runApplication<TreeMatchApplication>(*args)
}
