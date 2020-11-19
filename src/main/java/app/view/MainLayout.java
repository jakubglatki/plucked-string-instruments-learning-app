package app.view;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.server.VaadinSession;

public class MainLayout
        extends VerticalLayout implements RouterLayout, BeforeEnterObserver {

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        // Validate authenticaion in session
        // Check project source code for an example
    }

    public MainLayout() {

    }

}