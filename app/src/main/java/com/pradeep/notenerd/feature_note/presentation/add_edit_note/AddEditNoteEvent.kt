package com.pradeep.notenerd.feature_note.presentation.add_edit_note

import androidx.compose.ui.focus.FocusState

sealed class AddEditNoteEvent {
    data class EnteredTitle(
        val value:String
    ):AddEditNoteEvent()

    data class ChangeTitleFocus(
        val focusState: FocusState
    ):AddEditNoteEvent()

    data class EnteredBody(
        val value:String
    ):AddEditNoteEvent()

    data class ChangeBodyFocus(
        val focusState: FocusState
    ):AddEditNoteEvent()

    data class ChangeColor(val color: Int):AddEditNoteEvent()

    object SaveNote:AddEditNoteEvent()

}