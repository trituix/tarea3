import javax.swing.*;
import java.awt.Container;
import java.awt.Label;
import java.awt.GridLayout;
import java.awt.event.*;

public class PhysicsLabApplet extends JApplet{
   public void init() {
      PhysicsLabApplet_GUI gui = new PhysicsLabApplet_GUI();
      gui.setVisible(true);
      add(gui);
   }

   class PhysicsLabApplet_GUI extends JInternalFrame {
      public PhysicsLabApplet_GUI() {
         setSize(MyWorldView.WIDTH, MyWorldView.HEIGHT+50);  // height+50 to account for menu height
         MyWorld world = new MyWorld();
         MyWorldView  worldView = new MyWorldView(world);
         world.setView(worldView);
         world.setRefreshPeriod(Double.parseDouble(getParameter("refreshTime")));
         world.setDelta_t(Double.parseDouble(getParameter("deltaTime")));
         PhysicChart chart = new PhysicChart("Un titulo", world);
         chart.setVisible(true);
         chart.setMaxPlotTime(Integer.parseInt(getParameter("maxPlotTime")));
         world.setChart(chart);
         JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, worldView, chart);
         splitPane.setDividerLocation(0.5);
         add(splitPane);
         LabMenuListenerApplet menuListener = new LabMenuListenerApplet(world);
         setJMenuBar(createLabMenuBar(menuListener));
      }

      public JMenuBar createLabMenuBar(LabMenuListenerApplet menu_l) {
         JMenuBar mb = new JMenuBar();

         JMenu menu = new JMenu ("Configuration");
         mb.add(menu);
         JMenu subMenu = new JMenu("Insert");
         menu.add(subMenu);

         JMenuItem menuItem = new JMenuItem("Ball");
         menuItem.addActionListener(menu_l);
         subMenu.add(menuItem);
         menuItem = new JMenuItem("Fixed Hook");
         menuItem.addActionListener(menu_l);
         subMenu.add(menuItem);
         menuItem = new JMenuItem("Spring");
         menuItem.addActionListener(menu_l);
         subMenu.add(menuItem);
         menuItem = new JMenuItem("Oscillator");
         menuItem.addActionListener(menu_l);
         subMenu.add(menuItem);
         menuItem = new JMenuItem("My scenario");
         menuItem.addActionListener(menu_l);
         subMenu.add(menuItem);

         menu = new JMenu("MyWorld");
         mb.add(menu);
         menuItem = new JMenuItem("Start");
         menuItem.addActionListener(menu_l);
         menu.add(menuItem);
         menuItem = new JMenuItem("Stop");
         menuItem.addActionListener(menu_l);
         menu.add(menuItem);
         subMenu = new JMenu("Simulator");
         menuItem = new JMenuItem("Delta time");
         menuItem.addActionListener(menu_l);
         subMenu.add(menuItem);
         menuItem = new JMenuItem("View Refresh time");
         menuItem.addActionListener(menu_l);
         subMenu.add(menuItem);
         menu.add(subMenu);
         return mb;
      }
   }

   class LabMenuListenerApplet implements ActionListener {
      private MyWorld  world;
      public LabMenuListenerApplet (MyWorld  w){
         world = w;
      }
      public void actionPerformed(ActionEvent e) {
         JMenuItem menuItem = (JMenuItem)(e.getSource());
         String text = menuItem.getText();

         // Actions associated to main manu options
         if (text.equals("My scenario")) { 
            int fhookQty = Integer.parseInt(getParameter("fixedHookNum"));
            int ballQty = Integer.parseInt(getParameter("ballNum"));
            int osQty = Integer.parseInt(getParameter("oscillatorNum"));
            int count;
            for(count = 0; count<ballQty; count++)
            {
               //Masa, Radio, Posicion, Velocidad
               String ball = "ball." + (count+1);
               String[] data = getParameter(ball).split(";");
               double mass = Double.parseDouble(data[0]);
               double radius = Double.parseDouble(data[1]);
               double position = Double.parseDouble(data[2]);
               double speed = Double.parseDouble(data[3]);
               world.addElement(new Ball(mass, radius, position, speed));
            }
            for(count = 0; count<fhookQty; count++)
            {
               String fHook = "fixedHook." + (count+1);
               String[] data = getParameter(fHook).split(";");
               double position = Double.parseDouble(data[0]);
               world.addElement(new FixedHook(position));
            }
            for(count = 0; count<osQty; count++)
            {
               String os = "oscillator." + (count + 1);
               String[] data = getParameter(os).split(";");
               double center = Double.parseDouble(data[0]);
               double amplitude = Double.parseDouble(data[1]);
               double frequency = Double.parseDouble(data[2]);
               world.addElement(new Oscillator(center, amplitude, frequency));
            }
         }
         if (text.equals("Ball"))
           world.addElement(new Ball(1.0, 0.1, 1.2, 0));
         if (text.equals("Fixed Hook"))
           world.addElement(new FixedHook(0.5));
         if (text.equals("Spring"))
           world.addElement(new Spring(0.5, 5));
         if (text.equals("Oscillator"))
           world.addElement(new Oscillator(0.5,0.1,0.5));

         // Actions associated to MyWorld submenu
         if (text.equals("Start"))   /* to be coded */
           world.start();

         if (text.equals("Stop"))    /* to be coded */
           world.stop();

         if (text.equals("Delta time")) {
            String data = JOptionPane.showInputDialog("Enter delta t [s]");
            world.setDelta_t(Double.parseDouble(data));
         }
         if (text.equals("View Refresh time")) {
            // to be coded
            String data = JOptionPane.showInputDialog("Enter view refresh time [s]");
            world.setRefreshPeriod(Double.parseDouble(data));
         }
      }
   }
}