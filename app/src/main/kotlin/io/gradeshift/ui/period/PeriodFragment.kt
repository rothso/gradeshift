package io.gradeshift.ui.period

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.gradeshift.GradesApplication
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.support.v4.ctx
import rx.Subscription
import javax.inject.Inject

class PeriodFragment : Fragment() {
    @Inject lateinit var ui: PeriodUI
    @Inject lateinit var presenter: PeriodPresenter
    lateinit var subscription: Subscription

    companion object {
        private const val ARG_CLASS_ID: String = "CLASS_ID"

        fun newInstance(classId: Int): PeriodFragment {
            val bundle = Bundle()
            bundle.putInt(ARG_CLASS_ID, classId)

            val fragment = PeriodFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // TODO surely we need a QuarterID too, right?
        val classId = arguments.getInt(ARG_CLASS_ID)
        GradesApplication.graph.plus(PeriodModule(classId)).inject(this)
        val view = ui.createView(AnkoContext.Companion.create(ctx, this))
        subscription = presenter.bind(ui)
        return view
    }

    override fun onDestroyView() {
        subscription.unsubscribe()
        super.onDestroyView()
    }
}
