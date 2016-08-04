package io.gradeshift.data.network.auth

data class User(val id: Int, val token: Token) {

    companion object {
        val DUMMY_USER = User(0, Token.DUMMY_TOKEN)
    }
}