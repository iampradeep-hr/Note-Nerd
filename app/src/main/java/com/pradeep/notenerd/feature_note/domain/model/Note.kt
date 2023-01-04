package com.pradeep.notenerd.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pradeep.notenerd.ui.theme.*

@Entity
data class Note(
    @PrimaryKey
    val id:Int?=null,
    val title:String,
    val body:String,
    val timeStamp:Long,
    val color:Int
){
   companion object{
       //list of colors for note
       val noteColors= listOf(peach, mimosa, seafome_green, periwinkle, teal)
   }
}

class InvalidNoteException(message:String):Exception(message){

}