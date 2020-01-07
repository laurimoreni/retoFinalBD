package grupo3.retoFinalBD;

import grupo3.retoFinalBD.vista.VentanaPpal;

public class Main {
	
	public static void main(String[] args) {
		
		try {
			VentanaPpal vista = new VentanaPpal();
			Principal ppal = new Principal(vista);
			vista.setVisible(true);
			ppal.procesoPpal();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
