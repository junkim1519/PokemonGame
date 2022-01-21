import javax.swing.JFrame;
/**
* This program implements the PokemonPanel class to start the Pokemon game.
* @author Jun Kim
* @since 12/15/21
*/

public class PokemonGUI {

   /**
   Main method.
   @param args not used
   */   
   public static void main(String[] args) {
      
      JFrame frm = new JFrame("Pokemon Battle");
      frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frm.getContentPane().add(new PokemonPanel());
      frm.pack();
      frm.setVisible(true);
   }
}
