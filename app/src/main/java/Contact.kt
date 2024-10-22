package com.example.mycalllist.utils

import android.annotation.SuppressLint
import android.content.Context
import android.provider.ContactsContract

// Данные для хранения информации о контакте
data class Contact(val name: String, val phoneNumber: String, val contactId: String)


// Функция для получения списка всех контактов
@SuppressLint("Range")
fun Context.fetchAllContacts(): List<Contact> {
    // Используем ContentResolver для получения данных из контактов
    contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null)
        .use { cursor -> // Автоматически закрываем курсор после использования
            if (cursor == null) return emptyList() // Если курсор пуст, возвращаем пустой список

            val contactsList = ArrayList<Contact>()

            // Проходим по всем контактам
            while (cursor.moveToNext()) {
                val name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)) ?: "N/A"
                val phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)) ?: "N/A"
                val contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID)) ?: "N/A"
                // Добавляем контакт в список
                contactsList.add(Contact(name, phoneNumber, contactId))
            }

            // Возвращаем список контактов
            return contactsList
        }
}
