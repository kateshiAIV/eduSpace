package com.eduspace.models

data class SuggestPost(

    val postID: String? = null,
    val postAuthor: String? =  null,
    val postDescription: String? = null,
    val postHeader: String? = null,
    val postLink: String? =  null,
){
    fun first(): String {
        return postAuthor?.toString() ?: ""
    }

}
