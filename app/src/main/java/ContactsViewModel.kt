package com.example.mycalllist.utils

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mycalllist.utils.Contact

class ContactsViewModel : ViewModel() {
    // Состояние контактов
    var contacts by mutableStateOf(listOf<Contact>())
        private set

    // Метод для обновления контактов
    fun updateContacts(newContacts: List<Contact>) {
        contacts = newContacts
    }
}