package org.devio.simple

import org.devio.simple.utils.JsonUtils.toJson
import org.devio.simple.utils.JsonUtils.parseJson2Obj
import org.devio.simple.utils.JsonUtils.parseList2Json
import org.devio.simple.utils.JsonUtils.fromJson2List
import org.devio.simple.utils.JsonUtils.parseJson2StringMap
import org.devio.simple.utils.JsonUtils.parseJson2Map
import android.app.Activity
import android.os.Bundle
import org.devio.simple.R
import org.devio.simple.utils.JsonUtils
import com.elvishew.xlog.XLog
import android.content.Intent
import android.view.View
import org.devio.simple.SimpleActivity
import org.devio.simple.SimpleFragmentActivity
import org.devio.simple.bean.Data
import org.devio.simple.bean.Person
import java.util.ArrayList

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
class MainActivity : Activity(), View.OnClickListener {
    private val personList: MutableList<Person?> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_layout)
    }

    override fun onResume() {
        super.onResume()
        val miko = Person("miko", 34, false)
        val mikoString = toJson(miko)
        XLog.d("parseObj2Json:$mikoString")
        val data = parseJson2Obj(
            """{
  "qrCodeUrl": "http://www.mxnzp.com/api_file/qrcode/9/5/1/d/8/e/4/7/e2b815c4fac74f949686a1a041a06dae.png",
  "content": "你好",
  "type": 0,
  "qrCodeBase64": null
}""", Data::class.java
        )
        if (data != null) {
            XLog.d("parseJson2Obj:$data")
        }
        val jack = Person("jack", 12, false)
        val kate = Person("kate", 56, false)
        personList.add(miko)
        personList.add(jack)
        personList.add(kate)
        val parseList2Json = parseList2Json(personList)
        XLog.d("parseList2Json:$parseList2Json")
        if (parseList2Json != null) {
            val people = fromJson2List<Person>(parseList2Json)
            XLog.d("parseJson2List:$people")
        }
        val str = "{\"0\":\"zhangsan\",\"1\":\"lisi\",\"2\":\"wangwu\",\"3\":\"maliu\"}"
        val hashMap = parseJson2StringMap(str)
        if (hashMap != null) {
            XLog.d("parseJson2StringMap:$hashMap")
        }
        val objectObjectHashMap = parseJson2Map<Int, String>(str)
        if (objectObjectHashMap != null) {
            XLog.d("parseJson2Map:$objectObjectHashMap")
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnTakePhotoActivity -> startActivity(Intent(this, SimpleActivity::class.java))
            R.id.btnTakePhotoFragment -> startActivity(
                Intent(
                    this,
                    SimpleFragmentActivity::class.java
                )
            )
            else -> {}
        }
    }
}