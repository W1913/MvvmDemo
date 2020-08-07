package com.kaola.home

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder


/**
 * @author wjh
 * on      : 2020/7/29
 * description　:
 */
class HomeChildAdapter(data: MutableList<String>?) :
    BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_home, data) {

    override fun convert(holder: BaseViewHolder, item: String) {
        holder.setText(R.id.name, item)
    }

}