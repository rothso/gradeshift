package io.gradeshift.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.github.ajalt.timberkt.d
import com.jakewharton.rxbinding.view.clicks
import io.gradeshift.GradesApplication
import io.gradeshift.domain.model.Quarter
import io.gradeshift.ui.common.base.Presenter
import io.gradeshift.ui.common.ext.bind
import io.gradeshift.ui.common.ext.ui
import io.gradeshift.ui.login.LoginActivity
import io.gradeshift.ui.overview.OverviewActivity
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import org.jetbrains.anko.setContentView
import rx.Observable
import rx.Subscription
import rx.lang.kotlin.plusAssign
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainPresenter.View {
    @Inject lateinit var ui: MainUI
    @Inject lateinit var presenter: MainPresenter
    lateinit private var subscription: Subscription

    override val overviewClicks: Observable<Unit> by lazy { ui.overviewButton.clicks() }
    override val showOverview = ui<Quarter> { startActivity(OverviewActivity.intent(this, it)) }
    override val showLogin = ui<Unit> { startActivity(LoginActivity.intent(this)) }

    companion object {
        fun intent(ctx: Context): Intent {
            return ctx.intentFor<MainActivity>().newTask().clearTask()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        GradesApplication.graph.plus(MainModule()).inject(this)
        ui.setContentView(this)
        subscription = presenter.bind(this)
    }

    override fun onDestroy() {
        subscription.unsubscribe()
        super.onDestroy()
    }
}

interface CheckLogin {
    fun isLoggedIn(): Observable<Boolean>
}

class MainPresenter(val checkLogin: CheckLogin) : Presenter<MainPresenter.View>() {

    override fun bind(view: View): Subscription {
        val subscription = CompositeSubscription()

        // Redirect if user is not logged in, otherwise show content view
        subscription += checkLogin.isLoggedIn()
                .filter { status -> status == false }
                .doOnNext { d { "No user logged in, redirecting" } }
                .map { Unit }
                .bind(view.showLogin)

        subscription += view.overviewClicks
                .onBackpressureLatest()
                .map { Quarter.DUMMY_QUARTER }
                .bind(view.showOverview)

        return subscription
    }

    interface View {
        val overviewClicks: Observable<Unit>
        val showOverview: (Observable<Quarter>) -> Subscription
        val showLogin: (Observable<Unit>) -> Subscription
    }

}