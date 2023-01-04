package com.pradeep.notenerd.feature_note.domain.use_case

import com.pradeep.notenerd.feature_note.domain.model.InvalidNoteException
import com.pradeep.notenerd.feature_note.domain.model.Note
import com.pradeep.notenerd.feature_note.domain.repository.NoteRepository

class AddNoteUseCase(
    private val noteRepository: NoteRepository
) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note){
        //business logic
        if (note.title.isBlank()){
            throw InvalidNoteException("Title cannot be empty :/")
        }
        if (note.body.isBlank()){
            throw InvalidNoteException("Body cannot be empty :/")
        }
        noteRepository.insertNote(note)
    }
}