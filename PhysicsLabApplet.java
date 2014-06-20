import javax.swing.*;
import java.awt.Container;
import java.awt.Label;
import java.awt.GridLayout;

public class PhysicsLabApplet extends JApplet{
   public void init() {
      setLayout(new GridLayout(2, 1));
      PhysicsLab_GUI gui = new PhysicsLab_GUI();
      JPanel test = new JPanel();
      test.setLayout(new GridLayout(1, 3));
      test.add(new Label("Hola mundo"));
      test.add(new Label("Hola mundo"));
      test.add(new Label("Hola mundo"));
      test.setVisible(true);
      gui.setVisible(true);
      add(gui);
      add(test);
   }
}