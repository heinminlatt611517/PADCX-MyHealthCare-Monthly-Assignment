package com.padc.share.data.vos

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.IgnoreExtraProperties

@Entity(tableName = "Doctor")
@IgnoreExtraProperties
data class DoctorVO (
    @PrimaryKey
    var dr_id: String= "",
    var name: String = "",
    var email: String = "",
    var photo: String? = "",
    var age: Int =0,
    var degree : String? = "",
    var biography: String?= "",
    var address: String? ="",
    var speciality : String? =""
)