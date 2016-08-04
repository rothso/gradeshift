package io.gradeshift.data.network.provider.focus.converter

import com.ekchang.jsouper.ElementAdapter
import io.gradeshift.domain.model.Course
import org.jsoup.nodes.Element

class CourseConverter : ElementAdapter<Course>() {

    override fun query(): String {
        return ".Programs .BoxContents tr:has(img[src=\"modules/Grades/Grades.png\"])"
    }

    override fun fromElement(element: Element): Course {
        try {
            val links = element.select("a")
            val courseLink = links.get(0)
            val gradeLink = links.get(1)

            val id = courseLink.attr("href").split("course_period_id=")[1].toInt()
            val name = courseLink.text().split(" - ")[0]
            val teacher = courseLink.text().split(" - ")[1]
            val grade = gradeLink.text().split("%")[0].toInt()

            return Course(id, name, teacher, grade)
        } catch (e: Exception) {
            throw RuntimeException("ParseException", e)
        }
    }
}