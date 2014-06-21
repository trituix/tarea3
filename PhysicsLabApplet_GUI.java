import javax.swing.*;

class PhysicsLabApplet_GUI extends JInternalFrame {
   public PhysicsLabApplet_GUI() {
      //setTitle("My Small and Nice Physics Laboratory");
      setSize(MyWorldView.WIDTH, MyWorldView.HEIGHT+50);  // height+50 to account for menu height
      MyWorld world = new MyWorld();
      MyWorldView  worldView = new MyWorldView(world);
      world.setView(worldView);
      add(worldView);
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