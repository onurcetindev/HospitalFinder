package com.example.mobproject.ui.screen.main

import android.app.Activity
import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.permissions.ExperimentalPermissionsApi
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ProfileScreen(
    navController: NavController? = null,
    context: Context = LocalContext.current,
) {
    val screenHeight = LocalContext.current.resources.displayMetrics.heightPixels / LocalContext.current.resources.displayMetrics.density
    var userName by remember { mutableStateOf(TextFieldValue("Jane")) }
    var userSurname by remember { mutableStateOf(TextFieldValue("Doe")) }
    var userEmail by remember { mutableStateOf(TextFieldValue("jane.doe@example.com")) }
    var userPassword by remember { mutableStateOf(TextFieldValue("")) }
    var userDateOfBirth by remember { mutableStateOf(TextFieldValue("")) }
    var userCountry by remember { mutableStateOf(TextFieldValue("")) }
    var userPhone by remember { mutableStateOf(TextFieldValue("")) }
    var isEditing by remember { mutableStateOf(false) }
    var profileImageUri by remember { mutableStateOf<Uri?>(null) }
    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            profileImageUri = uri
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height((0.25f * screenHeight).dp)
                .background(Color.Gray)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(profileImageUri ?: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTKbnYFqUrBVv7bJBLQwMzA3OclwJpmekO7LA&usqp=CAU")
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer { alpha = 0.6f }
            )
            IconButton(
                onClick = { galleryLauncher.launch("image/*") },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(8.dp)
            ) {
                Icon(imageVector = Icons.Default.PhotoCamera, contentDescription = "Change Photo", tint = Color.Gray)
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = (0.17f * screenHeight).dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(profileImageUri ?: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTKbnYFqUrBVv7bJBLQwMzA3OclwJpmekO7LA&usqp=CAU")
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(140.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.White, CircleShape)
            )
            Column (
                modifier = Modifier.padding(top =142.dp, start = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(text = "${userName.text} ${userSurname.text}", color = Color.Black, fontSize = 18.sp)
            }
        }

        Text(
            text = "PROFILE",
            fontSize = 18.sp,
            color = Color.White,
            modifier = Modifier
                .padding(10.dp)
                .align(Alignment.TopCenter)
        )
        IconButton(
            onClick = { isEditing = !isEditing },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        ) {
            Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit", tint = Color.White)
        }

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(top = 300.dp, start = 12.dp, end = 12.dp)
                .verticalScroll(rememberScrollState())
        ) {
            ProfileInputField(
                label = "Name",
                value = userName,
                onValueChange = { userName = it },
                isEditing = isEditing
            )
            ProfileInputField(
                label = "Surname",
                value = userSurname,
                onValueChange = { userSurname = it },
                isEditing = isEditing
            )
            ProfileInputField(
                label = "Email",
                value = userEmail,
                onValueChange = { userEmail = it },
                isEditing = isEditing,
            )
            ProfileInputField(
                label = "Password",
                value = userPassword,
                onValueChange = { userPassword = it },
                isEditing = isEditing,
            )
            ProfileInputField(
                label = "Date of Birth",
                value = userDateOfBirth,
                onValueChange = { userDateOfBirth = it },
                isEditing = isEditing,
            )
            ProfileInputField(
                label = "Country",
                value = userCountry,
                onValueChange = { userCountry = it },
                isEditing = isEditing,
            )
            ProfileInputField(
                label = "Phone",
                value = userPhone,
                onValueChange = { userPhone = it },
                isEditing = isEditing,
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable {
                        // Uygulamayı kapat
                        (context as? Activity)?.finishAffinity()
                    }
                    .padding(22.dp)
            ) {
                Icon(imageVector = Icons.Default.ExitToApp, contentDescription = "Logout", tint = Color.Black)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Logout", color = Color.Black)
            }
        }


    }
}
@Composable
fun ProfileInputField(
    label: String,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    isEditing: Boolean
){
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        enabled = isEditing
    )
}