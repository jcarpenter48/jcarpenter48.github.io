import java.util.Scanner;
import java.lang.*;
import java.math;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import net.objecthunter.exp4j.*;

@SuppressWarnings("unused")
public class MassOfAtmosphereCalculator extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8984592406346035387L;
	public static double ERROR = -999.0;
	private static Scanner sc;

    private static JTextField textField; //pressure (p)
    private static JTextField textField2; //radius (r)
    private static JTextField textField3; //acceleration due to gravity (g)	
	
	private static JTextField calculationResult;

	public MassOfAtmosphereCalculator()
	{
		super(new BorderLayout());
		// layout of window
        JPanel labelPanel = new JPanel(new GridLayout(4, 2)); // 4 rows 2 columns
        add(labelPanel, BorderLayout.WEST);
        JPanel fieldPanel = new JPanel(new GridLayout(4, 2)); // 4 rows 2 columns
        add(fieldPanel, BorderLayout.CENTER);

        JLabel labelPressure = new JLabel("Pressure (Pascals):");
        textField = new JTextField();		
		JLabel labelRadius = new JLabel("Radius (Meters):");
        textField2 = new JTextField();
        JLabel labelAccel = new JLabel("Acceleration due to Gravity (m/s^2):");
        textField3 = new JTextField();
		
		JLabel finalResultLabel = new JLabel("Result:");		
		calculationResult = new JTextField();
		
        labelPanel.add(labelPressure);
        labelPanel.add(labelRadius);
        labelPanel.add(labelAccel);
        labelPanel.add(finalResultLabel);		

        fieldPanel.add(textField);
        fieldPanel.add(textField2);		
        fieldPanel.add(textField3);	
			fieldPanel.add(calculationResult);
    } //end of Public MassOfAtmosphereCalculator
	
	public static void main(String[] args)
	{
		final MassOfAtmosphereCalculator form = new MassOfAtmosphereCalculator();
		
		//calculate result button
		JButton submit = new JButton("Calculate Mass of Atmosphere");
        submit.addActionListener(new ActionListener() 
		{
            @Override
            public void actionPerformed(ActionEvent e) 
			{
                calculateResult(textField.getText(), textField2.getText(), textField3.getText());
            } //end of actionPerformed
        }); //end of Calculate button
		
        // program frame
		JFrame guiFrame = new JFrame("Mass of Atmosphere Calculator");
        guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		guiFrame.setSize(800,600);
					guiFrame.setLocationRelativeTo(null);
        guiFrame.getContentPane().add(form, BorderLayout.NORTH);
        JPanel p = new JPanel();
        p.add(submit);
        guiFrame.getContentPane().add(p, BorderLayout.SOUTH);
        //guiFrame.pack();
        guiFrame.setVisible(true);	
        guiFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
	} //just assembles the GUI and holds the trigger for calculating the result
	
	private static void calculateResult(String pressureStr, String radiusStr, String accelStr)
	{
			double p;
			double r;
			double g;
			
			radiusStr = radiusStr.replaceAll("[()]", "");
			
			p = Convert.Parse(pressureStr, ERROR);
			r = Convert.Parse(radiusStr, ERROR);
			g = Convert.Parse(accelStr, ERROR);
			
			System.out.println(""+radiusStr);
			
			double atmosphericMass;
			double temp = 0;
			temp = 4*Math.PI*(Math.pow(r, 2));
			
			atmosphericMass = (temp*p)/g;

			System.out.println("\nPressure: "+p);
			System.out.println("Radius: "+r);
			System.out.println("Acceleration due to Gravity: "+g);
			System.out.println("\nAtmospheric Mass <= "+atmosphericMass);		
			
			if(r==-999.0) 
			{
				calculationResult.setText("Radius Set as ERROR. Did you enter radius in meters? "+atmosphericMass);
			}
			else
			{
				calculationResult.setText(atmosphericMass+"");				
			}
	}
	

	public static class Convert 
	{
		/*
		Class  Convert
			Purpose:
			 Utility class to provide methods to convert Data from String to int and Double.
			*/

		public static boolean IsNotInt(String TestString) 
		{

		//  Tests a String to see if it is an integer requtrns False if it is not an integer.

			int start = 0;
			
			try{
				start = Integer.parseInt(TestString);
				return false;
			}
				catch(NumberFormatException e){
				return true;
			}
		 }  // end IsNotInt
		 public static boolean IsNotDouble(String TestString) 
		 {
		//  Tests a String to see if it is an Double requtrns False if it is not an Double.
			double start = 0;
			
			try{
				start = Double.parseDouble(TestString);
				return false;
			}
				catch(NumberFormatException e){
				return true;
			}
		 }  // end IsNotInt
		 

		 public static int Parse(String InString, int ErrorValue) 
		 {
		//  Tries to Parse Value returns ErrorValue if there is an Error.
		
			int myValue = 0;
			// Check for negative integers.  If negative, we will just skip the first character when we test the rest of the character string for digits.
			
			try{
				myValue = Integer.parseInt(InString);
				return myValue;
			}
			catch(NumberFormatException e){
			return ErrorValue;
			}
		 }  // end Parse
		 public static double Parse(String InString, double ErrorValue) {

		//  Tries to Parse Value returns ErrorValue if there is an Error.

			double myValue = 0;
			// Check for negative integers.  If negative, we will just skip the first character when we test the rest of the character string for digits.
			
			try{
				myValue = Double.parseDouble(InString);
				return myValue;
		}
			catch(NumberFormatException e){
			return ErrorValue;
			}
		 }  // end Parse
		 public boolean Parse(char value, boolean ERROR){
			value = Character.toUpperCase(value);
			boolean result;
			switch(value){
				case ('T'):
				case ('Y'):
				case ('1'):
					result = true;
					break;
				case ('F'):
				case ('N'):
				case ('0'):
					result = false;
					break;
				default:
					result = ERROR;
					break;
			}
			return result;
		}// end Parse;

	} // end Convert

}