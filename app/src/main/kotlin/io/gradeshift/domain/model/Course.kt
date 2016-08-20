package io.gradeshift.domain.model

import nz.bradcampbell.paperparcel.PaperParcel

@PaperParcel
data class Course(
        val id: Int,
        val name: String,
        val teacher: String,
        val grade: Int
)