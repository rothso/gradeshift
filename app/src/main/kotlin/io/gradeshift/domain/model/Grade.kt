package io.gradeshift.domain.model

import nz.bradcampbell.paperparcel.PaperParcel

@PaperParcel
data class Grade(
        val name: String,
        val pointsEarned: Int,
        val pointsPossible: Int,
        val score: Double
) {
    companion object {
        val DUMMY_GRADES: List<Grade> = listOf(
                Grade("Homework 1", 7, 10, 7.0 / 10 * 100),
                Grade("Homework 2", 9, 10, 9.0 / 10 * 100),
                Grade("Quiz", 20, 20, 20.0 / 20 * 100),
                Grade("Test", 88, 100, 88.0 / 100 * 100),
                Grade("Pop Final", 3, 100, 3.0 / 100 * 100)
        )
    }
}