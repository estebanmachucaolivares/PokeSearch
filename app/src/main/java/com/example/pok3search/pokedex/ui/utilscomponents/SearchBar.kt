
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import com.example.pok3search.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pok3search.ui.theme.searchBackgroundColor

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    searchText: String,
    onSearchTextChanged: (String) -> Unit,
    onSearchSubmit: () -> Unit,
    onClearSearch: () -> Unit
) {

    var isSearching by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val shape: Shape = RoundedCornerShape(30.dp)
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    var isTextFieldFocused by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .clip(shape)
            .background(searchBackgroundColor)
            .clickable {
                focusRequester.requestFocus()
            }
            .focusable(true) // Para permitir el enfoque
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    focusManager.clearFocus()
                    keyboardController?.hide()
                }
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .height(30.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            BasicTextField(
                value = searchText,
                onValueChange = {
                    onSearchTextChanged(it)
                    isSearching = it.isNotBlank()
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        onSearchSubmit()
                        focusManager.clearFocus()
                    }
                ),
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    color = Color.Black
                ),
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .padding(top = 3.dp)
                    .focusRequester(focusRequester)
                    .onFocusChanged {
                        isTextFieldFocused = it.isFocused
                    }

            )

            if(isTextFieldFocused && searchText.isEmpty()){
                Icon(
                    painter = painterResource(id = R.drawable.keyboard_hide),
                    contentDescription = "Clear",
                    modifier = Modifier
                        .clickable {
                            onClearSearch()
                            isSearching = false
                            focusManager.clearFocus()
                            keyboardController?.hide()
                        }
                    , tint = Color.Black

                )
            }else if (isSearching) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "Clear",
                    modifier = Modifier
                        .clickable {
                            onClearSearch()
                            isSearching = false
                            focusManager.clearFocus()
                            keyboardController?.hide()
                        }
                    , tint = Color.Black

                )
            } else {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    modifier = Modifier
                        .clickable {
                            onSearchSubmit()
                            focusManager.clearFocus()
                            keyboardController?.hide()
                        },
                    tint = Color.Black
                )
            }
        }
    }
}
