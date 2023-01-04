package com.pradeep.notenerd.feature_note.presentation.notes

import com.pradeep.notenerd.feature_note.domain.model.Note
import com.pradeep.notenerd.feature_note.domain.util.NoteOrder

sealed class NotesEvent {
    data class OrderNote(val noteOrder: NoteOrder):NotesEvent()
    data class DeleteNote(val note: Note):NotesEvent()
    object RestoreNote:NotesEvent()
    object ToggleOrderSelection:NotesEvent()
}