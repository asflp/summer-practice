package com.example.filmust.workdata
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable
data class MovieResponse (
    val page: Long? = null,
    val next: String? = null,
    val entries: Long? = null,
    val results: List<Movie>
)

@Serializable
data class Movie(
    @SerialName("_id")
    var id: String,

    @SerialName("id")
    var resultID: String,

    var ratingsSummary: ResultRatingsSummary? = null, //рейтинг
    var episodes: ResultEpisodes? = null,
    var primaryImage: PrimaryImage? = null,   //картинка
    var titleType: TitleType?,
    var genres: Genres? = null,                       //жанры
    var titleText: TitleText? = null,                 //название
    var originalTitleText: TitleText? = null,
    var releaseYear: ReleaseYear? = null,
    var releaseDate: ReleaseDate? = null,             //дата выхода
    var runtime: Runtime? = null,             //длительность
    var series: JsonElement? = null,
    var meterRanking: MeterRanking? = null,   //рейтинг
    var plot: Plot? = null                    //описание
)
@Serializable
data class ResultEpisodes (
    val episodes: TotalEpisodesClass? = null,
    val seasons: List<Season>? = null,
    val years: List<Year>? = null,
    val totalEpisodes: TotalEpisodesClass? = null,
    val unknownSeasonEpisodes: TotalEpisodesClass? = null,
    val topRated: TopRated? = null,

    @SerialName("__typename")
    val typename: String? = null
)

@Serializable
data class TotalEpisodesClass (
    val total: Long? = null,

    @SerialName("__typename")
    val typename: String? = null
)

@Serializable
data class Season (
    val number: Long? = null,

    @SerialName("__typename")
    val typename: String? = null
)

@Serializable
data class TopRated (
    val edges: List<Edge>? = null,

    @SerialName("__typename")
    val typename: String? = null
)

@Serializable
data class Edge (
    val node: Node? = null,

    @SerialName("__typename")
    val typename: String? = null
)

@Serializable
data class Node (
    val ratingsSummary: NodeRatingsSummary? = null,

    @SerialName("__typename")
    val typename: String? = null
)

@Serializable
data class NodeRatingsSummary (
    val aggregateRating: JsonElement? = null,

    @SerialName("__typename")
    val typename: RatingsSummaryTypename? = null
)

@Serializable
enum class RatingsSummaryTypename(val value: String) {
    @SerialName("RatingsSummary") RatingsSummary("RatingsSummary");
}

@Serializable
data class Year (
    val year: Long? = null,

    @SerialName("__typename")
    val typename: String? = null
)

@Serializable
data class Genres (
    val genres: List<Genre>? = null,

    @SerialName("__typename")
    val typename: GenresTypename? = null
)

@Serializable
data class Genre (
    val text: String? = null,
    val id: String? = null,

    @SerialName("__typename")
    val typename: GenreTypename? = null
)

@Serializable
enum class GenreTypename(val value: String) {
    @SerialName("Genre") Genre("Genre");
}

@Serializable
enum class GenresTypename(val value: String) {
    @SerialName("Genres") Genres("Genres");
}

@Serializable
data class MeterRanking (
    val currentRank: Long? = null,
    val rankChange: RankChange? = null,

    @SerialName("__typename")
    val typename: String? = null
)

@Serializable
data class RankChange (
    val changeDirection: String? = null,
    val difference: Long? = null,

    @SerialName("__typename")
    val typename: String? = null
)

@Serializable
data class TitleText (
    val text: String? = null,

    @SerialName("__typename")
    val typename: OriginalTitleTextTypename? = null
)

@Serializable
enum class OriginalTitleTextTypename(val value: String) {
    @SerialName("TitleText") TitleText("TitleText");
}

@Serializable
data class Plot (
    val plotText: PlotText? = null,
    val language: Language? = null,

    @SerialName("__typename")
    val typename: String? = null
)

@Serializable
data class Language (
    val id: String? = null,

    @SerialName("__typename")
    val typename: String? = null
)

@Serializable
data class PlotText (
    val plainText: String? = null,

    @SerialName("__typename")
    val typename: PlotTextTypename? = null
)

@Serializable
enum class PlotTextTypename(val value: String) {
    @SerialName("Markdown") Markdown("Markdown");
}

@Serializable
data class PrimaryImage (
    val id: String? = null,
    val width: Long? = null,
    val height: Long? = null,
    val url: String? = null,
    val caption: PlotText? = null,

    @SerialName("__typename")
    val typename: String? = null
)

@Serializable
data class ResultRatingsSummary (
    val aggregateRating: JsonElement? = null,
    val voteCount: Long? = null,

    @SerialName("__typename")
    val typename: RatingsSummaryTypename? = null
)

@Serializable
data class ReleaseDate (
    val day: Long? = null,
    val month: Long? = null,
    val year: Long? = null,

    @SerialName("__typename")
    val typename: ReleaseDateTypename? = null
)

@Serializable
enum class ReleaseDateTypename(val value: String) {
    @SerialName("ReleaseDate") ReleaseDate("ReleaseDate");
}

@Serializable
data class ReleaseYear (
    val year: Long? = null,
    val endYear: JsonElement? = null,

    @SerialName("__typename")
    val typename: ReleaseYearTypename? = null
)

@Serializable
enum class ReleaseYearTypename(val value: String) {
    @SerialName("YearRange") YearRange("YearRange");
}

@Serializable
data class Runtime (
    val seconds: Long? = null,
    val displayableProperty: DisplayableProperty? = null,

    @SerialName("__typename")
    val typename: String? = null
)

@Serializable
data class DisplayableProperty (
    val value: PlotText? = null,

    @SerialName("__typename")
    val typename: DisplayablePropertyTypename? = null
)

@Serializable
enum class DisplayablePropertyTypename(val value: String) {
    @SerialName("DisplayableTitleRuntimeProperty") DisplayableTitleRuntimeProperty("DisplayableTitleRuntimeProperty"),
    @SerialName("DisplayableTitleTypeProperty") DisplayableTitleTypeProperty("DisplayableTitleTypeProperty");
}

@Serializable
data class TitleType (
    val displayableProperty: DisplayableProperty? = null,
    val text: Text? = null,
    val id: ID? = null,
    val isSeries: Boolean? = null,
    val isEpisode: Boolean? = null,
    val categories: List<Category>? = null,
    val canHaveEpisodes: Boolean? = null,

    @SerialName("__typename")
    val typename: TitleTypeTypename? = null
)

@Serializable
data class Category (
    val value: Value? = null,

    @SerialName("__typename")
    val typename: CategoryTypename? = null
)

@Serializable
enum class CategoryTypename(val value: String) {
    @SerialName("TitleTypeCategory") TitleTypeCategory("TitleTypeCategory");
}

@Serializable
enum class Value(val value: String) {
    @SerialName("movie") Movie("movie"),
    @SerialName("tv") Tv("tv");
}

@Serializable
enum class ID(val value: String) {
    @SerialName("movie") Movie("movie"),
    @SerialName("tvSeries") TvSeries("tvSeries");
}

@Serializable
enum class Text(val value: String) {
    @SerialName("Movie") Movie("Movie"),
    @SerialName("TV Series") TVSeries("TV Series");
}

@Serializable
enum class TitleTypeTypename(val value: String) {
    @SerialName("TitleType") TitleType("TitleType");
}