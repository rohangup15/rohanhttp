package com.rohan.rohannetworking.models

import com.google.gson.annotations.SerializedName

class ResponseDto {
    @SerializedName("results")
    val results: List<Movie>? = null

    @SerializedName("page")
    val page: Int? = null

    @SerializedName("total_pages")
    val totalPages: Int? = null

    @SerializedName("total_results")
    val totalResults: Int? = null
}