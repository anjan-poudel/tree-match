package com.hm.treematch.api.util

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

fun treeMatchObjectMapper(): ObjectMapper {
    return jacksonObjectMapper()
        .setPropertyNamingStrategy( PropertyNamingStrategies.SNAKE_CASE)
}
