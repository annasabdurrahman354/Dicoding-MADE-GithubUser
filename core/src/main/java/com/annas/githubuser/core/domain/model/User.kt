package com.annas.githubuser.core.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var id: Int,

    var username: String,

    val avatarUrl: String? = null,

    val name: String? = null,

    val company: String? = null,

    val location: String? = null,

    val publicRepos: Int? = null,

    val followers: Int? = null,

    val following: Int? = null,
) : Parcelable