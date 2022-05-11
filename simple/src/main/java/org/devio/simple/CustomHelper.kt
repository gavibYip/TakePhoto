package org.devio.simple

import android.net.Uri
import android.view.View
import android.widget.EditText
import android.widget.RadioGroup
import org.devio.takephoto.app.TakePhoto
import org.devio.takephoto.compress.CompressConfig
import org.devio.takephoto.model.CropOptions
import org.devio.takephoto.model.LubanOptions
import org.devio.takephoto.model.TakePhotoOptions
import java.io.File

/**
 * - 支持通过相机拍照获取图片
 * - 支持从相册选择图片
 * - 支持从文件选择图片
 * - 支持多图选择
 * - 支持批量图片裁切
 * - 支持批量图片压缩
 * - 支持对图片进行压缩
 * - 支持对图片进行裁剪
 * - 支持对裁剪及压缩参数自定义
 * - 提供自带裁剪工具(可选)
 * - 支持智能选取及裁剪异常处理
 * - 支持因拍照Activity被回收后的自动恢复
 * Author: crazycodeboy
 * Date: 2016/9/21 0007 20:10
 * Version:4.0.0
 * 技术博文：http://www.devio.org
 * GitHub:https://github.com/crazycodeboy
 * Email:crazycodeboy@gmail.com
 */
class CustomHelper private constructor(private val rootView: View) {
    private var rgCrop: RadioGroup? = null
    private var rgCompress: RadioGroup? = null
    private var rgFrom: RadioGroup? = null
    private var rgCropSize: RadioGroup? = null
    private var rgCropTool: RadioGroup? = null
    private var rgShowProgressBar: RadioGroup? = null
    private var rgPickTool: RadioGroup? = null
    private var rgCompressTool: RadioGroup? = null
    private var rgCorrectTool: RadioGroup? = null
    private var rgRawFile: RadioGroup? = null
    private var etCropHeight: EditText? = null
    private var etCropWidth: EditText? = null
    private var etLimit: EditText? = null
    private var etSize: EditText? = null
    private var etHeightPx: EditText? = null
    private var etWidthPx: EditText? = null
    private fun init() {
        rgCrop = rootView.findViewById<View>(R.id.rgCrop) as RadioGroup
        rgCompress = rootView.findViewById<View>(R.id.rgCompress) as RadioGroup
        rgCompressTool = rootView.findViewById<View>(R.id.rgCompressTool) as RadioGroup
        rgCropSize = rootView.findViewById<View>(R.id.rgCropSize) as RadioGroup
        rgFrom = rootView.findViewById<View>(R.id.rgFrom) as RadioGroup
        rgPickTool = rootView.findViewById<View>(R.id.rgPickTool) as RadioGroup
        rgRawFile = rootView.findViewById<View>(R.id.rgRawFile) as RadioGroup
        rgCorrectTool = rootView.findViewById<View>(R.id.rgCorrectTool) as RadioGroup
        rgShowProgressBar = rootView.findViewById<View>(R.id.rgShowProgressBar) as RadioGroup
        rgCropTool = rootView.findViewById<View>(R.id.rgCropTool) as RadioGroup
        etCropHeight = rootView.findViewById<View>(R.id.etCropHeight) as EditText
        etCropWidth = rootView.findViewById<View>(R.id.etCropWidth) as EditText
        etLimit = rootView.findViewById<View>(R.id.etLimit) as EditText
        etSize = rootView.findViewById<View>(R.id.etSize) as EditText
        etHeightPx = rootView.findViewById<View>(R.id.etHeightPx) as EditText
        etWidthPx = rootView.findViewById<View>(R.id.etWidthPx) as EditText
    }

