package com.cs407.cardfolio

import android.graphics.drawable.Icon
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.cs407.cardfolio.ui.theme.CardfolioTheme
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AssistChip
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.cs407.cardfolio.ui.theme.AppTheme
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CardfolioTheme {
                val gradientTopColor = AppTheme.customColors.gradientTop
                val gradientBottomColor = AppTheme.customColors.gradientBottom

                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    gradientTopColor,
                                    gradientBottomColor
                                )
                            )
                        ),
                    color = Color.Transparent
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = stringResource(id = R.string.app_name),
                            style = MaterialTheme.typography.headlineLarge,
                            modifier = Modifier.padding(top = 16.dp, bottom = 24.dp)
                        )
                        Cardfolio()
                    }
                }
            }
        }
    }
}
@Composable
fun Cardfolio() {
    var name by remember { mutableStateOf("") }
    var hobby by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var isEditing by remember { mutableStateOf(true) }
    val outlineColor = MaterialTheme.colorScheme.outline
    val context = LocalContext.current

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.extraLarge,
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.download),
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .size(84.dp)
                        .clip(CircleShape)
                        .border(1.dp, outlineColor, CircleShape)
                )
                Spacer(Modifier.width(16.dp))
                Column(Modifier.weight(1f)) {
                    Text(
                        text = if (name.isBlank()) stringResource(id = R.string.card_name) else name,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = if (hobby.isBlank()) stringResource(id = R.string.card_hobby) else hobby,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                AssistChip(
                    onClick = {
                        isEditing = !isEditing
                    },
                    label = {
                        Text(
                            if (isEditing)
                                stringResource(id = R.string.status_editing)
                            else
                                stringResource(id = R.string.status_locked)
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = if (isEditing) {
                                Icons.Default.Edit
                            } else {
                                Icons.Default.Lock
                            },
                            contentDescription = null,
                            modifier = Modifier.size(size = 16.dp)
                        )
                    }
                )
            }

            HorizontalDivider(color = outlineColor)

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(stringResource(id = R.string.card_name_label)) },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                    enabled = isEditing
                )

                OutlinedTextField(
                    value = hobby,
                    onValueChange = { hobby = it },
                    label = { Text(stringResource(id = R.string.card_hobby_label)) },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { Icon(Icons.Default.Favorite, contentDescription = null) },
                    enabled = isEditing
                )

                OutlinedTextField(
                    value = age,
                    onValueChange = { input ->
                        if (input.all { it.isDigit() }) {
                            age = input
                        }
                    },
                    supportingText = {
                        if (isEditing) Text(stringResource(id = R.string.age_warning))
                    },
                    label = { Text(stringResource(id = R.string.card_age_label)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { Icon(Icons.Default.Info, contentDescription = null) },
                    enabled = isEditing
                )

                // Button row with Edit and Show buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.End)
                ) {
                    // Edit Button
                    OutlinedButton(
                        onClick = { isEditing = true },
                        enabled = !isEditing
                    ) {
                        Icon(
                            Icons.Default.Edit,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(stringResource(id = R.string.button_edit))
                    }

                    // Show Button with Input Validation
                    Button(
                        onClick = {
                            // Validate input fields
                            val missingFields = buildList {
                                if (name.isBlank()) add("Name")
                                if (hobby.isBlank()) add("Hobby")
                                if (age.isBlank()) add("Age")
                            }

                            if (missingFields.isNotEmpty()) {
                                // Show Toast with missing fields
                                val missingText = when (missingFields.size) {
                                    1 -> "Please enter: ${missingFields[0]}"
                                    2 -> "Please enter: ${missingFields[0]}, ${missingFields[1]}"
                                    else -> "Please enter: ${missingFields.joinToString(", ")}"
                                }
                                Toast.makeText(
                                    context,
                                    missingText,
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                // All fields filled - save successfully
                                isEditing = false
                                Toast.makeText(
                                    context,
                                    "Saved successfully!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        },
                        enabled = isEditing
                    ) {
                        Icon(
                            Icons.Default.Check,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(stringResource(id = R.string.button_show))
                    }
                }
            }
        }
    }
}


