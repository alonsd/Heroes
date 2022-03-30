package com.heroes.model.network_models


import com.google.gson.annotations.SerializedName

data class HeroesListResponseModel(
    @SerializedName("response")
    val response: String,
    @SerializedName("results")
    val heroesList: List<Hero>,
    @SerializedName("results-for")
    val resultsFor: String
) {
    data class Hero(
        @SerializedName("appearance")
        val appearance: Appearance,
        @SerializedName("biography")
        val biography: Biography,
        @SerializedName("connections")
        val connections: Connections,
        @SerializedName("id")
        val id: String,
        @SerializedName("image")
        val image: Image,
        @SerializedName("name")
        val name: String,
        @SerializedName("powerstats")
        val powerstats: Powerstats,
        @SerializedName("work")
        val work: Work
    ) {
        data class Appearance(
            @SerializedName("eye-color")
            val eyeColor: String,
            @SerializedName("gender")
            val gender: String,
            @SerializedName("hair-color")
            val hairColor: String,
            @SerializedName("height")
            val height: List<String>,
            @SerializedName("race")
            val race: String,
            @SerializedName("weight")
            val weight: List<String>
        )

        data class Biography(
            @SerializedName("aliases")
            val aliases: List<String>,
            @SerializedName("alignment")
            val alignment: String,
            @SerializedName("alter-egos")
            val alterEgos: String,
            @SerializedName("first-appearance")
            val firstAppearance: String,
            @SerializedName("full-name")
            val fullName: String,
            @SerializedName("place-of-birth")
            val placeOfBirth: String,
            @SerializedName("publisher")
            val publisher: String
        )

        data class Connections(
            @SerializedName("group-affiliation")
            val groupAffiliation: String,
            @SerializedName("relatives")
            val relatives: String
        )

        data class Image(
            @SerializedName("url")
            val url: String
        )

        data class Powerstats(
            @SerializedName("combat")
            val combat: String,
            @SerializedName("durability")
            val durability: String,
            @SerializedName("intelligence")
            val intelligence: String,
            @SerializedName("power")
            val power: String,
            @SerializedName("speed")
            val speed: String,
            @SerializedName("strength")
            val strength: String
        )

        data class Work(
            @SerializedName("base")
            val base: String,
            @SerializedName("occupation")
            val occupation: String
        )
    }
}