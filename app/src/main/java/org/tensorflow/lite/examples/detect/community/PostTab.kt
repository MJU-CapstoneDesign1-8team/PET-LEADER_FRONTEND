package org.tensorflow.lite.examples.detect.community

enum class PostTab(val tabName: String) {
    FREE("자유"),
    CARE("관리"),
    WALK("산책"),
    SHOW("자랑"),
    INVALID("invalid");

    companion object {
        fun getTab(tab_name: String): PostTab {
            return when(tab_name) {
                FREE.tabName -> FREE
                CARE.tabName -> CARE
                WALK.tabName -> WALK
                SHOW.tabName -> SHOW
                else -> INVALID
            }
        }
    }
}