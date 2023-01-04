package com.pradeep.notenerd.feature_note.domain.use_case

import com.pradeep.notenerd.feature_note.domain.model.Note
import com.pradeep.notenerd.feature_note.domain.repository.NoteRepository
import com.pradeep.notenerd.feature_note.domain.util.NoteOrder
import com.pradeep.notenerd.feature_note.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


// Basically business logic is written here

class GetNotesUseCase(
    private val repository: NoteRepository
) {
    operator fun invoke(
        //way of ordering i want
        noteOrder: NoteOrder=NoteOrder.Date(OrderType.Descending)
    ): Flow<List<Note>>{
        return repository.getAllNotes().map { notes->
            when(noteOrder.orderType){
                is OrderType.Ascending ->{
                    when(noteOrder){
                        is NoteOrder.Title -> notes.sortedBy { it.title.lowercase()}
                        is NoteOrder.Date -> notes.sortedBy { it.timeStamp }
                        is NoteOrder.Color -> notes.sortedBy { it.color }
                    }
                }
                is OrderType.Descending->{
                    when(noteOrder){
                        is NoteOrder.Title -> notes.sortedByDescending { it.title.lowercase()}
                        is NoteOrder.Date -> notes.sortedByDescending { it.timeStamp }
                        is NoteOrder.Color -> notes.sortedByDescending { it.color }
                    }
                }
            }
        }
    }
}