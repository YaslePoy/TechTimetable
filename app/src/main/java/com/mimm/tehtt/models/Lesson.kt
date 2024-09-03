package com.mimm.tehtt.models

import kotlinx.serialization.Serializable

@Serializable
data class Lesson(val name: String, val room: String, val teacher: String, val number: Int)