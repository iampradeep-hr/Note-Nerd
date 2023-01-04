package com.pradeep.notenerd.feature_note.domain.use_case

//to avoid big constructors in viewmodels
//use this class to inject in viewmodels


data class NoteUseCase(
    val getNotesUseCase: GetNotesUseCase,
    val deleteNoteUseCase: DeleteNoteUseCase,
    val addNoteUseCase: AddNoteUseCase
){

}