package com.pradeep.notenerd.feature_note.domain.use_case

import com.pradeep.notenerd.feature_note.domain.model.Note
import com.pradeep.notenerd.feature_note.domain.repository.NoteRepository

class DeleteNoteUseCase(
    private val noteRepository: NoteRepository
) {

    suspend operator fun invoke(
        note:Note
    ){
        noteRepository.deleteNote(note)
    }
}