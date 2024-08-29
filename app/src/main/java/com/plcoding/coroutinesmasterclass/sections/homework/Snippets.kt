package com.plcoding.coroutinesmasterclass.sections.homework

/**
 * Listener definition to use in your class
 */
fun interface LeaderboardListener {
    fun onLeaderboardUpdated(leaderboard: String)
}

private val listeners = mutableListOf<LeaderboardListener>()

/**
 * Logic to calculate the top 3 highest scores from a map
 */
val allScores = mutableMapOf<String, Int>(
    "Mani" to 45,
    "Alex" to 100,
    "Peter" to 300,
)
val topThree = allScores
    .entries
    .sortedByDescending { it.value }
    .take(3)
    .withIndex()
    .joinToString("\n") { (index, entry) ->
        "#${index + 1} is ${entry.key} with ${entry.value} points"
    }