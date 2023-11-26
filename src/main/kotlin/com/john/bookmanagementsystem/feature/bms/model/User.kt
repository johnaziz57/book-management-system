package com.john.bookmanagementsystem.feature.bms.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "Person")
data class User(@Id @GeneratedValue val id: Long, val userType: UserType, val name: String)