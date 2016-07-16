package io.gradeshift.model

data class Grade(
        val name: String,
        val pointsEarned: Int,
        val pointsPossible: Int
) {
    companion object {
        val DUMMY_GRADES: List<Grade> = listOf(
                Grade("Homework 1", 7, 10),
                Grade("Homework 2", 9, 10),
                Grade("Quiz", 20, 20),
                Grade("Test", 88, 100),
                Grade("Pop Final", 3, 100)
        )
    }
}