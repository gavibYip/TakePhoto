package org.devio.simple.utils


import android.text.TextUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object JsonUtils {

    /**
     * 将java对象转换成json字符串
     *
     * @param obj 待转换的java对象
     * @return
     */
    fun toJson(obj: Any?): String? {
        if (obj == null) {
            return null
        }

        try {
            return Gson().toJson(obj)
        } catch (e: Exception) {
            println("try exception,${e.message}")
        }

        return null
    }

    /**
     * 将json字符串转换成java对象
     *
     * @param json  待转换的json字符串
     * @param clazz java对象的类
     * @return
     */
    fun <T> parseJson2Obj(json: String, clazz: Class<T>): T? {
        if (TextUtils.isEmpty(json)) {
            return null
        }

        try {
            return Gson().fromJson(json, clazz)
        } catch (e: Exception) {
            println("try exception,${e.message}")
        }

        return null
    }

    /**
     * 将list对象转换成json字符串
     *
     * @param list 待转换的list对象
     * @return
     */
    fun parseList2Json(list: List<*>?): String? {
        if (list == null || list.isEmpty()) {
            return null
        }

        try {
            return Gson().toJson(list)
        } catch (e: Exception) {
            println("try exception,${e.message}")
        }

        return null
    }

    /**
     * fromJson2List
     */
    fun <T> fromJson2List(json: String) = fromJson<List<T>>(json)

    /**
     * fromJson
     */
    private fun <T> fromJson(json: String): T? {
        return try {
            val type = object : TypeToken<T>() {}.type
            return Gson().fromJson(json, type)
        } catch (e: Exception) {
            println("try exception,${e.message}")
            null
        }
    }


    /**
     * 将json字符串转为Map对象
     *
     * @param json 待转换的json字符串
     * @return
     */
    fun parseJson2StringMap(json: String): HashMap<String, String>? {
        if (TextUtils.isEmpty(json)) {
            return null
        }

        try {
            val hashMap = HashMap<String, String>()
            val fromJson = Gson().fromJson(json, hashMap.javaClass)
            val entries = fromJson.entries.iterator()
            while (entries.hasNext()) {
                val entry = entries.next()
                hashMap[entry.key] = entry.value
            }

            return hashMap
        } catch (e: Exception) {
            println("try exception,${e.message}")
        }

        return null
    }

    /**
     * 将json字符串转为Map对象
     *
     * @param json 待转换的json字符串
     * @param <K>
     * @param <V>
     * @return
    </V></K> */
    fun <K, V> parseJson2Map(json: String): HashMap<K, V>? {
        if (TextUtils.isEmpty(json)) {
            return null
        }

        try {
            val hashMap = HashMap<K, V>()
            val fromJson = Gson().fromJson(json, hashMap.javaClass)
            val entries = fromJson.entries.iterator()
            while (entries.hasNext()) {
                val entry = entries.next()
                hashMap[entry.key] = entry.value
            }

            return hashMap
        } catch (e: Exception) {
            println("try exception,${e.message}")
        }

        return null
    }
}
