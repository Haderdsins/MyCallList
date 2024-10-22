package com.example.mycalllist

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import com.example.mycalllist.ui.theme.MyCallListTheme
import com.example.mycalllist.utils.fetchAllContacts


import com.example.mycalllist.utils.ContactsViewModel


class MainActivity : ComponentActivity() {
    // ViewModel для хранения и управления состоянием контактов
    private val contactsViewModel: ContactsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Проверяем наличие разрешения на чтение контактов
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CONTACTS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // Разрешение уже есть, загружаем контакты
            loadContacts()
        } else {
            // Запрашиваем разрешение на чтение контактов
            requestPermissionLauncher.launch(Manifest.permission.READ_CONTACTS)
        }

        setContent {
            MyCallListTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ContactList(
                        contacts = contactsViewModel.contacts,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    // Лямбда для обработки результата запроса разрешения
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                loadContacts()
            } else {
                // Разрешение не было предоставлено, можно показать сообщение
            }
        }

    // Загружаем контакты с использованием ViewModel
    private fun loadContacts() {
        val contacts = fetchAllContacts()
        contactsViewModel.updateContacts(contacts)
    }
}
