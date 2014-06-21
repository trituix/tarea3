import java.util.*;
import java.io.*;
import javax.swing.Timer;
import java.awt.event.*;
import java.applet.AudioClip;
import java.net.URL;
 
public class MyWorld implements ActionListener {
   private PrintStream out;
 
   private ArrayList<PhysicsElement> elements;  // array to hold everything in my world.
   private MyWorldView view;   // NEW
   private Timer passingTime;   // NEW
   private double t;        // simulation time
   private double delta_t;        // in seconds
   private double refreshPeriod;  // in seconds
   private AudioClip ballcollisionsound;
   private PhysicChart chart;

   public MyWorld(){
      this(System.out);  // delta_t= 0.1[ms] and refreshPeriod=200 [ms]
   }
   public MyWorld(PrintStream output){
      out = output;
      t = 0;
      refreshPeriod = 0.0083;      // 60 [ms]
      delta_t = 0.00001;          // 0.01 [ms]
      elements = new ArrayList<PhysicsElement>();
      view = null;
      passingTime = new Timer((int)(refreshPeriod*1000), this);
      URL sound = this.getClass().getClassLoader().getResource("bolas.wav");
      ballcollisionsound = java.applet.Applet.newAudioClip(sound);
   }
 
   public void setChart(PhysicChart c)
   {
      chart = c;
   }

   public void addElement(PhysicsElement e) {
      elements.add(e);
      view.repaintView();
   }
   public void setView(MyWorldView view) {
      this.view = view;
   }

   public double getDelta_t()
   {
      return delta_t;
   }

   public void setDelta_t(double delta) {
      delta_t = delta;
   }

   public double getRefreshTime()
   {
      return refreshPeriod;
   }

   public void setRefreshPeriod (double rp) {
      refreshPeriod = rp;
      passingTime.setDelay((int)(refreshPeriod*1000)); // convert from [s] to [ms]
   }
   public void start() {
      if(passingTime.isRunning()) return;
      passingTime.start();
      view.desableMouseListener();
   }
   public void stop(){
      passingTime.stop();
      view.enableMouseListener();
   }
 
   public void actionPerformed (ActionEvent event) {  // like simulate method of Assignment 1,
      double nextStop=t+refreshPeriod;                // the arguments are attributes here.
      for (; t<nextStop; t+=delta_t){
         for (PhysicsElement e: elements)
            if (e instanceof Simulateable) {
               Simulateable s = (Simulateable) e;
               s.computeNextState(delta_t,this); // compute each element next state based on current global state
            }
         for (PhysicsElement e: elements)  // for each element update its state.
            if (e instanceof Simulateable) {
               Simulateable s = (Simulateable) e;
               s.updateState();            // update its state
            }
      }
      repaintView();
      chart.updateChart();
   }
 
   public void repaintView(){
      view.repaintView();
   }
 
   public Ball findCollidingBall(Ball me) {
      for (PhysicsElement e: elements)
         if ( e instanceof Ball) {
            Ball b = (Ball) e;
            if ((b!=me) && b.collide(me)){
             ballcollisionsound.play();
                return b;
            }
         }
      return null;
   }
 
   public ArrayList<PhysicsElement> getPhysicsElements(){
      return elements;
   }
 
   public PhysicsElement find(double x, double y) {
      for (PhysicsElement e: elements)
            if (e.contains(x,y)) return e;
      return null;
   }
   public PhysicsElement findNext(PhysicsElement element, double x, double y) {
      for (int i = elements.indexOf(element)+1; i< elements.size(); i++) { // find
          if (elements.get(i).contains(x,y))    // next element in that position ahead in array
            return elements.get(i);
      }
      for (PhysicsElement e: elements)   // There was no element in that position ahead in array
          if (e.contains(x,y)) return e; // search for an element in that position from begining.
      return element;
   }
   public SpringAttachable findAttachableElement(double x) {
      for (PhysicsElement e: elements)
         if (e instanceof SpringAttachable)
            if (e.contains(x,0)) return (SpringAttachable)e;
      return null;
   }

   public double getPotentialEnergy(){
      double totalPotencialEnergy=0;
      for (PhysicsElement e: elements)
         if (e instanceof Spring)
            totalPotencialEnergy+= e.getEnergy();
      return totalPotencialEnergy;
   }
   
   public double getKineticEnergy(){
      double totalcineticEnergy=0;
      for (PhysicsElement e: elements)
         if (e instanceof Ball)
            totalcineticEnergy+= e.getEnergy();
      return totalcineticEnergy;
   }
   
   public double getMechanicalEnergy(){
      double totalMechanicalEnergy=0;
      totalMechanicalEnergy += getKineticEnergy() + getPotentialEnergy();
      return totalMechanicalEnergy;
   }
}