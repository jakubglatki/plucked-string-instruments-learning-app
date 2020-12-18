package app.view.LessonView;

import app.model.Group.Group;
import app.model.Group.GroupRepository;
import app.model.Lesson.Lesson;
import app.model.User.User;
import com.vaadin.flow.component.contextmenu.MenuItem;

public class ChordsBarView extends TabsView {

    private MenuItem c;
    private MenuItem cm;
    private MenuItem c7;
    private MenuItem d;
    private MenuItem dm;
    private MenuItem d7;
    private MenuItem e;
    private MenuItem e7;
    private MenuItem f;
    private MenuItem fm;
    private MenuItem f7;
    private MenuItem g;
    private MenuItem gm;
    private MenuItem g7;
    private MenuItem a;
    private MenuItem a7;
    private MenuItem am;
    private MenuItem bm;
    private MenuItem b7;
    private MenuItem b;

    public ChordsBarView(GroupRepository groupRepository, Group group, Lesson lesson, User user) {
        super(groupRepository, group, lesson, user);
    }
}
