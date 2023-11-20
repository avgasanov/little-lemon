package com.example.littlelemon.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.littlelemon.R
import com.example.littlelemon.persistence.User
import com.example.littlelemon.ui.theme.SemiWhite

@Composable
fun PersonalInformation(
    user: User,
    setUser: (User) -> Unit,
    submitText: String,
    onClickSubmit: () -> Unit,
    modifier: Modifier
) {
    Column(modifier) {
        Text(
            stringResource(id = R.string.personal_information),
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 40.dp, bottom = 12.dp)
        )
        InputBox(
            title = stringResource(id = R.string.first_name),
            value = user.name,
            setValue = {
                setUser(user.copy(name = it))
            },
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        )
        InputBox(
            title = stringResource(id = R.string.last_name), value = user.lastName, setValue = {
                setUser(user.copy(lastName = it))
            }, modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        InputBox(
            title = stringResource(id = R.string.email), value = user.email, setValue = {
                setUser(user.copy(email = it))
            }, modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        Spacer(modifier = Modifier.weight(1F))
        OutlinedButton(
            onClick = onClickSubmit,
            Modifier
                .fillMaxWidth(1F)
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
            ),
            shape = RoundedCornerShape(4.dp),
            border = BorderStroke(
                width = 1.dp, color = MaterialTheme.colorScheme.tertiary
            )
        ) {
            Text(submitText)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputBox(
    title: String,
    value: String,
    setValue: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(4.dp)
        )
        TextField(
            value = value,
            onValueChange = setValue,
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth(1F)
                .border(2.dp, SemiWhite, RoundedCornerShape(16.dp)),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )
    }
}