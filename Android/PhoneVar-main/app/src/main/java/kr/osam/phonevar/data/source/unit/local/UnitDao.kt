package kr.osam.phonevar.data.source.unit.local

import androidx.room.*
import kr.osam.phonevar.data.UnitItem

@Dao interface UnitDao {
    @Query("SELECT * FROM Units") fun getUnits(): List<UnitItem>

    @Query("SELECT * FROM Units WHERE isDeleted = 0") fun getValidUnits(): List<UnitItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE) fun insertUnit(unitItem: UnitItem)

    @Update
    fun updateUnit(unitItem: UnitItem): Int

    @Query("UPDATE Units SET isDeleted = :isDeleted WHERE id = :id")
    fun updateDeleted(id: String, isDeleted : Boolean)

    @Query("DELETE FROM Units") fun deleteAllUnits()
}