package org.devio.simple.bean

import android.os.Parcel
import android.os.Parcelable


data class Data(
    val content: String,
    val qrCodeBase64: String,
    val qrCodeUrl: String,
    val type: Int
) : BaseBean() {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        super.writeToParcel(parcel, flags)
        parcel.writeString(content)
        parcel.writeString(qrCodeBase64)
        parcel.writeString(qrCodeUrl)
        parcel.writeInt(type)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "Data(content='$content', qrCodeBase64='$qrCodeBase64', qrCodeUrl='$qrCodeUrl', type=$type)"
    }

    companion object CREATOR : Parcelable.Creator<Data> {
        override fun createFromParcel(parcel: Parcel): Data {
            return Data(parcel)
        }

        override fun newArray(size: Int): Array<Data?> {
            return arrayOfNulls(size)
        }
    }
}