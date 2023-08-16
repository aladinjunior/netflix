package co.aladinjunior.netflixremake.model

import androidx.annotation.DrawableRes

data class Movie(
    val id: Int,
    val coverUrl: String,
    var title: String? = null,
    var description: String? = null,
    var cast: String? = null,
    var similars: List<Movie>? = null)


