package com.padc.patient.views.viewHolders

import android.content.Context
import android.graphics.Color
import android.text.Editable
import android.view.MotionEvent
import android.view.View
import com.google.api.ResourceProto.resource
import com.padc.patient.R
import com.padc.patient.delegates.PatientAddressItemDelegate
import com.padc.share.data.vos.AddressVO
import com.padc.share.views.viewHolder.BaseViewHolder
import kotlinx.android.synthetic.main.activity_order_prescription.view.*
import kotlinx.android.synthetic.main.list_item_patient_adderss.view.*


class PatientAddressViewHolder(var previousPosition : Int,private val mDelegate: PatientAddressItemDelegate,itemView: View) :
    BaseViewHolder<AddressVO>(itemView) {
    override fun clickItem(it: View?) {
        mData?.let {

        }
    }

    override fun bindData(data: AddressVO) {
        mData = data

        data?.let {
            itemView.ed_patient_address.text =
                Editable.Factory.getInstance().newEditable(data.fullAddress)
        }

        if (adapterPosition == previousPosition){
            itemView.card_address.setBackgroundColor(Color.BLUE)
        }

        itemView.ed_patient_address.setOnClickListener {
            mDelegate.onTapAddress(data,adapterPosition)
        }







    }
}