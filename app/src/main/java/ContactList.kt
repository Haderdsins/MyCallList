package com.example.mycalllist

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.ContactsContract
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mycalllist.utils.Contact


@Composable
fun ContactList(contacts: List<Contact>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier.fillMaxSize().padding(16.dp)) {
        items(contacts) { contact ->
            ContactCard(contact = contact, context = LocalContext.current) // Передаем контекст здесь
        }
    }
}
private fun contactExists(context: Context, contactId: String): Boolean {
    val uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, contactId)
    val cursor = context.contentResolver.query(uri, null, null, null, null)
    val exists = cursor != null && cursor.count > 0
    cursor?.close() // закрываем курсор после проверки
    return exists
}
@Composable
fun ContactCard(contact: Contact, context: Context) {
    Card(
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                if (contactExists(context, contact.contactId)) { // Проверяем существование контакта
                    openContact(context, contact.contactId)
                } else {
                    // Здесь можно добавить уведомление о том, что контакт не существует
                }
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // иконка контакта
            ContactIcon(painterResource(id = R.drawable.ion)) // Поставьте здесь свою иконку

            Spacer(modifier = Modifier.width(16.dp))

            // информация о контакте
            Column {
                Text(
                    text = contact.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = contact.phoneNumber,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Composable
fun ContactIcon(painter: Painter) {
    Card(
        shape = RoundedCornerShape(50),
        modifier = Modifier.size(48.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        )
    }
}

private fun openContact(context: Context, contactId: String) {
    val intent = Intent(Intent.ACTION_VIEW).apply {
        data = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, contactId)
    }
    context.startActivity(intent)
}


