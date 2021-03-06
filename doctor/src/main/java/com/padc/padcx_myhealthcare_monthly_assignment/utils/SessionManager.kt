package com.padc.padcx_myhealthcare_monthly_assignment.utils

import android.content.Context
import android.content.SharedPreferences
import com.padc.share.utils.*


object SessionManager {

    private const val NAME = sharePreferenceDoctor
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences


    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    var login_status: Boolean
        get() = preferences.getBoolean(sharePreferenceDocotrLoginStatus, false)
        set(value) = preferences.edit {
            it.putBoolean(sharePreferenceDocotrLoginStatus, value)
        }

    var doctor_name: String?

        get() = preferences.getString(sharePreferenceDoctortName, "")

        set(value) = preferences.edit {
            it.putString(sharePreferenceDoctortName, value)
        }

    var doctor_email: String?

        get() = preferences.getString(sharePreferenceDoctorEmail, "")

        set(value) = preferences.edit {
            it.putString(sharePreferenceDoctorEmail, value)
        }

    var doctor_id: String?

        get() = preferences.getString(sharePreferenceDoctorID, "")

        set(value) = preferences.edit {
            it.putString(sharePreferenceDoctorID, value)
        }

    var doctor_device_id: String?

        get() = preferences.getString(sharePreferenceDoctorDeviceID, "")

        set(value) = preferences.edit {
            it.putString(sharePreferenceDoctorDeviceID, value)
        }

    var doctor_degree: String?

        get() = preferences.getString(sharePreferenceDoctorDegree, "")

        set(value) = preferences.edit {
            it.putString(sharePreferenceDoctorDegree, value)
        }

    var doctor_bigraphy: String?

        get() = preferences.getString(sharePreferenceDoctorBiography, "")

        set(value) = preferences.edit {
            it.putString(sharePreferenceDoctorBiography, value)
        }

    var doctor_photo : String?

        get() = preferences.getString(sharePreferenceDoctorPhoto, "")

        set(value) = preferences.edit {
            it.putString(sharePreferenceDoctorPhoto, value)
        }

    var doctor_speciality : String?

        get() = preferences.getString(sharePreferenceDoctorSpeciality, "")

        set(value) = preferences.edit {
            it.putString(sharePreferenceDoctorSpeciality, value)
        }

    var doctor_specialityname : String?

        get() = preferences.getString(sharePreferenceDoctorSpecialityName, "")

        set(value) = preferences.edit {
            it.putString(sharePreferenceDoctorSpecialityName, value)
        }

    var doctor_phone : String?

        get() = preferences.getString(sharePreferenceDoctorPhone, "")

        set(value) = preferences.edit {
            it.putString(sharePreferenceDoctorPhone, value)
        }

    var consultation_chat_id : String?

        get() = preferences.getString(sharePreferenceConsultationID, "")

        set(value) = preferences.edit {
            it.putString(sharePreferenceConsultationID, value)
        }

    var doctor_dateofBirth : String?

        get() = preferences.getString(sharePreferenceDoctorDATEOFBIRTH, "")

        set(value) = preferences.edit {
            it.putString(sharePreferenceDoctorDATEOFBIRTH, value)
        }

    var doctor_experience : String?

        get() = preferences.getString(sharePreferenceDoctorEXPERIENCE, "")

        set(value) = preferences.edit {
            it.putString(sharePreferenceDoctorEXPERIENCE, value)
        }

    var doctor_address : String?

        get() = preferences.getString(sharePreferenceDoctorAdress, "")

        set(value) = preferences.edit {
            it.putString(sharePreferenceDoctorAdress, value)
        }

    var doctor_gender : String?

        get() = preferences.getString(sharePreferenceDoctorGENDER, "")

        set(value) = preferences.edit {
            it.putString(sharePreferenceDoctorGENDER, value)
        }

}