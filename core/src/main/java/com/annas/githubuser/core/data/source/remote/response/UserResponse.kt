package com.annas.githubuser.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @field:SerializedName("id")
    var id: Int,

    @field:SerializedName("login")
    var username: String,

    @field:SerializedName("avatar_url")
    val avatarUrl: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("company")
    val company: String? = null,

    @field:SerializedName("location")
    val location: String? = null,

    @field:SerializedName("public_repos")
    val publicRepos: Int? = null,

    @field:SerializedName("followers")
    val followers: Int? = null,

    @field:SerializedName("following")
    val following: Int? = null,
)

