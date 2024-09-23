package br.com.quatrodcum.myhealth.model.domain

data class User(
    val id: Int?,
    val name: String,
    val email: String,
    val objective: Objective
)
