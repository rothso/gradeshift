package io.gradeshift.domain.model

import nz.bradcampbell.paperparcel.PaperParcel

@PaperParcel
data class Quarter(
        val id: Int,
        val name: String
) {
    companion object {
        val DUMMY_QUARTER: Quarter = Quarter(0, "Quarter")
    }
}