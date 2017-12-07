package io.atlant.rent.utils

object CredentialHolder {

    private var token: String? = null

    fun logOut() {
        token = null
    }
}
