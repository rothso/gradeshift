package io.gradeshift.data.network.provider.focus.converter

import com.ekchang.jsouper.ElementAdapter
import io.gradeshift.domain.model.Grade
import org.jsoup.nodes.Element

class GradeConverter : ElementAdapter<Grade>() {

    override fun query(): String {
        return "#center_table tbody .lo_row"
    }

    override fun fromElement(element: Element): Grade {
        try {
            val cells = element.select("td")

            val name = cells[0].text()
            val points = cells[1].text().split(" / ")
            val pointsEarned = try { points[0].toInt() } catch (e: NumberFormatException) { null }
            val pointsPossible = points[1].toInt()
            val score = pointsEarned?.toDouble()?.div(pointsPossible)

            return Grade(name, pointsEarned, pointsPossible, score)
        } catch (e: Exception) {
            throw RuntimeException("ParseException", e)
        }
    }
}