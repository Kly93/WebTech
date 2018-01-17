
public class Konto extends NumberFormatException {

	private double guthaben = 0; 
	
	public void abheben(double betrag) { 
		guthaben -= betrag; 
		
		
		/* Benoetigt explizites throw, da unchecked Exception
	 		--> Führt nicht zur Beendigung des Programms */
		 if (guthaben <= 0) {
			throw new NumberFormatException();
		} 
	} 
	
	public void einzahlen(double betrag) { 
		guthaben += betrag; 
		
		if (guthaben <= 0) {
			throw new NumberFormatException();
		} 
	} 
	
	public String toString() { 
		return "Kontostand: " + guthaben; 
	} 
	
	public static void main(String[] a) {     
		Konto k = new Konto(); 
		
		try {
			
		k.einzahlen(-100); 
		k.abheben(20); 
		
		}
		catch (NumberFormatException e) {
			System.out.println("Ungültige Zahl!");
			e.printStackTrace();
		}
		
		
		
		System.out.println(k); 
	} 
	
}
