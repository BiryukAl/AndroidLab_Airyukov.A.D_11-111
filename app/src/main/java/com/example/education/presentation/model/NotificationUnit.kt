package com.example.education.presentation.model

data class NotificationUnit(
    var headerText: String? = null,
    var messageText: String? = null,
    var bigText: String? = null,
    var timeOut: Long = 0L,

    var headerIsNotEmpty: Boolean = false,
    var messageIsNotEmpty: Boolean = false,
    var bigTextIsNotEmpty: Boolean = false,
    var timeOutIsNotEmpty: Boolean = false,
    )
