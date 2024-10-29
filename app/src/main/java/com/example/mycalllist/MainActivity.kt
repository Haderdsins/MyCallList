package com.example.mycalllist

import PostsScreen
import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.mycalllist.ui.theme.MyCallListTheme
import com.example.mycalllist.utils.fetchAllContacts
import com.example.mycalllist.utils.ContactsViewModel

class MainActivity : ComponentActivity() {
    private val contactsViewModel: ContactsViewModel by viewModels()
    private val postsViewModel: PostsViewModel by viewModels()

    // Переменная для отслеживания состояния экрана
    private var showPosts by mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Проверка разрешения на чтение контактов
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CONTACTS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // Разрешение уже предоставлено, загружаем контакты
            loadContacts()
        } else {
            // Запрашиваем разрешение на чтение контактов
            requestPermissionLauncher.launch(Manifest.permission.READ_CONTACTS)
        }

        setContent {
            MyCallListTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        // Кнопка для переключения между контактами и постами
                        Button(
                            onClick = { showPosts = !showPosts },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Text(text = if (showPosts) "Show Contacts" else "GET Posts")
                        }

                        // Отображение контактов или постов в зависимости от состояния
                        if (showPosts) {
                            PostsScreen(viewModel = postsViewModel)
                        } else {
                            ContactList(
                                contacts = contactsViewModel.contacts,
                                modifier = Modifier.fillMaxSize().padding(16.dp)
                            )
                        }
                    }
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
                // Обработка отказа в предоставлении разрешения
            }
        }

    // Загрузка контактов с использованием ViewModel
    private fun loadContacts() {
        val contacts = fetchAllContacts()
        contactsViewModel.updateContacts(contacts)
    }
}
