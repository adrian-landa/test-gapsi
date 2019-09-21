package com.gapsi.productsearcher.data.dao

import androidx.room.*
import com.gapsi.productsearcher.data.entites.Word

@Dao
interface WordDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(itm: Word)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items: List<Word>)
    @Update
    fun update(itm: Word)

    @Delete
    fun delete(item: Word)

    @Delete
    fun delete(items: List<Word>)

    @Query("SELECT * FROM words")
    fun getAll(): List<Word>

}