package com.mrecun.utils

import com.mrecun.model.PageModel

class GenerateResponse {

    companion object {
        fun generateViewPagerResponse(count : Int): List<PageModel> {
            val pages = ArrayList<PageModel>()
            for (item in 1..count) {
                pages.add(PageModel("data$item", "link"))
            }
            return pages
        }
    }


}