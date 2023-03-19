package com.example.education.extansions

import androidx.lifecycle.MutableLiveData

/**
 * @author Mobile Developer https://www.youtube.com/watch?v=rb0lobFwZbg
 * */

// Set default value for any type of MutableLiveData
fun <T : Any?> MutableLiveData<T>.default(initialValue: T) = apply { setValue(initialValue) }
// Set new value for any tye of  MutableLiveData
fun <T : Any?> MutableLiveData<T>.set(newValue: T) = apply { setValue(newValue) }
