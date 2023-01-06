package com.pradeep.notenerd.feature_note.presentation.add_edit_note

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pradeep.notenerd.feature_note.domain.model.InvalidNoteException
import com.pradeep.notenerd.feature_note.domain.model.Note
import com.pradeep.notenerd.feature_note.domain.use_case.NoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val noteUseCase: NoteUseCase,
    savedStateHandle: SavedStateHandle
):ViewModel(){
    private var currentNoteId:Int?=null

    //separation of events for each fileds on screen to prevent whole screen recomposition
    private val _noteTitle = mutableStateOf(NotesTxtFieldState(
        hint = "Title goes here..."
    ))
    val noteTitle:State<NotesTxtFieldState> = _noteTitle

    private val _noteBody = mutableStateOf(NotesTxtFieldState(
        "Body goes here..."
    ))
    val noteBody:State<NotesTxtFieldState> = _noteBody

    private val _noteColor = mutableStateOf<Int>(Note.noteColors.random().toArgb())
    val noteColor:State<Int> = _noteColor

    private val _eventFlow= MutableSharedFlow<UiEvent>()
    val eventFlow=_eventFlow.asSharedFlow()

    sealed class UiEvent{
        data class ShowSnackBar(val message:String):UiEvent()
        object SaveNote:UiEvent()
    }

    init {
        savedStateHandle.get<Int>("noteId").let {
            if (it!=-1){
                viewModelScope.launch {
                    if (it != null) {
                        noteUseCase.getNoteById(it)?.also {
                            currentNoteId=it.id
                            _noteTitle.value=noteTitle.value.copy(
                                text = it.title,
                                isHintVisible = false
                            )
                            _noteBody.value=noteBody.value.copy(
                                text = it.body,
                                isHintVisible = false
                            )
                            _noteColor.value=noteColor.value
                        }
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditNoteEvent){
        when(event){
            is AddEditNoteEvent.EnteredTitle->{
                _noteTitle.value=noteTitle.value.copy(
                    text = event.value
                )
            }
            is AddEditNoteEvent.EnteredBody->{
                _noteBody.value=noteBody.value.copy(
                    text = event.value
                )
            }
            is AddEditNoteEvent.ChangeBodyFocus->{
                _noteBody.value=noteBody.value.copy(
                    isHintVisible = !event.focusState.isFocused && noteBody.value.text.isBlank()
                )
            }
            is AddEditNoteEvent.ChangeTitleFocus->{
                _noteTitle.value=noteTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused && noteTitle.value.text.isBlank()
                )
            }
            is AddEditNoteEvent.ChangeColor->{
                _noteColor.value=event.color
            }
            is AddEditNoteEvent.SaveNote->{
                viewModelScope.launch {
                    try {
                        noteUseCase.addNoteUseCase(
                            Note(
                                title = noteTitle.value.text,
                                body = noteBody.value.text,
                                timeStamp = System.currentTimeMillis(),
                                id = currentNoteId,
                                color = noteColor.value
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveNote)
                    }catch (e:InvalidNoteException){
                        _eventFlow.emit(
                            UiEvent.ShowSnackBar(
                                e.localizedMessage?:"Something went wrong :/"
                            )
                        )
                    }
                }
            }
        }
    }



}