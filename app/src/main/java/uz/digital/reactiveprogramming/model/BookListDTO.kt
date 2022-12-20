package uz.digital.reactiveprogramming.model

import com.google.gson.annotations.SerializedName

data class BookListDTO(val items: ArrayList<VolumeInfo>)
data class VolumeInfo(val volumeInfo: BookInfo)
data class BookInfo(
    val title: String,
    val publisher: String,
    val description: String,
    @SerializedName("imageLinks")
    val imageLinks: ImageLinks
)

data class ImageLinks(
    @SerializedName("smallThumbnail")
    val smallThumbnail: String?
    )