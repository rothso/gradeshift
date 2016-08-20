package io.gradeshift.domain.model

import nz.bradcampbell.paperparcel.PaperParcel

@PaperParcel
data class Grade(
        val name: String,
        val pointsEarned: Int?,
        val pointsPossible: Int,
        val score: Double?
)