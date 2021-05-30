package com.mads2202.android_1_hw_2.model

import java.util.*

data class Note(var title: String, var description: String) {
    var id: UUID
    var creationDate: Date

    init {
        creationDate = Date()
        id = UUID.randomUUID()
    }
}