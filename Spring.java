import java.awt.*;

public class Spring extends PhysicsElement {
   private static int id=0;  // Spring identification
   private final double restLength;
   private final double stiffness;
   private SpringAttachable a_end, b_end;
   private double aLoosePosition, bLoosePosition;
   
   private SpringView view;

   private Spring(){   // nobody can create a block without state
      this(0,0);
   }
   public Spring(double restLength, double stiffness){
      super(id++);
      this.restLength = restLength;
      this.stiffness = stiffness;
      a_end = b_end = null;
      aLoosePosition=0;
      bLoosePosition=restLength;
      view = new SpringView(this);
   }
   public void attachAend (SpringAttachable sa) {  // note: we attach a spring to a ball, 
      if(a_end!=null)                              // not the other way around.
        a_end.detachSpring(this);
      a_end = sa;                     
      sa.attachSpring(this);
   }
   public void attachBend (SpringAttachable sa) {  // note: we attach a spring to a ball, 
      if(b_end!=null)                              // not the other way around.
        b_end.detachSpring(this);
      b_end = sa;                     
      sa.attachSpring(this);
   }
   public void detachAend(){
      if (a_end == null) return;
      a_end.detachSpring(this);
      aLoosePosition = a_end.getPosition();
      a_end=null;
   }      
   public void detachBend(){
      if (b_end == null) return;
      b_end.detachSpring(this);
      bLoosePosition = b_end.getPosition();
      b_end=null;
   }      

   public double getAendPosition() {
      if (a_end != null)
         return a_end.getPosition();
      else 
         return aLoosePosition;
   }
   public double getBendPosition() {
      if (b_end != null)
         return b_end.getPosition();
      else
         return bLoosePosition;
   }
   public double getRestLength() {
      return restLength;
   }
   public double getForce(SpringAttachable ball) {
      double force = 0;
      if ((a_end == null) || (b_end == null))
         return force;
      if ((ball != a_end) && (ball != b_end))
         return force;
      double a_pos = getAendPosition();
      double b_pos = getBendPosition();
      double stretch = Math.abs(b_pos-a_pos)-restLength;
      force = stretch*stiffness;
      if ((ball==a_end)^(a_pos<b_pos)) return -force;
      return force;
   }
   public void updateView (Graphics2D g){
     view.updateView(g);      
   }
   public void setSelected(){
      view.setSelected();
   }
   public void setReleased(){
      view.setReleased();
   }
   public boolean contains(double x, double y){
      return view.contains(x,y);
   }
   public void dragTo(double x){
     if (Math.abs(x-getAendPosition()) < Math.abs(x-getBendPosition()) ){  // A end is closer
       if (a_end!=null)
         detachAend();
       aLoosePosition=x;
     }else {
       if (b_end!=null)
         detachBend();
       bLoosePosition=x;
     }
   }
   public String getDescription() {
      return "Spring_"+ getId()+":a_end\tb_end";
   }
   public String getState() {
      String s = getAendPosition() + "\t" + getBendPosition();
      return s;
   }
}