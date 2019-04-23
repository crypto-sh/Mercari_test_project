package com.mrecun.model

import android.os.Parcel
import android.os.Parcelable

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
data class PageModel(val name : String, val data : String) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    )
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(data)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PageModel> {
        override fun createFromParcel(parcel: Parcel): PageModel {
            return PageModel(parcel)
        }

        override fun newArray(size: Int): Array<PageModel?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        return "name : $name, data : $data"
    }
}