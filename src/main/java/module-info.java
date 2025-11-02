module uet.project.arkanoid {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.media;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires java.desktop;
    requires java.naming;
    requires jdk.jfr;

    opens uet.project.arkanoid to javafx.fxml;
    exports uet.project.arkanoid;
    exports uet.project.arkanoid.objects;
    opens uet.project.arkanoid.objects to javafx.fxml;
    exports uet.project.arkanoid.game;
    opens uet.project.arkanoid.game to javafx.fxml;
    exports uet.project.arkanoid.utils;
    opens uet.project.arkanoid.utils to javafx.fxml;
    exports uet.project.arkanoid.base;
    opens uet.project.arkanoid.base to javafx.fxml;
}
