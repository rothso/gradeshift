package io.gradeshift.domain.model

data class Year(
        val id: Int,
        val name: String
) {

    companion object {
        val DUMMY_YEAR = Year(0, "Year")
    }
}