package com.padc.share.data.vos

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class ChatMessageVO (
    var id : String = "",
    var sendAt: String= "",
    var messageText: String= "",
    var messageImage: String = "",
    var senderType : SenderTypeVO ? = null
)

@IgnoreExtraProperties
data class SenderTypeVO(
    var id : String = "",
    var name: String = "",
    var photo: String? = ""

)
