package co.id.billyon.db.entity

import androidx.room.*
import co.id.billyon.util.Converters

@Entity(tableName = "carts")
data class Carts(
        //Equals to cashier id
        @ColumnInfo(name = "user_id")
        var userId : Long,
        @TypeConverters(Converters::class)
        @ColumnInfo(name = "is_finished")
        var isFinished : Boolean,
        @TypeConverters(Converters::class)
        @ColumnInfo(name = "need_synced")
        var need_synced: Boolean,
        @ColumnInfo(name = "created_date")
        var createdDate: String,
        @ColumnInfo(name = "updated_date")
        var updatedDate: String


) {
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        var id: Long = 0
}