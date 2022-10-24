package com.example.authorizationtest

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composetest.R
import kotlinx.coroutines.launch

@Composable
fun AppCompose.AuthorizationScreen(
    model: AuthorizationScreenModel,
    onIntent: (Intent) -> Unit,
    onCLickAddButton: () -> Unit
) {
    val listItems = listOf("мужской", "женский")
    val listFieldState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .background(
                    color = colorResource(id = R.color.white_2),
                    shape = RoundedCornerShape(size = 8.dp)
                )
                .padding(
                    start = 12.dp,
                    top = 8.dp,
                    end = 12.dp,
                    bottom = 16.dp
                ),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            val focusManager = LocalFocusManager.current
            CustomText(
                text = "Привет!\nУкажите данные:",
                fontSize = 17.sp
            )
            EntryField(
                hint = "Фамилия",
                value = model.surNameField,
                onValueChange = { newSurName ->
                    onIntent(Intent.SurnameFieldChanged(newSurName))
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                )
            )
            EntryField(
                hint = "Имя",
                value = model.nameField,
                onValueChange = { newName ->
                    onIntent(Intent.NameFieldChanged(newName))
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                )
            )
            EntryField(
                hint = "Отчество",
                value = model.patronymicField,
                onValueChange = { newPatronymic ->
                    onIntent(Intent.PatronymicFieldChanged(newPatronymic))
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()

                    }
                )
            )
            DropDownField(
                value = model.sexField,
                listItems = listItems,
                hint = "Выберите пол",
                onItemSelected = { selectedSex ->
                    onIntent(Intent.SexFieldChanged(selectedSex))
                }
            )
            CheckboxField(
                checked = model.subscribedCheckbox,
                onCheckedChange = { newSelection ->
                    onIntent(Intent.SubscribedCheckboxChanged(newSelection))
                },
                text = "Подписаться на спам-рассылку в которой ничего полезного для Вас",
                fontSize = 17.sp
            )

            ButtonSend(
                onClick = {
                    onCLickAddButton()
                    coroutineScope.launch {
                        listFieldState.animateScrollToItem(
                            if (model.authorizedUsersList.isEmpty()) 0 else model.authorizedUsersList.lastIndex
                        )
                    }
                },
                enabled = model.surNameField.isNotBlank()
                        && model.nameField.isNotBlank() &&
                        model.patronymicField.isNotBlank() &&
                        model.sexField.isNotBlank()
            )
        }
        ListField(
            authorizedUsers = model.authorizedUsersList,
            state = listFieldState
        )

    }
}

@Composable
private fun TextListFieldItem(
    hint: String,
    text: String
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        Text(
            text = hint,
            style = AppStyles.textStyle.copy(color = colorResource(id = R.color.grey_3))
        )
        Text(
            text = text,
            style = AppStyles.textStyle,
            modifier = Modifier
                .background(
                    color = colorResource(id = R.color.white_1),
                    shape = RoundedCornerShape(size = 8.dp)
                )
                .padding(
                    start = 10.dp,
                    top = 2.dp,
                    bottom = 2.dp,
                    end = 28.dp
                )
        )
    }

}

@Composable
private fun ListFieldItem(
    surname: String,
    name: String,
    patronymic: String,
    sex: String,
    subscribedCheckbox: String
) {
    Column(
        modifier = Modifier
            .background(
                color = colorResource(id = R.color.white_2),
                shape = RoundedCornerShape(size = 8.dp)
            )
            .border(
                width = 1.dp,
                color = colorResource(id = R.color.grey_1),
                shape = RoundedCornerShape(size = 8.dp)
            )
            .padding(
                start = 12.dp,
                top = 8.dp,
                end = 12.dp,
                bottom = 8.dp
            )

            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        TextListFieldItem(hint = "Фамилия:", text = surname)
        TextListFieldItem(hint = "Имя:", text = name)
        TextListFieldItem(hint = "Отчество:", text = patronymic)
        TextListFieldItem(hint = "Пол:", text = sex)
        TextListFieldItem(hint = "Подписка на спам:", text = subscribedCheckbox)
    }
}

