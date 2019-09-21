package com.gapsi.productsearcher.data.entites

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "words")
class Word(
    @PrimaryKey
    @NonNull
    var name: String = ""
)