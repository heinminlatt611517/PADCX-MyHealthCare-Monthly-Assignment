package com.padc.share.data.vos

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.IgnoreExtraProperties

@Entity(tableName = "special_question")
@IgnoreExtraProperties
data class SpecialQuestionVO(
    @PrimaryKey
    var id: String= "",
    var name: String? = ""

)