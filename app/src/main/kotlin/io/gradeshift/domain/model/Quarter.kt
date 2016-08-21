package io.gradeshift.domain.model

import nz.bradcampbell.paperparcel.PaperParcel

@PaperParcel
data class Quarter(
        val id: Int,
        val name: String
) {
    companion object {
        val DUMMY_QUARTER: Quarter = Quarter(0, "Quarter")
        val DUMMY_QUARTERS: List<Quarter> = listOf(
                Quarter(0, "Quarter 1"),
                Quarter(1, "Quarter 2"),
                Quarter(2, "Quarter 3"),
                Quarter(3, "Quarter 4")
        )
    }

    override fun toString(): String {
        return name
    }
}