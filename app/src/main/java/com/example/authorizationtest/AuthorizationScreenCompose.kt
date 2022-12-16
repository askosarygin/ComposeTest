package com.example.authorizationtest

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.*
import com.example.composetest.R
import com.skydoves.landscapist.glide.GlideImage
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
            .fillMaxSize()
            .background(color = colorResource(id = R.color.grey_3))
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
                    focusManager.clearFocus()
                },
                enabled = model.surNameField.isNotBlank()
                        && model.nameField.isNotBlank() &&
                        model.patronymicField.isNotBlank() &&
                        model.sexField.isNotBlank()
            )
        }
        ListField(
            list = model.authorizedUsersList,
            state = listFieldState
        )

    }
}

@Composable
private fun AvatarImage(
    size: Dp = 64.dp,
    id: Long
) {
    val numberOfCat = 250L + id
    GlideImage(
        imageModel = "https://placekitten.com/g/${numberOfCat}/300",
        modifier = Modifier
            .size(size = size)
            .clip(shape = CircleShape)
    )
}

@SuppressLint("UnusedCrossfadeTargetStateParameter")
@Composable
private fun ListFieldItem(
    id: Long = 0L,
    surname: String,
    name: String,
    patronymic: String,
    sex: String,
    subscribedCheckbox: String,
    timeOfRegistration: String = ""
) {
    var animated by rememberSaveable {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier
            .clip(shape = RectangleShape),
        contentAlignment = Alignment.Center
    ) {
        val alphaItem = remember {
            mutableStateOf(if (animated) 1f else 0f)
        }
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(color = AppStyles.white_1.copy(alpha = alphaItem.value))
                .padding(
                    start = 12.dp,
                    top = 8.dp,
                    bottom = 8.dp,
                    end = 12.dp
                )
                .alpha(alphaItem.value),
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            AvatarImage(
                id = id,
                size = 48.dp
            )
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "$surname $name $patronymic",
                    style = AppStyles.textStyle.copy(fontSize = 18.sp),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Text(
                    text = "Пол $sex, подписка $subscribedCheckbox",
                    style = AppStyles.textStyle,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
            Text(
                text = timeOfRegistration,
                style = AppStyles.textStyle.copy(
                    fontSize = 10.sp,
                    color = colorResource(id = R.color.grey_2)
                )
            )
        }

        if (!animated) {
            val itemHeightDp = 64.dp
            val itemHeight = with(LocalDensity.current) {
                itemHeightDp.toPx()
            }
            val radiusCircle = remember {
                Animatable(initialValue = itemHeight / 8)
            }

            val radiusCircleMax = itemHeight / 8 * 27
            val itemCenter = itemHeight / 2

            val circlePositionY = remember {
                Animatable(initialValue = 0f - radiusCircle.value)
            }

            val alphaCircle = remember {
                Animatable(initialValue = 1f)
            }

            LaunchedEffect(
                key1 = Unit,
                block = {
                    circlePositionY.animateTo(
                        targetValue = itemCenter,
                        animationSpec = tween(100)
                    )
                    if (circlePositionY.value == itemCenter) {
                        radiusCircle.animateTo(
                            targetValue = radiusCircleMax,
                            animationSpec = tween(300)
                        )
                    }
                    if (radiusCircle.value == radiusCircleMax) {
                        alphaItem.value = 1f
                        alphaCircle.animateTo(
                            targetValue = 0.0f,
                            animationSpec = tween(300)
                        )
                    }

                })

            if (radiusCircle.value < radiusCircleMax || alphaCircle.value > 0.0f) {
                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp),
                    onDraw = {
                        drawCircle(
                            color = AppStyles.white_1,
                            radius = radiusCircle.value,
                            center = Offset(
                                x = size.width / 2,
                                y = circlePositionY.value
                            ),
                            alpha = alphaCircle.value
                        )
                    })
            } else {
                animated = true
            }
        }
    }
}

@SuppressLint("UnusedCrossfadeTargetStateParameter")
@Composable
private fun ListField(
    list: SnapshotStateList<AuthorizedUser>,
    state: LazyListState = rememberLazyListState()
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        reverseLayout = true,
        state = state
    ) {
        itemsIndexed(items = list) { index, authorizedUser ->
            if (index != 0) {
                Divider(color = colorResource(id = R.color.grey_1))
            }
            ListFieldItem(
                id = authorizedUser.id,
                surname = authorizedUser.surname,
                name = authorizedUser.name,
                patronymic = authorizedUser.patronymic,
                sex = authorizedUser.sex,
                subscribedCheckbox = if(authorizedUser.subscribedCheckbox) "подписан" else "не подписан",
                timeOfRegistration = authorizedUser.timeOfRegistration
            )
        }
    }
}

@Composable
private fun ButtonSend(
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    Crossfade(
        targetState = enabled,
        animationSpec = tween(durationMillis = 500)
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colorResource(id = R.color.black_1)
            ),
            shape = RoundedCornerShape(size = 8.dp),
            elevation = null,
            enabled = it
        ) {
            Text(
                text = "Добавить",
                style = AppStyles.textStyle.copy(color = colorResource(id = R.color.white_0))
            )
        }
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
    Crossfade(
        targetState = checked,
        animationSpec = tween(400)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(checkboxSize)
        ) {
            Button(
                onClick = {
                    onCheckedChange(!it)
                },
                shape = shape,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = uncheckedColor
                ),
                elevation = null
            ) {}
            if (it) {
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
            AnimatedVisibility(
                visible = value.isEmpty(),
                enter = slideInHorizontally(
                    animationSpec = spring(
                        stiffness = Spring.StiffnessHigh,
                        visibilityThreshold = IntOffset.VisibilityThreshold
                    ),
                    initialOffsetX = { it / 10 }
                ),
                exit = slideOutHorizontally(
                    animationSpec = spring(
                        stiffness = Spring.StiffnessHigh,
                        visibilityThreshold = IntOffset.VisibilityThreshold
                    ),
                    targetOffsetX = { it / 10 }
                ) + fadeOut(
                    animationSpec = spring(stiffness = Spring.StiffnessMedium)
                )
            ) {
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
                end = 13.dp
            )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Crossfade(
                    targetState = value.ifEmpty { hint },
                    animationSpec = tween(400)
                ) {
                    Text(
                        text = it,
                        style = AppStyles.textStyle.copy(color = colorResource(id = R.color.grey_2))
                    )
                }

                Icon(
                    painter = painterResource(id = R.drawable.arrowdropdownusedesk),
                    contentDescription = null,
                    tint = colorResource(id = R.color.grey_2),
                    modifier = Modifier.size(width = 8.dp, height = 5.dp)
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