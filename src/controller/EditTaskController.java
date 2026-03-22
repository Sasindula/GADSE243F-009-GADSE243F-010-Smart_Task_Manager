package controller;

import dao.EditTaskDAO;
import model.EditTask;

public class EditTaskController {

    EditTaskDAO dao = new EditTaskDAO();

    public boolean updateTask(EditTask task) {
        return dao.updateTask(task);
    }
}
