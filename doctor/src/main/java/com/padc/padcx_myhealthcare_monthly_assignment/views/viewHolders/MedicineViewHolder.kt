package com.padc.padcx_myhealthcare_monthly_assignment.views.viewHolders

import android.view.View
import com.padc.padcx_myhealthcare_monthly_assignment.R
import com.padc.padcx_myhealthcare_monthly_assignment.delegate.ConsultationRequestItemDelegate
import com.padc.padcx_myhealthcare_monthly_assignment.delegate.PrescribeMedicineItemDelegate
import com.padc.share.data.vos.*
import com.padc.share.utils.ImageUtils
import com.padc.share.views.viewHolder.BaseViewHolder
import kotlinx.android.synthetic.main.list_item_consultation_request_item.view.*
import kotlinx.android.synthetic.main.list_item_consultation_request_item.view.iv_patient
import kotlinx.android.synthetic.main.list_item_medicine.view.*
import kotlinx.android.synthetic.main.listitem_consultation_accept.view.*
import kotlinx.android.synthetic.main.listitem_question_answer.view.*

class MedicineViewHolder(private val mDelegate : PrescribeMedicineItemDelegate, itemView: View) :
    BaseViewHolder<MedicineVO>(itemView) {
    override fun clickItem(it: View?) {
        mData?.let {

        }
    }
    override fun bindData(data: MedicineVO) {
        mData = data

        data?.let {
             itemView.tv_medicine_name.text = data.name
            if (!data.isSelect){
                itemView.iv_add_medicine.visibility = View.VISIBLE
                itemView.iv_remove_medicine.visibility = View.GONE
            }
            else{
                itemView.iv_add_medicine.visibility = View.GONE
                itemView.iv_remove_medicine.visibility = View.VISIBLE
            }
        }

        itemView.iv_remove_medicine.setOnClickListener {
           mDelegate.onTapRemoveMedicine(data)
        }

        itemView.iv_add_medicine.setOnClickListener {
            mDelegate.onTapAddMedicine(data)
        }
    }
}