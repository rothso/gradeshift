package io.gradeshift.model

data class Class(val name: String, val teacher: String, val grade: Int) {

    companion object {
        val DUMMY_CLASSES: List<Class> = listOf(
                Class("Chemistry", "Dr. HCL", 100),
                Class("History", "Henry VIII", 80),
                Class("Calculus", "Ms. Lady", 86)
        )
    }

}