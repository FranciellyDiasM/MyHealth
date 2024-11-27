package br.com.quatrodcum.myhealth.model.dao.mongodb.entity

import br.com.quatrodcum.myhealth.model.domain.Objective
import br.com.quatrodcum.myhealth.model.domain.User

data class UserEntity(
    var id: Int? = null,
    var name: String?,
    var email: String?,
    var imc: Double?,
    var password: String?,
    var objective: Int?
) {
    companion object {
        fun UserEntity.toDomain(objective: Objective): User {
            return User(
                id = this.id,
                name = this.name.orEmpty(),
                email = this.email.orEmpty(),
                imc = this.imc ?: -1.0,
                password = this.password.orEmpty(),
                objective = objective
            )
        }
    }

}