@Composable
private fun ListField(
    authorizedUsers: List<Repository.AuthorizedUser>,
    state: LazyListState = rememberLazyListState()
) {
    LazyColumn(
        modifier = Modifier
            .padding(all = 10.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(6.dp),
        reverseLayout = true,
        state = state
    ) {
        items(items = authorizedUsers) { authorizedUser ->
            ListFieldItem(
                surname = authorizedUser.surname,
                name = authorizedUser.name,
                patronymic = authorizedUser.patronymic,
                sex = authorizedUser.sex,
                subscribedCheckbox = authorizedUser.subscribedCheckbox
            )
        }
    }
}

@Composable
private fun ButtonSend(
    onClick: () -> Unit,
    enabled: Boolean
) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = colorResource(id = R.color.black_1)
        ),
        shape = RoundedCornerShape(size = 8.dp),
        elevation = null,
        enabled = enabled
    ) {
        Text(
            text = "Добавить",
            style = AppStyles.textStyle.copy(color = colorResource(id = R.color.white_0))
        )
    }
}


@Composable
private fun CustomText(
    text: String,
    fontSize: TextUnit = TextUnit.Unspecified
) {
    Text(
        text = text,
        style = AppStyles.textStyle,
        fontSize = fontSize
    )
}

@Composable
private fun CheckboxField(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    text: String = "",
    fontSize: TextUnit,
    checkboxSize: Dp = 18.dp
) {
    Row(
        verticalAlignment = Alignment.Top
    ) {
        CustomCheckbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            checkedColor = colorResource(id = R.color.blue_1),
            uncheckedColor = colorResource(id = R.color.white_0),
            checkmarkColor = colorResource(id = R.color.white_0),
            shape = RoundedCornerShape(size = 4.dp),
            checkboxSize = checkboxSize
        )
        Text(
            text = text,
            style = AppStyles.textStyle,
            fontSize = fontSize,
            modifier = Modifier
                .padding(start = 10.dp)
                .offset(y = (-3).dp)
        )
    }
}

@Composable
private fun CustomCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    checkedColor: Color = MaterialTheme.colors.secondary,
    uncheckedColor: Color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
    checkmarkColor: Color = MaterialTheme.colors.surface,
    shape: Shape = RectangleShape,
    checkboxSize: Dp = 18.dp
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(checkboxSize)
    ) {
        Button(
            onClick = {
                onCheckedChange(!checked)
            },
            shape = shape,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = uncheckedColor
            ),
            elevation = null
        ) {}
        if (checked) {
            Box(
                modifier = Modifier
                    .background(
                        color = checkedColor,
                        shape = shape
                    )
                    .fillMaxSize()
            )
            Icon(
                painter = painterResource(id = R.drawable.checkmarkusedesk),
                contentDescription = null,
                tint = checkmarkColor
            )
        }
    }
}

@Composable
private fun EntryField(
    hint: String = "",
    value: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .background(
                color = colorResource(id = R.color.white_1),
                shape = RoundedCornerShape(size = 8.dp)
            )
            .border(
                width = 1.dp,
                color = colorResource(R.color.grey_1),
                shape = RoundedCornerShape(size = 8.dp)
            )
            .fillMaxWidth()
            .padding(
                start = 10.dp,
                top = 9.dp,
                bottom = 9.dp,
                end = 28.dp
            ),
        textStyle = AppStyles.textStyle,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        decorationBox = { innerTextField ->
            if (value.isEmpty()) {
                Text(
                    text = hint,
                    style = AppStyles.textStyle.copy(color = colorResource(id = R.color.grey_2))
                )
            }
            innerTextField()
        }
    )
}

@Composable
private fun DropDownField(
    value: String,
    listItems: List<String>,
    hint: String = "",
    onItemSelected: (String) -> Unit
) {
    var expanded by rememberSaveable {
        mutableStateOf(false)
    }

    Column {
        Button(
            onClick = { expanded = true },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colorResource(id = R.color.white_1)
            ),
            border = BorderStroke(
                width = 1.dp,
                color = colorResource(id = R.color.grey_1)
            ),
            shape = RoundedCornerShape(size = 8.dp),
            elevation = null,
            contentPadding = PaddingValues(
                start = 10.dp,
                top = 9.dp,
                bottom = 9.dp,
                end = 28.dp
            )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (value.isEmpty()) {
                    Text(
                        text = hint,
                        style = AppStyles.textStyle.copy(color = colorResource(id = R.color.grey_2))
                    )
                } else {
                    Text(
                        text = value,
                        style = AppStyles.textStyle.copy(color = colorResource(id = R.color.grey_2))
                    )
                }
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    tint = colorResource(id = R.color.grey_2)
                )
            }
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            listItems.forEach {
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        onItemSelected(it)
                    }) {
                    Text(text = it)
                }
            }
        }
    }
}