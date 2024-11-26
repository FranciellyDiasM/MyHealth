package br.com.quatrodcum.myhealth.model.dao.mongodb.entity

data class UserEntity(
    var id: Int?,
    var name: String,
    var email: String,
    var imc: Double,
    var password: String,
    var objective: Int
)
