package com.sewain.mobileapp.data.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "Catalog")
@Parcelize
data class CatalogEntity(
    @PrimaryKey(autoGenerate = false)
    var id: String = "",
    var name: String? = "",
    var price: Double? = null,
    var dayRent: Int? = null,
    var size: String? = null,
    var photoUrl: String? = null,
) : Parcelable