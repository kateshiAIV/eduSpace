package com.eduspace.models

import android.os.Parcel
import android.os.Parcelable

data class PostData(

    val postID: String? =  null,
    val postHeader: String? = null,
    val postDescription: String? = null,
    val postAuthor: String? = null,
    val postLink: String? = null,
    val postLikes: String? = null,
    val postComments: String? = null,
    val postCommentsObj: List<Comment>? = null, // Updated to include a list of comments
    val postSaves: String? =  null

) {
    fun first(): String {
        return postID?.toString() ?: ""
    }

}

data class Comment(
    val author: String? = null,
    val comment: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(author)
        parcel.writeString(comment)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Comment> {
        override fun createFromParcel(parcel: Parcel): Comment {
            return Comment(parcel)
        }

        override fun newArray(size: Int): Array<Comment?> {
            return arrayOfNulls(size)
        }
    }
}