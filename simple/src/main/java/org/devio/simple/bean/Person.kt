package org.devio.simple.bean

import android.os.Parcel
import android.os.Parcelable


data class Person(var name: String, var age: Int, var sex: Boolean) : BaseBean() {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {


        super.writeToParcel(parcel, flags)
        parcel.writeString(name)
        parcel.writeInt(age)
        parcel.writeByte(if (sex) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "Person(name='$name', age=$age, sex=$sex)"
    }

    companion object CREATOR : Parcelable.Creator<Person> {
        override fun createFromParcel(parcel: Parcel): Person {
            return Person(parcel)
        }

        override fun newArray(size: Int): Array<Person?> {
            return arrayOfNulls(size)
        }
    }


}