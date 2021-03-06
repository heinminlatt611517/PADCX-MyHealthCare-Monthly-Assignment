package com.padc.share.data.models.impls

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import com.padc.share.data.models.BaseModel
import com.padc.share.data.models.DoctorModel
import com.padc.share.data.vos.*
import com.padc.share.networks.CloudFireStoreFirebaseApiImpl
import com.padc.share.networks.FirebaseApi
import com.padc.share.networks.auth.AuthManager
import com.padc.share.networks.auth.FirebaseAuthManager

object DoctorModelImpl : DoctorModel, BaseModel() {

    override var mFirebaseApi: FirebaseApi = CloudFireStoreFirebaseApiImpl
    override var mAuthManager: AuthManager = FirebaseAuthManager

    override fun uploadPhotoToFirebaseStorage(
        image: Bitmap,
        onSuccess: (photoUrl: String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.uploadPhotoToFirebaseStorage(image ,onSuccess,onFailure)
    }

    override fun registerNewDoctor(
        doctorVO: DoctorVO,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {

        mFirebaseApi.addNewDoctor(
            doctorVO,
            onSuccess = {
                onSuccess()
            },
            onFailure = { onFailure(it) })
    }




    override fun getDoctorFromFirebaseAndSaveToDatabase(
        onSuccess: (doctorList: List<DoctorVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.getDoctor(onSuccess = {
            mTheDB.doctorDao().insertDoctorList(it)
        },onFailure = {

        })
    }

    override fun getPatientByID(
        patientID: String,
        onSuccess: (patientVO: PatientVO) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.getPatientByID(patientID,onSuccess,onFailure)
    }

    override fun getBroadConsultationRequest(
        consultation_request_id: String,
        onSuccess: (consultationRequest: ConsultationRequestVO) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.getBroadConsultationRequest(consultation_request_id,onSuccess,onFailure)
    }

    override fun getDoctorByEmail(
        email: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        mFirebaseApi.getDoctorByEmail(email,
            onSuccess = {
                mTheDB.doctorDao().deleteAllDoctorData()
                mTheDB.doctorDao().insertNewDoctor(it)
            }, onFailure = { onError(it) })
    }

    override fun getDoctorByEmailFromDB(email: String): LiveData<DoctorVO> {
        return mTheDB.doctorDao().getAllDoctorDataByEmail(email)
    }

    override fun getAllChatMessage(
        consultationID: String,
        onSuccess: (messages: List<ChatMessageVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        PatientModelImpl.mFirebaseApi.getAllChatMessage(consultationID,onSuccess,onFailure)
    }

    override fun sendMessage(
        consultationChatId: String,
        messageVO: ChatMessageVO,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        PatientModelImpl.mFirebaseApi.sendMessage(consultationChatId,onSuccess = {
            onSuccess()
        },onFailure = {
            onFailure(it)
        },chatMessageVO = messageVO)
    }

    override fun startConsultation(
        consultationId: String,
        dateTime: String,
        questionAnswerList: List<QuestionAnswerVO>,
        patientVO: PatientVO,
        doctorVO: DoctorVO,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.startConsultation(consultationId, dateTime, questionAnswerList, patientVO, doctorVO,
            onSuccess = {}, onFailure = { onFailure(it) })
    }

    override fun acceptRequest(
        status: String,
        consultationId: String,
        questionAnswerList: List<QuestionAnswerVO>,
        patientVO: PatientVO,
        doctorVO: DoctorVO,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.acceptRequest(status,consultationId,  questionAnswerList, patientVO, doctorVO,
            onSuccess = {}, onFailure = { onFailure(it) })
    }

    override fun getConsultedPatient(
        doctorId: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        mFirebaseApi.getConsultationPatient(doctorId,onSuccess = {
            mTheDB.consultedPatientDao().deleteConsultedPatient()
            mTheDB.consultedPatientDao().insertConsultedPatient(it)
        }, onFailure= {})
    }

    override fun getConsultationPatient(doctorId: String, onSuccess: (List<ConsultedPatientVO>) -> Unit, onFailure: (String) -> Unit) {
        mFirebaseApi.getConsultationPatient(doctorId,onSuccess,onFailure)
    }

    override fun getConsultedPatientFromDB(): LiveData<List<ConsultedPatientVO>> {
        return mTheDB.consultedPatientDao().getConsultedPatient()
    }

    override fun getConsultedPatientFromDB(doctorId: String): LiveData<List<ConsultedPatientVO>> {
        return mTheDB.consultedPatientDao().getConsultedPatient()
    }

    override fun getBrodcastConsultationRequestsFromDB(speciality: String): LiveData<List<ConsultationRequestVO>> {
        return mTheDB.consultationRequestDao().getAllConsultationRequestDataBySpeciality(speciality)
    }

    override fun getBrodcastConsultationRequests(
        speciality: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        mFirebaseApi.getBroadcastConsultationRequestBySpeciality(speciality,
            onSuccess = {
                mTheDB.consultationRequestDao().deleteAllConsultationRequestData()
                mTheDB.consultationRequestDao().insertConsultationRequestData(it)

            }, onFailure = { onError(it) })
    }

    override fun getConsultationByDoctorId(
        doctorId: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        mFirebaseApi.getConsulationChatForDoctor(doctorId,
            onSuccess = {
                mTheDB.consultationChatDao().deleteAllConsultationChatData()
                mTheDB.consultationChatDao().insertConsultationChatData(it)

            }, onFailure = { onError(it) })
    }

    override fun getConsultationByDoctorIdFromDB(doctorId: String): LiveData<List<ConsultationChatVO>> {
        return mTheDB.consultationChatDao().getAllConsultationChatDataByDoctorId(doctorId)
    }

    override fun getMedicineBySpeciality(
        speciality: String,
        onSuccess: (medicine: List<MedicineVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.getMedicineBySpeciality(speciality,onSuccess,onFailure)
    }

    override fun getRelatedQuestionBySpeciality(
        speciality: String,
        onSuccess: (List<RelatedQuestionVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.getRelatedQuestionBySpeciality(speciality,onSuccess,onFailure)
    }

    override fun finishConsultation(
        consultationChatVO: ConsultationChatVO,
        prescriptionList: List<PrescriptionVO>,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        mFirebaseApi.finishConsultation(consultationChatVO,prescriptionList,onSuccess,onError)
    }

    override fun getPrescription(
        consultationId: String,
        onSuccess: (List<PrescriptionVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.getPrescription(consultationId,onSuccess,onFailure)
    }

    override fun getConsultationChatForDoctor(doctorId: String, onSuccess: (List<ConsultationChatVO>) -> Unit, onFailure: (String) -> Unit) {
        mFirebaseApi.getConsulationChatForDoctor(doctorId,onSuccess,onFailure)
    }

    override fun getConsultationByConsulationRequestId(consultation_request_id: String, onSuccess: (consultationRequestVO: ConsultationRequestVO) -> Unit, onError: (String) -> Unit) {
        mFirebaseApi.getBroadcastConsultationRequest(consultation_request_id,
                onSuccess = {
                    mTheDB.consultationRequestDao().deleteAllConsultationRequestData()
                    mTheDB.consultationRequestDao().insertConsultationRequest(it)
                }, onFailure = { onError(it) })
    }

    override fun getConsultationByConsulationRequestIdFromDB(consultation_request_id: String): LiveData<ConsultationRequestVO> {
        return mTheDB.consultationRequestDao().getConsultationRequestByConsultationRequestId(consultation_request_id)
    }

    override fun getBroadcastConsultationRequest(consulation_request_id: String, onSuccess: (consulationRequest: ConsultationRequestVO) -> Unit, onFailure: (String) -> Unit) {
        mFirebaseApi.getBroadConsultationRequest(consulation_request_id,onSuccess,onFailure)
    }

    override fun getConsultationChat(consulationId: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        mFirebaseApi.getConsulationChatById(consulationId,
                onSuccess = {
                    mTheDB.consultationChatDao().deleteAllConsultationChatData()
                    mTheDB.consultationChatDao().insertConsultationChatData(it)
                }, onFailure = { onError(it) })
    }

    override fun getConsultationChatFromDB(consulationId: String): LiveData<ConsultationChatVO> {
        return mTheDB.consultationChatDao().getAllConsultationChatDataBy(consulationId)
    }

    override fun deleteConsultationRequestById(consulationId: String,speciality: String): LiveData<List<ConsultationRequestVO>> {
        mTheDB.consultationRequestDao().deleteAllConsultationRequestDataById(consulationId)
        return mTheDB.consultationRequestDao().getAllConsultationRequestDataBySpeciality("dentist")
    }

    override fun upDateDoctorInfo(doctorVO: DoctorVO, onSuccess: () -> Unit, onError: (String) -> Unit) {
        mFirebaseApi.updateDoctorData(doctorVO, onSuccess = {
            onSuccess()
        }, onFailure = { onError(it) })
    }

    override fun saveMedicalRecord(
        consultationChatVO: ConsultationChatVO,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        mFirebaseApi.saveMedicalRecord(consultationChatVO,onSuccess,onError)
    }


}