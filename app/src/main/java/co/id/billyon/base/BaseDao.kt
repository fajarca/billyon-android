package co.id.billyon.base

import androidx.room.*

interface BaseDao<T> {

    /**
     * Insert an object in the database.
     *
     * @param entity the object to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: T)


    /**
     * Insert an array of objects in the database.
     *
     * @param entity the objects to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg entity: T)

    /**
     * Update an object from the database.
     *
     * @param entity the object to be updated
     */
    @Update
    fun update(entity: T)

    /**
     * Delete an object from the database
     *
     * @param entity the object to be deleted
     */

    @Delete
    fun delete(entity: T)
}