package io.gradeshift.ui.login

import android.content.Context
import dagger.Module
import dagger.Provides
import io.gradeshift.domain.LoginInteractor
import io.gradeshift.domain.service.Authenticator
import io.gradeshift.ui.common.ActivityScope

@Module
class LoginModule {

    @Provides @ActivityScope
    fun provideUI(): LoginUI = LoginUI()

    @Provides @ActivityScope
    fun providePresenter(interactor: LoginInteractor): LoginPresenter = LoginPresenter(interactor)

    @Provides @ActivityScope
    fun provideInteractor(authenticator: Authenticator, application: Context): LoginInteractor {
      return LoginInteractor(authenticator, application as Authenticator.Callback) // TODO hacky
    }
}