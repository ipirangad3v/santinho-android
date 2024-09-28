package digital.thon.santinho.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

enum class Role(
    val maxChars: Int,
    val exhibitionName: String
) {
    PRESIDENT(2, "Presidente"),
    GOVERNOR(2, "Governador"),
    MAYOR(2, "Prefeito"),
    SENATOR(3, "Senador"),
    FEDERAL_DEPUTY(4, "Deputado Federal"),
    STATE_DEPUTY(5, "Deputado Estadual"),
    COUNCILOR(5, "Vereador");

    companion object {

        fun createState() = mutableStateOf("")
    }
}

class RoleStates {
    private val states = mutableMapOf<Role, MutableState<String>>()

    fun getState(role: Role): MutableState<String> {
        return states.computeIfAbsent(role) { Role.createState() }
    }
}

