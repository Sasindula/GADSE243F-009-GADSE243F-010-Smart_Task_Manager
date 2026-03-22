package controller;

import dao.NoteDAO;
import model.Note;

public class NoteController {

    public boolean saveNote(String title, String content, int userId){

        if(title.isEmpty() || content.isEmpty()){
            return false;
        }

        Note note = new Note(title, content, userId);
        return NoteDAO.addNote(note);
    }
}
