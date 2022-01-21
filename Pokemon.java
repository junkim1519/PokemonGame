/**
* This program creates an object class for a Pokemon.
* @author Jun Kim
* @since 11/15/21
*/

public class Pokemon {
   //Instance variables
   /** The species of the Pokemon. */
   private String species;
   /** The name of the Pokemon. */
   private String name;
   /** The attack power of the Pokemon. */
   private int atk;
   /** The hit points of the Pokemon. */
   private int hp;

   // Constructor
   /** Makes a Pokemon.
   * @param newSpec the species of the Pokemon
   * @param newName the name of the Pokemon
   * @param newAtk attack value of the Pokemon
   * @param newHp the hit points of the Pokemon
   */
   Pokemon(String newSpec, String newName, int newAtk, int newHp) {
      this.setSpecies(newSpec);
      this.setName(newName);
      this.setAtk(newAtk);
      this.setHp(newHp);
   }
   
   /** toString method.
   * used in printing
   * @return a string representation of the object
   */
   public String toString() {
      String s = "Species: " + this.species;
      s = s + "\nName: " + this.name;
      s = s + "\nAttack: " + this.atk;
      s = s + "\nHP: " + this.hp;
      return s;
   }
   
   //Get methods
   /** gets the species of the Pokemon.
   * @return the species
   */
   public String getSpecies() {
      return this.species;
   }
   
   /** gets the name of the Pokemon.
   * @return the name
   */
   public String getName() {
      return this.name;
      
   }
   /** gets the attack value of the Pokemon.
   * @return the attack value
   */
   public int getAtk() {
      return this.atk;
   }
     
   /** gets the HP of the Pokemon.
   * @return the HP
   */
   public int getHp() {
      return this.hp;
   }
   
   
   //Set Methods
   /** sets the species of the Pokemon.
   * @param newSpec the new species
   * @throws PokemonException if the new species is blank or all spaces
   */
   public void setSpecies(String newSpec) throws PokemonException {
      if (newSpec.trim().length() > 0) {
         this.species = newSpec;
      } else {
         throw new PokemonException("Species name cannot be blank or only spaces");
      }
   }
   
   /** sets the name of the Pokemon.
   * @param newName the new name
   */
   public void setName(String newName) {
      if (newName.trim().length() > 0) {
         this.name = newName;
      } else {
         this.name = this.species;
      }
   }
   
   /** sets the attack value of the Pokemon.
   * @param newAtk the new number
   * @throws PokemonException if the number is less than 1 or greater than 200
   */
   public void setAtk(int newAtk) throws PokemonException {
      if (newAtk >= 1 && newAtk <= 200) {
         this.atk = newAtk;
      } else {
         throw new PokemonException("Attack value must be between 1 and 200");
      }
   }
   
   /** sets the HP of the Pokemon.
   * @param newHp the new HP value
   * @throws PokemonException if new HP is less than 0 or greater than 500
   */
   public void setHp(int newHp) throws PokemonException {
      if (newHp >= 0 && newHp <= 500) {
         this.hp = newHp;
      } else {
         throw new PokemonException("HP must be between 0 and 500");
      }
   }
}
