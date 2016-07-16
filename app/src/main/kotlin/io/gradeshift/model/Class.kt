package io.gradeshift.model

data class Class(
        val id: Int,
        val name: String,
        val teacher: String,
        val grade: Int
) {
    companion object {
        val DUMMY_CLASSES: List<Class> = listOf(
                Class(0, "Chemistry", "Dr. HCL", 100),
                Class(1, "History", "Henry VIII", 80),
                Class(2, "Calculus", "Ms. Lady", 86)
        )
    }

}