package com.example.littlelemon.components

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.littlelemon.R
import com.example.littlelemon.persistence.SharedPreferences
import com.example.littlelemon.persistence.User
import com.example.littlelemon.ui.theme.SemiWhite

@Composable
fun Onboarding(navController: NavHostController) {
    val (user, setUser) = rememberSaveable { mutableStateOf(User("", "", "")) }
    val context = LocalContext.current
    val sharedPrefs = remember { SharedPreferences(context) }
    Column() {
        Image(
            painterResource(id = R.drawable.logo),
            contentDescription = stringResource(id = R.string.logo_desc),
            modifier = Modifier
                .align(CenterHorizontally)
                .width(200.dp)
                .height(100.dp)
        )
        Text(
            stringResource(id = R.string.lets_get_you_know),
            modifier = Modifier
                .fillMaxWidth(1F)
                .background(MaterialTheme.colorScheme.primary)
                .padding(8.dp, 20.dp),
            style = MaterialTheme.typography.titleLarge,
            color = SemiWhite,
            textAlign = TextAlign.Center
        )
        PersonalInformation(
            user = user,
            setUser = setUser,
            submitText = stringResource(id = R.string.register),
            onClickSubmit = {
                if (user.isValid()) {
                    sharedPrefs.saveUser(user)
                    navController.navigate(com.example.littlelemon.Home.route) {
                        popUpTo(
                            com.example.littlelemon.Onboarding.route
                        ) { inclusive = true }
                    }
                } else {
                    Toast.makeText(
                        context, R.string.please_fill_required_fields, Toast.LENGTH_SHORT
                    ).show()
                }
            },
            modifier = Modifier.weight(1F)
        )
    }
}



@Preview
@Composable
fun OnboardingPreview() {
    val navController = rememberNavController()
    Onboarding(navController)
}