package com.padc.padcx_myhealthcare_monthly_assignment.activities

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TimePicker
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.padc.padcx_myhealthcare_monthly_assignment.R
import com.padc.padcx_myhealthcare_monthly_assignment.adapter.ConsultationAcceptAdapter
import com.padc.padcx_myhealthcare_monthly_assignment.adapter.ConsultationRequestAdapter
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.dialog.PatientDialogFragment
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.dialog.PatientDialogFragment.Companion.BITMAP_IMAGE
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.dialog.PatientDialogFragment.Companion.BUNDLE_BIRTH_DATE
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.dialog.PatientDialogFragment.Companion.BUNDLE_NAME
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.presenter.MainPresenter
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.presenter.impls.MainPresenterImpl
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.view.MainView
import com.padc.padcx_myhealthcare_monthly_assignment.utils.SessionManager
import com.padc.share.activities.BaseActivity
import com.padc.share.data.vos.ConsultationChatVO
import com.padc.share.data.vos.ConsultationRequestVO
import com.padc.share.data.vos.ConsultedPatientVO
import com.padc.share.data.vos.PatientVO
import com.padc.share.services.FCMService
import com.padc.share.utils.ImageUtils
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_patient_dialog.*
import kotlinx.android.synthetic.main.fragment_patient_dialog.iv_patient
import kotlinx.android.synthetic.main.fragment_patient_dialog.tv_patientBirthDate
import kotlinx.android.synthetic.main.fragment_patient_dialog.tv_patientName
import kotlinx.android.synthetic.main.layout_header.*
import kotlinx.android.synthetic.main.list_item_consultation_request_item.*
import kotlinx.android.synthetic.main.postpone_dialog.view.*

class MainActivity : BaseActivity() ,MainView{

    companion object {

        private const val ID_EXTRA = "ID_EXTRA"
        fun newIntent(context: Context,patientID : String) : Intent {
            val intent = Intent(context,
                MainActivity::class.java)
            intent.putExtra(ID_EXTRA,patientID)
            return intent
        }
    }

    private lateinit var mMainPresenter : MainPresenter
    private lateinit var mConsultationRequestAdapter : ConsultationRequestAdapter
    private lateinit var mConsultationAcceptAdapter : ConsultationAcceptAdapter

    private lateinit var mConsultationRequestVO : ConsultationRequestVO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getFirebaseInstanceID()

        setUpPresenter()
        setUpRecyclerView()
        setUpActionListener()

        mMainPresenter.onUiReady(this,intent.getStringExtra(ID_EXTRA).toString())

        tv_doctorName.text = SessionManager.doctor_name
        ImageUtils().showImage(iv_doctor_top, SessionManager.doctor_photo.toString(),R.drawable.user)
    }

    private fun setUpRecyclerView() {
        rv_consultationRequest.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        mConsultationRequestAdapter = ConsultationRequestAdapter (mMainPresenter)
        rv_consultationRequest.adapter = mConsultationRequestAdapter

        rv_consulation_accept.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mConsultationAcceptAdapter = ConsultationAcceptAdapter (mMainPresenter)
        rv_consulation_accept.adapter = mConsultationAcceptAdapter
    }



    private fun setUpActionListener() {


        iv_setting.setOnClickListener {
            mMainPresenter.onTapSetting()
        }
    }



    private fun setUpPresenter() {
        mMainPresenter = ViewModelProviders.of(this).get(MainPresenterImpl::class.java)
        mMainPresenter.initPresenter(this)
    }

    override fun showPatientDialog(patientVO: PatientVO) {
        val patientDialog = PatientDialogFragment.newFragment()
        val bundle = Bundle()

        bundle.putString(BUNDLE_NAME, patientVO.name)
        bundle.putString(BITMAP_IMAGE, patientVO.photo)
        bundle.putString(BUNDLE_BIRTH_DATE, patientVO.dateOfBirth)

        patientDialog.arguments = bundle
        this?.supportFragmentManager?.let { patientDialog.show(it,PatientDialogFragment.TAG_ADD_PATIENT_DIALOG) }
    }

    override fun displayConsultationRequestLists(list: List<ConsultationRequestVO>) {
        Log.d("RequestList",list.size.toString())
       mConsultationRequestAdapter.setNewData(list.toMutableList())

    }

    override fun displayConsultationRequestPatient(consultationRequestVO: ConsultationRequestVO) {
        mConsultationRequestVO = consultationRequestVO
        cardView.visibility = View.VISIBLE

        tv_patientName.text = consultationRequestVO?.patient_info?.name
        tv_patientBirthDate.text = consultationRequestVO?.patient_info?.dateOfBirth
        ImageUtils().showImage(
            iv_patient,
            consultationRequestVO?.patient_info?.photo.toString(),
            R.drawable.doctor_img
        )
    }

    override fun navigateToSettingScreen() {
        startActivity(ProfileActivity.newIntent(this))

    }

    override fun displayPostPoneDialog(consultationRequestVO: ConsultationRequestVO) {
        val view = layoutInflater.inflate(R.layout.postpone_dialog, null)
        val dialog = this?.let { Dialog(it) }
        val timePicker = view?.findViewById<TimePicker>(R.id.timePicker)
        var msg : String =""
        timePicker?.setOnTimeChangedListener { _, hour, minute -> var hour = hour
            var am_pm = ""
            when {hour == 0 -> { hour += 12
                am_pm = "AM"
            }
                hour == 12 -> am_pm = "PM"
                hour > 12 -> { hour -= 12
                    am_pm = "PM"
                }
                else -> am_pm = "AM"
            }

            val h = if (hour < 10) "0$hour" else hour
            val min = if (minute < 10) "0$minute" else minute
            msg = " $h : $min $am_pm"

        }
        dialog?.apply {
            setCancelable(true)
            setContentView(view)
            window?.setBackgroundDrawableResource(android.R.color.transparent)
        }

        view.confirm.setOnClickListener {

            dialog?.dismiss()
        }
        dialog?.show()
    }

    override fun displayConsultationAcceptList(list: List<ConsultationChatVO>) {
        Log.d("ConsultedAcceptLists",list.size.toString())
        mConsultationAcceptAdapter.setNewData(list.toMutableList())

        if(list?.size == 0) {
            empty_view.visibility =View.VISIBLE
            tv_consultationRecord.visibility = View.GONE
        }else
        {
            empty_view.visibility =View.GONE
            tv_consultationRecord.visibility = View.VISIBLE
        }


    }

    override fun navigateToPatientCaseSummary(consultation_request_id: String) {
        SessionManager.consultation_chat_id = consultation_request_id
        startActivity(PatientCaseSummaryActivity.newIntent(this,consultation_request_id))
    }


    override fun displayConsultedPatient(list: List<ConsultedPatientVO>) {
        Log.d("ConsultedPatientLists",list.size.toString())
        mConsultationRequestAdapter.setConsultedPatientList(list.toMutableList())
    }

    override fun navigateToChatScreen(consultation_chat_id: String) {
       startActivity(ChatActivity.newIntent(this,consultation_chat_id))
    }




    override fun showErrorMessage(errorMessage: String) {
      showSnackbar(errorMessage)
    }

    override fun getLifeCycleOwner(): LifecycleOwner  = this


}