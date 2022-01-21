/**
* This program creates an exception class that prevents invalid Pokemon objects.
* @author Jun Kim
* @since 11/22/21
*/

public class PokemonException extends RuntimeException {
   /**
   * Constructor with message string.
   * @param m the error message
   */
   public PokemonException(String m) {
      super(m);
   }
   
   /**
   * Constructor with default message.
   */
   public PokemonException() {
      super("Error: There is something wrong.");
   }
}
