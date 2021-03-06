package com.padc.patient.mvp.presenter

import androidx.lifecycle.LifecycleOwner
import com.padc.patient.mvp.view.CaseSummaryView
import com.padc.share.mvp.presenter.BasePresenter

interface CaseSummaryPresenter : BasePresenter<CaseSummaryView> {
 fun onUIReady(lifecycleOwner: LifecycleOwner,patientID : String)
    fun onTapContinue(specialityName : String,doctorEmail : String)
}