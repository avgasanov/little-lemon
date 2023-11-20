package com.example.littlelemon.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.littlelemon.R
import com.example.littlelemon.persistence.SharedPreferences
import com.example.littlelemon.persistence.User

@Composable
fun Profile(navController: NavController) {
    val context = LocalContext.current
    val sharedPrefs = remember { SharedPreferences(context) }
    val (user, setUser) = rememberSaveable { mutableStateOf(sharedPrefs.getUser()!!) }
    Column() {
        Image(
            painterResource(id = R.drawable.logo),
            contentDescription = stringResource(id = R.string.logo_desc),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 40.dp)
                .width(200.dp)
                .height(100.dp)
        )
        PersonalInformation(
            user = user,
            setUser = setUser,
            submitText = stringResource(id = R.string.logout),
            onClickSubmit = {
                sharedPrefs.saveUser(User.DEFAULT)
                navController.navigate(com.example.littlelemon.Onboarding.route)
            },
            modifier = Modifier.weight(1F)
        )
    }
}