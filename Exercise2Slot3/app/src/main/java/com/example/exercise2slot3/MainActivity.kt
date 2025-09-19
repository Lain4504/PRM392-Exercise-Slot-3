package com.example.exercise2slot3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.weight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.exercise2slot3.ui.theme.Exercise2Slot3Theme
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicInteger

// Simple data class with stable id for robust deletion
data class Note(val id: Int, val text: String)

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Exercise2Slot3Theme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    NoteBoardScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteBoardScreen() {
    val idCounter = remember { AtomicInteger(0) }
    val notes = remember { mutableStateListOf<Note>() }
    val input = remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Drag state
    val dragging = remember { mutableStateOf(false) }
    val dragOverTrash = remember { mutableStateOf(false) }
    val dragPosition = remember { mutableStateOf(Offset.Zero) }
    val trashBounds = remember { mutableStateOf<Rect?>(null) }
    val lastDeleted = remember { mutableStateOf<Pair<Note, Int>?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopAppBar(title = {
                Text(text = "My Note Board", fontWeight = FontWeight.Bold, modifier = Modifier.shadow(1.dp))
            })

            Column(modifier = Modifier.padding(16.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    TextField(
                        value = input.value,
                        onValueChange = { input.value = it },
                        modifier = Modifier.weight(1f),
                        placeholder = { Text("Enter note") }
                    )

                    Button(onClick = {
                        if (input.value.isNotBlank()) {
                            val id = idCounter.incrementAndGet()
                            notes.add(0, Note(id, input.value.trim()))
                            input.value = ""
                        }
                    }, enabled = input.value.isNotBlank()) {
                        Text("+ Add")
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                LazyColumn(modifier = Modifier.weight(1f).padding(end = 8.dp)) {
                    itemsIndexed(notes) { index, note ->
                        NoteItem(
                            note = note,
                            onDelete = { noteToDelete ->
                                val pos = notes.indexOfFirst { it.id == noteToDelete.id }
                                if (pos >= 0) {
                                    lastDeleted.value = noteToDelete to pos
                                    notes.removeAt(pos)
                                    scope.launch {
                                        val result = snackbarHostState.showSnackbar("Note deleted.", actionLabel = "Undo")
                                        if (result == androidx.compose.material3.SnackbarResult.ActionPerformed) {
                                            lastDeleted.value?.let { (n, i) ->
                                                notes.add(i.coerceAtMost(notes.size), n)
                                            }
                                        }
                                    }
                                }
                            },
                            onStartDrag = { dragging.value = true },
                            onDrag = { globalOffset ->
                                dragPosition.value = globalOffset
                                val rect = trashBounds.value
                                dragOverTrash.value = rect?.contains(globalOffset) == true
                            },
                            onEndDrag = { globalOffset, draggedNote ->
                                dragging.value = false
                                if (dragOverTrash.value) {
                                    val pos = notes.indexOfFirst { it.id == draggedNote.id }
                                    if (pos >= 0) {
                                        lastDeleted.value = draggedNote to pos
                                        notes.removeAt(pos)
                                        scope.launch {
                                            val result = snackbarHostState.showSnackbar("Note deleted.", actionLabel = "Undo")
                                            if (result == androidx.compose.material3.SnackbarResult.ActionPerformed) {
                                                lastDeleted.value?.let { (n, i) ->
                                                    notes.add(i.coerceAtMost(notes.size), n)
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }

        // Trash bin at bottom-right with overlay
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
            if (dragging.value) {
                // semi-transparent highlight around trash (uses yellow tint)
                Box(modifier = Modifier
                    .padding(24.dp)
                    .size(100.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color(0x66F9A825)))
            }

            IconButton(onClick = { /* noop */ }, modifier = Modifier
                .padding(16.dp)
                .onGloballyPositioned { coords ->
                    val pos = coords.positionInWindow()
                    val size = coords.size
                    trashBounds.value = Rect(
                        pos.x,
                        pos.y,
                        pos.x + size.width,
                        pos.y + size.height
                    )
                }) {
                Icon(painter = painterResource(id = com.example.exercise2slot3.R.drawable.ic_trash), contentDescription = "Trash", tint = Color.Gray, modifier = Modifier.size(48.dp))
            }
        }

        SnackbarHost(hostState = snackbarHostState, modifier = Modifier.align(Alignment.BottomCenter))
    }
}

@Composable
fun NoteItem(
    note: Note,
    onDelete: (Note) -> Unit,
    onStartDrag: () -> Unit,
    onDrag: (Offset) -> Unit,
    onEndDrag: (Offset, Note) -> Unit
) {
    val selected = remember { mutableStateOf(false) }

    var itemWindowOffset = remember { Offset.Zero }

    Box(modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(12.dp))
        .background(if (selected.value) MaterialTheme.colorScheme.primary.copy(alpha = 0.12f) else MaterialTheme.colorScheme.surfaceVariant)
        .shadow(1.dp)
        .onGloballyPositioned { coords ->
            itemWindowOffset = coords.positionInWindow()
        }
        .pointerInput(Unit) {
            detectDragGesturesAfterLongPress(
                onDragStart = { _ -> selected.value = true; onStartDrag() },
                onDragEnd = { selected.value = false; onEndDrag(Offset.Zero, note) },
                onDragCancel = { selected.value = false },
                onDrag = { change, _ ->
                    val local = change.position
                    val global = Offset(itemWindowOffset.x + local.x, itemWindowOffset.y + local.y)
                    onDrag(global)
                    change.consume()
                }
            )
        }
        .padding(12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = note.text, modifier = Modifier.weight(1f))
            IconButton(onClick = { onDelete(note) }) {
                Text("🗑")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NoteBoardPreview() {
    Exercise2Slot3Theme {
        NoteBoardScreen()
    }
}
package com.example.exercise2slot3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.weight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.exercise2slot3.ui.theme.Exercise2Slot3Theme
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicInteger

// Simple data class with stable id for robust deletion
data class Note(val id: Int, val text: String)

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Exercise2Slot3Theme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    NoteBoardScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteBoardScreen() {
    val idCounter = remember { AtomicInteger(0) }
    val notes = remember { mutableStateListOf<Note>() }
    val input = remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Drag state
    val dragging = remember { mutableStateOf(false) }
    val dragOverTrash = remember { mutableStateOf(false) }
    val dragPosition = remember { mutableStateOf(Offset.Zero) }
    val trashBounds = remember { mutableStateOf<androidx.compose.ui.geometry.Rect?>(null) }
    val lastDeleted = remember { mutableStateOf<Pair<Note, Int>?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopAppBar(title = {
                Text(text = "My Note Board", fontWeight = FontWeight.Bold, modifier = Modifier.shadow(1.dp))
            })

            Column(modifier = Modifier.padding(16.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    TextField(
                        value = input.value,
                        onValueChange = { input.value = it },
                        modifier = Modifier.weight(1f),
                        placeholder = { Text("Enter note") }
                    )

                    Button(onClick = {
                        if (input.value.isNotBlank()) {
                            val id = idCounter.incrementAndGet()
                            notes.add(0, Note(id, input.value.trim()))
                            input.value = ""
                        }
                    }, enabled = input.value.isNotBlank()) {
                        Text("+ Add")
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                LazyColumn(modifier = Modifier.weight(1f).padding(end = 8.dp)) {
                    itemsIndexed(notes) { index, note ->
                        NoteItem(
                            note = note,
                            onDelete = { noteToDelete ->
                                val pos = notes.indexOfFirst { it.id == noteToDelete.id }
                                if (pos >= 0) {
                                    lastDeleted.value = noteToDelete to pos
                                    notes.removeAt(pos)
                                    scope.launch {
                                        val result = snackbarHostState.showSnackbar("Note deleted.", actionLabel = "Undo")
                                        if (result == androidx.compose.material3.SnackbarResult.ActionPerformed) {
                                            lastDeleted.value?.let { (n, i) ->
                                                notes.add(i.coerceAtMost(notes.size), n)
                                            }
                                        }
                                    }
                                }
                            },
                            onStartDrag = { dragging.value = true },
                            onDrag = { globalOffset ->
                                dragPosition.value = globalOffset
                                val rect = trashBounds.value
                                dragOverTrash.value = rect?.contains(globalOffset) == true
                            },
                            onEndDrag = { globalOffset, draggedNote ->
                                dragging.value = false
                                if (dragOverTrash.value) {
                                    val pos = notes.indexOfFirst { it.id == draggedNote.id }
                                    if (pos >= 0) {
                                        lastDeleted.value = draggedNote to pos
                                        notes.removeAt(pos)
                                        scope.launch {
                                            val result = snackbarHostState.showSnackbar("Note deleted.", actionLabel = "Undo")
                                            if (result == androidx.compose.material3.SnackbarResult.ActionPerformed) {
                                                lastDeleted.value?.let { (n, i) ->
                                                    notes.add(i.coerceAtMost(notes.size), n)
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }

        // Trash bin at bottom-right with overlay
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
            if (dragging.value) {
                // semi-transparent highlight around trash (uses yellow tint)
                Box(modifier = Modifier
                    .padding(24.dp)
                    .size(100.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color(0x66F9A825)))
            }

            IconButton(onClick = { /* noop */ }, modifier = Modifier
                .padding(16.dp)
                .onGloballyPositioned { coords ->
                    val pos = coords.positionInWindow()
                    val size = coords.size
                    trashBounds.value = androidx.compose.ui.geometry.Rect(
                        pos.x,
                        pos.y,
                        pos.x + size.width,
                        pos.y + size.height
                    )
                }) {
                Icon(painter = painterResource(id = com.example.exercise2slot3.R.drawable.ic_trash), contentDescription = "Trash", tint = Color.Gray, modifier = Modifier.size(48.dp))
            }
        }

        SnackbarHost(hostState = snackbarHostState, modifier = Modifier.align(Alignment.BottomCenter))
    }
}

@Composable
fun NoteItem(
    note: Note,
    onDelete: (Note) -> Unit,
    onStartDrag: () -> Unit,
    onDrag: (Offset) -> Unit,
    onEndDrag: (Offset, Note) -> Unit
) {
    val selected = remember { mutableStateOf(false) }

    var itemWindowOffset = remember { Offset.Zero }

    Box(modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(12.dp))
        .background(if (selected.value) MaterialTheme.colorScheme.primary.copy(alpha = 0.12f) else MaterialTheme.colorScheme.surfaceVariant)
        .shadow(1.dp)
        .onGloballyPositioned { coords ->
            itemWindowOffset = coords.positionInWindow()
        }
        .pointerInput(Unit) {
            detectDragGesturesAfterLongPress(
                onDragStart = { _ -> selected.value = true; onStartDrag() },
                onDragEnd = { selected.value = false; onEndDrag(Offset.Zero, note) },
                onDragCancel = { selected.value = false },
                onDrag = { change, _ ->
                    val local = change.position
                    val global = Offset(itemWindowOffset.x + local.x, itemWindowOffset.y + local.y)
                    onDrag(global)
                    change.consume()
                }
            )
        }
        .padding(12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = note.text, modifier = Modifier.weight(1f))
            IconButton(onClick = { onDelete(note) }) {
                Text("🗑")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NoteBoardPreview() {
    Exercise2Slot3Theme {
        NoteBoardScreen()
    }
}

package com.example.exercise2slot3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.exercise2slot3.ui.theme.Exercise2Slot3Theme
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicInteger

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Exercise2Slot3Theme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    @Composable
                    fun NoteItem(
                        note: Any,
                        onDelete: (Any) -> Unit,
                        onStartDrag: () -> Unit,
                        onDrag: (Offset) -> Unit,
                        onEndDrag: (Offset, Any) -> Unit
                    ) {
                        // Using Any to avoid repeating the inner data class declaration; MainActivity passes the Note object
                        val selected = remember { mutableStateOf(false) }

                        var itemWindowOffset = remember { Offset.Zero }

                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (selected.value) MaterialTheme.colorScheme.primary.copy(alpha = 0.12f) else MaterialTheme.colorScheme.surfaceVariant)
                            .shadow(1.dp)
                            .onGloballyPositioned { coords ->
                                itemWindowOffset = coords.positionInWindow()
                            }
                            .pointerInput(Unit) {
                                detectDragGesturesAfterLongPress(
                                    onDragStart = { _ -> selected.value = true; onStartDrag() },
                                    onDragEnd = { selected.value = false; onEndDrag(Offset.Zero, note) },
                                    onDragCancel = { selected.value = false },
                                    onDrag = { change, _ ->
                                        val local = change.position
                                        val global = Offset(itemWindowOffset.x + local.x, itemWindowOffset.y + local.y)
                                        onDrag(global)
                                        change.consume()
                                    }
                                )
                            }
                            .padding(12.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(text = (note as? Any).toString(), modifier = Modifier.weight(1f))
                                IconButton(onClick = { onDelete(note) }) {
                                    Text("🗑")
                                }
                            }
                        }
                    }

                        Button(onClick = {
                            if (input.value.isNotBlank()) {
                                val id = idCounter.incrementAndGet()
                                notes.add(0, Note(id, input.value.trim()))
                                input.value = ""
                            }
                        }, enabled = input.value.isNotBlank()) {
                            Text("+ Add")
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    LazyColumn(modifier = Modifier.weight(1f).padding(end = 8.dp)) {
                        itemsIndexed(notes) { index, note ->
                            NoteItem(
                                note = note,
                                onDelete = { noteToDelete ->
                                    val pos = notes.indexOfFirst { it.id == noteToDelete.id }
                                    if (pos >= 0) {
                                        lastDeleted.value = noteToDelete to pos
                                        notes.removeAt(pos)
                                        scope.launch {
                                            val result = snackbarHostState.showSnackbar("Note deleted.", actionLabel = "Undo")
                                            if (result == androidx.compose.material3.SnackbarResult.ActionPerformed) {
                                                lastDeleted.value?.let { (n, i) ->
                                                    notes.add(i.coerceAtMost(notes.size), n)
                                                }
                                            }
                                        }
                                    }
                                },
                                onStartDrag = { dragging.value = true },
                                onDrag = { globalOffset ->
                                    dragPosition.value = globalOffset
                                    val rect = trashBounds.value
                                    dragOverTrash.value = rect?.contains(globalOffset) == true
                                },
                                onEndDrag = { globalOffset, draggedNote ->
                                    dragging.value = false
                                    if (dragOverTrash.value) {
                                        val pos = notes.indexOfFirst { it.id == draggedNote.id }
                                        if (pos >= 0) {
                                            lastDeleted.value = draggedNote to pos
                                            notes.removeAt(pos)
                                            scope.launch {
                                                val result = snackbarHostState.showSnackbar("Note deleted.", actionLabel = "Undo")
                                                if (result == androidx.compose.material3.SnackbarResult.ActionPerformed) {
                                                    lastDeleted.value?.let { (n, i) ->
                                                        notes.add(i.coerceAtMost(notes.size), n)
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }

            // Trash bin at bottom-right with overlay
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
                if (dragging.value) {
                    // semi-transparent highlight around trash (uses yellow tint)
                    Box(modifier = Modifier
                        .padding(24.dp)
                        .size(100.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color(0x66F9A825)))
                }

                IconButton(onClick = { /* noop */ }, modifier = Modifier
                    .padding(16.dp)
                    .onGloballyPositioned { coords ->
                        val pos = coords.positionInWindow()
                        val size = coords.size
                        trashBounds.value = androidx.compose.ui.geometry.Rect(
                            pos.x,
                            pos.y,
                            pos.x + size.width,
                            pos.y + size.height
                        )
                    }) {
                    Icon(painter = painterResource(id = com.example.exercise2slot3.R.drawable.ic_trash), contentDescription = "Trash", tint = Color.Gray, modifier = Modifier.size(48.dp))
                }
            }

            SnackbarHost(hostState = snackbarHostState, modifier = Modifier.align(Alignment.BottomCenter))
        }
    }
    onEndDrag: (Offset) -> Unit
) {
    val selected = remember { mutableStateOf(false) }

    var itemWindowOffset = remember { Offset.Zero }

    Box(modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(12.dp))
        .background(MaterialTheme.colorScheme.surfaceVariant)
        .shadow(1.dp)
        .onGloballyPositioned { coords ->
            itemWindowOffset = coords.positionInWindow()
        }
        .pointerInput(Unit) {
            detectDragGesturesAfterLongPress(
                onDragStart = { _ -> selected.value = true; onStartDrag() },
                onDragEnd = { selected.value = false; onEndDrag(Offset.Zero) },
                onDragCancel = { selected.value = false },
                onDrag = { change, _ ->
                    // compute global position: window offset + local pointer position
                    val local = change.position
                    val global = Offset(itemWindowOffset.x + local.x, itemWindowOffset.y + local.y)
                    onDrag(global)
                    change.consume()
                }
            )
        }
        .padding(12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = text, modifier = Modifier.weight(1f))
            IconButton(onClick = onDelete) {
                Text("🗑")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NoteBoardPreview() {
    Exercise2Slot3Theme {
        NoteBoardScreen()
    }
}