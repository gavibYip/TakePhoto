package org.devio.simple.bean

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

// 用于处理 Lint 的错误提示
@SuppressLint("ParcelCreator")
@Parcelize
open class BaseBean : Parcelable