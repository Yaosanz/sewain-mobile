package com.sewain.mobileapp.data.local.room

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.sewain.mobileapp.data.local.entity.CatalogEntity
@Dao
interface CatalogDao {
    @Query("SELECT * FROM catalog")
    fun getCatalogs(): LiveData<List<CatalogEntity>>

    @Query("SELECT * FROM catalog")
    fun getAllCatalogs(): PagingSource<Int, CatalogEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCatalog(user: List<CatalogEntity>)

    @Update
    fun updateCatalog(user: CatalogEntity)

    @Query("DELETE FROM catalog")
    fun deleteAll()
}