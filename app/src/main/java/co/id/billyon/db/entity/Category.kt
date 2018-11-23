package co.id.billyon.db.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import co.id.billyon.util.Converters

@Entity(tableName = "category")
data class Category(
        @ColumnInfo(name = "category_name")
        var categoryName: String,

        @ColumnInfo(name = "image_path")
        var categoryImagePath : String,

        @ColumnInfo(name = "store_id")
        var storeId: Long,

        @TypeConverters(Converters::class)
        @ColumnInfo(name = "is_active")
        var isActive: Boolean,

        @TypeConverters(Converters::class)
        @ColumnInfo(name = "need_synced")
        var need_synced: Boolean,

        var created_date: String,

        var updated_date: String

) {
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        var id: Long = 0
}