    fun onClick(view: View, takePhoto: TakePhoto) {
        //适配Android 11
        var root = File(
            rootView.context.getExternalFilesDir(null),
            "/temp/" + System.currentTimeMillis() + ".jpg"
        )
        if (root == null) {
            root = File(
                rootView.context.filesDir, "/temp/" + System.currentTimeMillis() + ".jpg"
            )
        }
        if (!root.parentFile.exists()) {
            root.parentFile.mkdirs()
        }
        val imageUri = Uri.fromFile(root)
        configCompress(takePhoto)
        configTakePhotoOption(takePhoto)
        when (view.id) {
            R.id.btnPickBySelect -> {
                val limit = etLimit!!.text.toString().toInt()
                if (limit > 1) {
                    if (rgCrop!!.checkedRadioButtonId == R.id.rbCropYes) {
                        takePhoto.onPickMultipleWithCrop(limit, cropOptions)
                    } else {
                        takePhoto.onPickMultiple(limit)
                    }
                    return
                }
                if (rgFrom!!.checkedRadioButtonId == R.id.rbFile) {
                    if (rgCrop!!.checkedRadioButtonId == R.id.rbCropYes) {
                        takePhoto.onPickFromDocumentsWithCrop(imageUri, cropOptions)
                    } else {
                        takePhoto.onPickFromDocuments()
                    }
                    return
                } else {
                    if (rgCrop!!.checkedRadioButtonId == R.id.rbCropYes) {
                        takePhoto.onPickFromGalleryWithCrop(imageUri, cropOptions)
                    } else {
                        takePhoto.onPickFromGallery()
                    }
                }
            }
            R.id.btnPickByTake -> if (rgCrop!!.checkedRadioButtonId == R.id.rbCropYes) {
                takePhoto.onPickFromCaptureWithCrop(imageUri, cropOptions)
            } else {
                takePhoto.onPickFromCapture(imageUri)
            }
            else -> {}
        }
    }

    private fun configTakePhotoOption(takePhoto: TakePhoto) {
        val builder = TakePhotoOptions.Builder()
        if (rgPickTool!!.checkedRadioButtonId == R.id.rbPickWithOwn) {
            builder.setWithOwnGallery(true)
        }
        if (rgCorrectTool!!.checkedRadioButtonId == R.id.rbCorrectYes) {
            builder.setCorrectImage(true)
        }
        takePhoto.setTakePhotoOptions(builder.create())
    }

    private fun configCompress(takePhoto: TakePhoto) {
        if (rgCompress!!.checkedRadioButtonId != R.id.rbCompressYes) {
            takePhoto.onEnableCompress(null, false)
            return
        }
        val maxSize = etSize!!.text.toString().toInt()
        val width = etCropWidth!!.text.toString().toInt()
        val height = etHeightPx!!.text.toString().toInt()
        val showProgressBar =
            rgShowProgressBar!!.checkedRadioButtonId == R.id.rbShowYes
        val enableRawFile = rgRawFile!!.checkedRadioButtonId == R.id.rbRawYes
        val config: CompressConfig
        if (rgCompressTool!!.checkedRadioButtonId == R.id.rbCompressWithOwn) {
            config = CompressConfig.Builder().setMaxSize(maxSize)
                .setMaxPixel(if (width >= height) width else height)
                .enableReserveRaw(enableRawFile)
                .create()
        } else {
            val option =
                LubanOptions.Builder().setMaxHeight(height).setMaxWidth(width).setMaxSize(maxSize)
                    .create()
            config = CompressConfig.ofLuban(option)
            config.enableReserveRaw(enableRawFile)
        }
        takePhoto.onEnableCompress(config, showProgressBar)
    }

    private val cropOptions: CropOptions?
        get() {
            if (rgCrop!!.checkedRadioButtonId != R.id.rbCropYes) {
                return null
            }
            val height = etCropHeight!!.text.toString().toInt()
            val width = etCropWidth!!.text.toString().toInt()
            val withWonCrop =
                rgCropTool!!.checkedRadioButtonId == R.id.rbCropOwn
            val builder = CropOptions.Builder()
            if (rgCropSize!!.checkedRadioButtonId == R.id.rbAspect) {
                builder.setAspectX(width).setAspectY(height)
            } else {
                builder.setOutputX(width).setOutputY(height)
            }
            builder.setWithOwnCrop(withWonCrop)
            return builder.create()
        }

    companion object {
        @JvmStatic
        fun of(rootView: View): CustomHelper {
            return CustomHelper(rootView)
        }
    }

    init {
        init()
    }
}