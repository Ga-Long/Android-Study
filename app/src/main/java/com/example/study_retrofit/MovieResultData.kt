package com.example.study_retrofit

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MovieAPIResponse(
    //가져올 데이터
    @SerializedName("status")
    @Expose
    var status: String,

    @SerializedName("status_message")
    @Expose
    var status_message: String,

    @SerializedName("data")
    @Expose
    var data: MovieAPIResult,

    )

data class MovieAPIResult(
    @SerializedName("movie_count")
    @Expose
    var movie_count: String,

    @SerializedName("limit")
    @Expose
    var limit: String,

    @SerializedName("page_number")
    @Expose
    var page_number: String,

    @SerializedName("movies")
    @Expose
    var movies: List<MovieData>,
)

data class MovieData(
    @SerializedName("title")
    @Expose
    var title: String,

    @SerializedName("summary")
    @Expose
    var summary: String,

    @SerializedName("medium_cover_image")
    @Expose
    var medium_cover_image: String,
)
