package digital.thon.santinho.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType.Companion.Number
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import digital.thon.santinho.ui.theme.BorderDark
import digital.thon.santinho.ui.theme.BorderLight
import kotlinx.coroutines.delay

@Composable
fun SantinhoView(innerPadding: PaddingValues) {
    val roleStates = remember { RoleStates() }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = CenterHorizontally
    ) {


        item { Text("Santinho", style = MaterialTheme.typography.headlineMedium) }

        items(Role.entries.toTypedArray()) { role ->
            RoleField(
                text = roleStates.getState(role).value,
                length = role.maxChars,
                role = role.exhibitionName,
            ) { newText, filled ->
                roleStates.getState(role).value = newText

            }
        }
    }
}

@Composable
fun RoleField(
    modifier: Modifier = Modifier,
    text: String,
    role: String,
    length: Int,
    shouldShowCursor: Boolean = false,
    shouldCursorBlink: Boolean = false,
    onOtpModified: (String, Boolean) -> Unit
) {
    LaunchedEffect(Unit) {
        if (text.length > length) {
            throw IllegalArgumentException("OTP should be $length digits")
        }
    }
    Column(horizontalAlignment = CenterHorizontally) {
        Text(modifier = Modifier.padding(bottom = 4.dp), text = role, style = MaterialTheme.typography.bodyMedium)
        BasicTextField(
            modifier = modifier,
            value = TextFieldValue(text, selection = TextRange(text.length)),
            onValueChange = {
                if (it.text.length <= length) {
                    onOtpModified.invoke(it.text, it.text.length == length)
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = Number,
                imeAction = ImeAction.Done
            ),
            decorationBox = {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    repeat(length) { index ->
                        CharacterContainer(
                            index = index,
                            text = text,
                            shouldShowCursor = shouldShowCursor,
                            shouldCursorBlink = shouldCursorBlink,
                        )
                    }
                }
            }
        )
    }
}

@Composable
internal fun CharacterContainer(
    index: Int,
    text: String,
    shouldShowCursor: Boolean,
    shouldCursorBlink: Boolean,
) {
    val isFocused = text.length == index
    val character = when {
        index < text.length -> text[index].toString()
        else -> ""
    }

    val cursorVisible = remember { mutableStateOf(shouldShowCursor) }

    LaunchedEffect(key1 = isFocused) {
        if (isFocused && shouldShowCursor && shouldCursorBlink) {
            while (true) {
                delay(800)
                cursorVisible.value = !cursorVisible.value
            }
        }
    }

    Box(contentAlignment = Alignment.Center) {
        Text(
            modifier = Modifier
                .width(36.dp)
                .border(
                    width = when {
                        isFocused -> 2.dp
                        else -> 1.dp
                    },
                    color = when {
                        isFocused -> BorderDark
                        else -> BorderLight
                    },
                    shape = RoundedCornerShape(6.dp)
                )
                .padding(2.dp),
            text = character,
            style = MaterialTheme.typography.headlineLarge,
            color = if (isFocused) BorderLight else BorderDark,
            textAlign = TextAlign.Center
        )

        AnimatedVisibility(visible = isFocused && cursorVisible.value) {
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .width(2.dp)
                    .height(24.dp)
                    .background(BorderDark)
            )
        }
    }
}

