package com.example.mycalllist.utils

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ContactsViewModel : ViewModel() {
    // состояние контактов
    var contacts by mutableStateOf(listOf<Contact>())
        private set

    // обновление контактов
    fun updateContacts(newContacts: List<Contact>) {
        contacts = newContacts
    }
}