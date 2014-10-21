package net.javajeff.jtwain;


public class Main {

	public static void main(String[] args) throws JTwainException {
		
		System.loadLibrary ("orcajtwain");


		new JTwainDemo("Uyeah");
		//JTwainDemo d = new JTwainDemo("Uyeah");
		
		
	}

}
