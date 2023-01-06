package com.pradeep.notenerd.feature_note.presentation.add_edit_note

data class NotesTxtFieldState(
    val text:String ="",
    val hint:String ="",
    val isHintVisible:Boolean = true
)