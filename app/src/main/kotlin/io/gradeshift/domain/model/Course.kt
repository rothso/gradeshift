package io.gradeshift.domain.model

import nz.bradcampbell.paperparcel.PaperParcel

@PaperParcel
data class Course(
        val id: Int,
        val name: String,
        val teacher: String,
        val grade: Int
) {
    companion object {
        val DUMMY_COURSE: Course = Course(0, "Chemistry", "Dr. HCL", 100)
        val DUMMY_COURSES: List<Course> = listOf(
                Course(0, "Chemistry", "Dr. HCL", 100),
                Course(1, "History", "Henry VIII", 80),
                Course(2, "Calculus", "Ms. Lady", 86),
                Course(3, "Physics", "Jesus", 89),
                Course(4, "TOK", "Ms. Lady", 4),
                Course(5, "English", "Ms. Lady", 81),
                Course(6, "Latin", "Ms. Lady", 80),
                Course(7, "Psychology", "Ms. Lady", 82)
        )
    }
}