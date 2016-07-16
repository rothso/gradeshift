package io.gradeshift.domain

import io.gradeshift.model.Grade
import rx.Observable

class QuarterGradesInteractor(val classId: Int) {

    fun getCurrentQuarterGrades(): Observable<List<Grade>> =
            Observable.defer { Observable.just(Grade.DUMMY_GRADES) }
                    .map { it.map { it.copy(name = "Class $classId ${it.name}") } }
}