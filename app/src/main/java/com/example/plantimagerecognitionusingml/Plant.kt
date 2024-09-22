package com.example.plantimagerecognitionusingml


data class Plant(
    val commonName: String,
    val scientificName: String? = null,
    val imageResId: Int,
    val medicinalProperties: String? = null,
    val application: String? = null,
    val commonlyFound: String? = null,
    val description: String? = null // Optional description
) {
    val info: String
        get() {
            val scientificNameInfo = if (scientificName != null) ", Scientific Name: $scientificName" else ""
            val descriptionInfo = if (description != null) "\nDescription: $description" else ""
            return "Name: $commonName$scientificNameInfo$descriptionInfo"
        }
}
