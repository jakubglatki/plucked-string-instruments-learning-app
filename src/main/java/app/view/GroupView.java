package app.view;

import app.model.User.User;
import app.model.User.UserRepository;
import app.model.User.UserType;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value="group", layout = InternalLayout.class)
public class GroupView extends VerticalLayout {

    @Autowired
    private UserRepository userRepository;
    private Button refreshButton;
    private GroupView currentGroupView;
    private Label infoLabel;

    public GroupView(){
        infoLabel = new Label();
        add(infoLabel);
        refreshGroup();
    }

    @Override
    public void onAttach(AttachEvent event){
        refreshGroup();
    }

    public void refreshGroup() {
            VaadinSession session=VaadinSession.getCurrent();
            try {
                User user = userRepository.findByMail(session.getAttribute("user").toString());
                if(user.getUserType()== UserType.TEACHER)
                    infoLabel.setText("Welcome "+ user.getFirstName() + " teacher!");
                else if(user.getUserType()==UserType.STUDENT)
                    infoLabel.setText("Welcome "+ user.getFirstName() + " student!");
            }
            catch (Exception exception){infoLabel.setText("Login to access your groups");}
    }
}
