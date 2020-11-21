package app.view.GroupsView;

import app.model.User.Grade.Grade;
import app.model.User.Student.Student;
import app.model.User.Teacher.Teacher;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;

import java.util.Collection;
import java.util.Collections;
import java.util.WeakHashMap;

public class GradesView
{
    private Student student;
    private Teacher teacher;
    private Grid<Grade> gradeGrid;

    public GradesView(Student student){
        this.student=student;
        addGradesGrid(student);
    }


    public Grid<Grade> getGradeGrid() {
        return gradeGrid;
    }

    private void addGradesGrid(Student student) {
        gradeGrid=new Grid<>();
        if(student.getGrades()!=null)
            gradeGrid.setItems(student.getGrades());
        gradeGrid.addColumn(Grade::getGrade).setHeader("Ocena");
        gradeGrid.addColumn(Grade::getGradeDescription).setHeader("Opis").setWidth("700px");
        gradeGrid.addColumn(Grade->Grade.getTeacher().getFirstName()+" " +Grade.getTeacher().getLastName()).setHeader("Nauczyciel");
        gradeGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_COMPACT);
    }


}
