package shafat.projects.assesment.presentation.states


sealed class AuthScreenState(
    val username: String? = "",
    val password: String? = "",
    val authToken: String? = ""
) {
    object Idle : AuthScreenState()

    class LoginInRequestSend(
        username: String,
        password: String
    ) :
        AuthScreenState(username = username, password = password)

    object LoginInProgress : AuthScreenState()

    class LoginSuccessful(authToken: String) : AuthScreenState(authToken = authToken)

    object LoginFailed : AuthScreenState()
}