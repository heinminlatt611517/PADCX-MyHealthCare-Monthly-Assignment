package com.padc.share.presistence.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.padc.share.data.vos.ConsultationChatVO


@Dao
interface ConsultationChatDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertConsultationChat(consultationChatVO: ConsultationChatVO)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertConsultationChatData(consultationChatList: List<ConsultationChatVO>)

    @Query("select * from consultation_chat")
    fun getAllConsultationChatData(): LiveData<List<ConsultationChatVO>>

    @Query("select * from consultation_chat WHERE doctor_id = :id")
    fun getAllConsultationChatDataByDoctorId(id: String): LiveData<List<ConsultationChatVO>>

    @Query("select * from consultation_chat WHERE id = :id")
    fun getAllConsultationChatDataBy(id: String): LiveData<ConsultationChatVO>

    @Query("DELETE FROM consultation_chat")
    fun deleteAllConsultationChatData()

}