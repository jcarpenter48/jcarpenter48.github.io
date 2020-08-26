import java.util.Scanner;
import java.lang.*;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import net.objecthunter.exp4j.*;

@SuppressWarnings("unused")
public class IntegralApproximationCalculator extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7984592406346035387L;
	public static double ERROR = -999.0;
	private static Scanner sc;

    private static JComboBox approximationtype; //which rule to use (Trapezoidal, Midpoint, Simpson)

    private static JTextField textField; //lower bounds (a)
    private static JTextField textField2; //upper bounds (b)
    private static JTextField textField3; //number of divisions (n)
    private static JTextField textField4; //second or fourth derivative	
	
	private static JTextField approximationresult;

	public IntegralApproximationCalculator()
	{
		super(new BorderLayout());
		// layout of window
        JPanel labelPanel = new JPanel(new GridLayout(6, 2)); // 6 rows 2 columns
        add(labelPanel, BorderLayout.WEST);
        JPanel fieldPanel = new JPanel(new GridLayout(6, 2)); // 6 rows 2 columns
        add(fieldPanel, BorderLayout.CENTER);

        JLabel labelDeriv = new JLabel("Second Derivative:");
		
        // select between approximation rules
        JLabel labelCombo = new JLabel("Approximation Rule:");
        String[] options = { "Trapezoidal Approximation Error", "Midpoint Approximation Error", "Simpson Approximation Error" };
        approximationtype = new JComboBox(options);
        approximationtype.addActionListener(new ActionListener() 
		{
			String choice;
            @Override
            public void actionPerformed(ActionEvent e) 
			{
				choice = (String) approximationtype.getSelectedItem();
                if(choice.equals("Simpson Approximation Error"))
				{
					labelDeriv.setText("Fourth Derivative:");
				}
				else
				{
					labelDeriv.setText("Second Derivative:");
				}
            } //see above
        }); //end of approximationtype ActionListener

        JLabel labelLower = new JLabel("Lower Bounds (a):");
        textField = new JTextField();		
		JLabel labelUpper = new JLabel("Upper Bounds (b):");
        textField2 = new JTextField();
        JLabel labelDivisions = new JLabel("Number of Divisions (n):");
        textField3 = new JTextField();

        textField4 = new JTextField();		
		JLabel finalResultLabel = new JLabel("Result:");		
		approximationresult = new JTextField();
		
        labelPanel.add(labelCombo);	
        labelPanel.add(labelLower);
        labelPanel.add(labelUpper);
        labelPanel.add(labelDivisions);
        labelPanel.add(labelDeriv);	
        labelPanel.add(finalResultLabel);		

        fieldPanel.add(approximationtype);
        fieldPanel.add(textField);
        fieldPanel.add(textField2);		
        fieldPanel.add(textField3);
        fieldPanel.add(textField4);		
			fieldPanel.add(approximationresult);
    } //end of Public IntegralErrorCalculator
	
	public static void main(String[] args)
	{
		final IntegralApproximationCalculator form = new IntegralApproximationCalculator();
		
		//calculate result button
		JButton submit = new JButton("Calculate Approximation Error");
        submit.addActionListener(new ActionListener() 
		{
            @Override
            public void actionPerformed(ActionEvent e) 
			{
                calculateResult((String) approximationtype.getSelectedItem(), textField.getText(), textField2.getText(), textField3.getText(), textField4.getText());
            } //end of actionPerformed
        }); //end of Calculate button
		
        // program frame
		JFrame guiFrame = new JFrame("Integral Approximation Error Calculator");
        guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		guiFrame.setSize(600,400);
					guiFrame.setLocationRelativeTo(null);
        guiFrame.getContentPane().add(form, BorderLayout.NORTH);
        JPanel p = new JPanel();
        p.add(submit);
        guiFrame.getContentPane().add(p, BorderLayout.SOUTH);
        guiFrame.pack();
        guiFrame.setVisible(true);	
		
	} //just assembles the GUI and holds the trigger for calculating the result
	
	private static void calculateResult(String approximationSelect, String lower, String upper, String nvalue, String derivativeStr)
	{
		if(approximationSelect.equals("Midpoint Approximation Error")) //midpoint
		{
			double b;
			double a;
			double n;
			
			b = Convert.Parse(upper, ERROR);
			a = Convert.Parse(lower, ERROR);
			n = Convert.Parse(nvalue, ERROR);
			
			double K = 0.0;
			
			System.out.println("Entering second derivative");
			Expression secondderivativeExp = new ExpressionBuilder(derivativeStr)
				.variables("x")
				.build();
			K = calculateKVal(secondderivativeExp, a, b);
			
			double ErrorMidpoint;
			double badifference = Math.pow((b-a),3);
			double nsquared = Math.pow(n,2);
			ErrorMidpoint = (K*(badifference))/(24*(nsquared));

			System.out.println("\nN Value: "+nvalue);
			System.out.println("Upper Bound: "+b);
			System.out.println("Lower Bound: "+a);
			System.out.println("\nError Midpoint <= "+ErrorMidpoint);		
			
			approximationresult.setText(ErrorMidpoint+"");				
		}
		else if(approximationSelect.equals("Simpson Approximation Error")) //simpsons
		{
			double b;
			double a;
			double n;
			
			b = Convert.Parse(upper, ERROR);
			a = Convert.Parse(lower, ERROR);
			n = Convert.Parse(nvalue, ERROR);
			
			double K = 0.0;
			
			System.out.println("Entering fourth derivative");
			Expression fourthderivativeExp = new ExpressionBuilder(derivativeStr)
				.variables("x")
				.build();
			K = calculateKVal(fourthderivativeExp, a, b);
			
			double ErrorSimpson;
			double badifference = Math.pow((b-a),3);
			double nfourth = Math.pow(n,4);
			ErrorSimpson = (K*(badifference))/(180*(nfourth));

			System.out.println("\nN Value: "+nvalue);
			System.out.println("Upper Bound: "+b);
			System.out.println("Lower Bound: "+a);
			System.out.println("\nError Simpson <= "+ErrorSimpson);		
			
			approximationresult.setText(ErrorSimpson+"");				
		}
		else //trapezoidal
		{
			double b;
			double a;
			double n;
			
			b = Convert.Parse(upper, ERROR);
			a = Convert.Parse(lower, ERROR);
			n = Convert.Parse(nvalue, ERROR);
			
			double K = 0.0;
			
			System.out.println("Entering second derivative");
			Expression secondderivativeExp = new ExpressionBuilder(derivativeStr)
				.variables("x")
				.build();
			K = calculateKVal(secondderivativeExp, a, b);
			
			double ErrorTrapezoidal;
			double badifference = Math.pow((b-a),3);
			double nsquared = Math.pow(n,2);
			ErrorTrapezoidal = (K*(badifference))/(12*(nsquared));

			System.out.println("\nN Value: "+nvalue);
			System.out.println("Upper Bound: "+b);
			System.out.println("Lower Bound: "+a);
			System.out.println("\nError Trapezoidal <= "+ErrorTrapezoidal);		
			
			approximationresult.setText(ErrorTrapezoidal+"");			
		}
	}
	public static double calculateKVal(Expression e, double a, double b)
	{
		double maxValue = e.setVariable("x", a).evaluate();
		for (double x = a, i = 0; x <= b; x += .1, i++)
		{
				double val = e.setVariable("x", x).evaluate();

				if (val > maxValue)
					maxValue = val;
		}
		/*Expression e = new ExpressionBuilder("3 * sin(y) - 2 / (x - 2)")
		        .variables("x", "y")
		        .build()
		        .setVariable("x", 2.3)
		        .setVariable("y", 3.14);
				double result = e.evaluate();
				System.out.println(""+result);*/
		double kSolution = maxValue;
		return kSolution;
	}
	
	public static String inputXvar(String function)
	{
		int testXint = (5);
		String alternative = function.replaceAll("x", "("+testXint+")");
		
		return alternative;
	}
	
	public static interface Function 
	{
        public double f(double x);
    }
	
	public static double findMax(Function f, double lowerBound, double upperBound, double step) 
	{
		double maxValue = f.f(lowerBound);

		for (double i=lowerBound; i <= upperBound; i+=step) {
			double currEval = f.f(i);
			if (currEval > maxValue) {
				maxValue = currEval;
			}
		}

		return maxValue;
	}
	
	public static double eval(final String str) 
	{
		return new Object() {
			int pos = -1, ch;

			void nextChar() {
				ch = (++pos < str.length()) ? str.charAt(pos) : -1;
			}

			boolean eat(int charToEat) {
				while (ch == ' ') nextChar();
				if (ch == charToEat) {
					nextChar();
					return true;
				}
				return false;
			}

			double parse() {
				nextChar();
				double x = parseExpression();
				if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
				return x;
			}

			// Grammar:
			// expression = term | expression `+` term | expression `-` term
			// term = factor | term `*` factor | term `/` factor
			// factor = `+` factor | `-` factor | `(` expression `)`
			//        | number | functionName factor | factor `^` factor

			double parseExpression() {
				double x = parseTerm();
				for (;;) {
					if      (eat('+')) x += parseTerm(); // addition
					else if (eat('-')) x -= parseTerm(); // subtraction
					else return x;
				}
			}

			double parseTerm() {
				double x = parseFactor();
				for (;;) {
					if      (eat('*')) x *= parseFactor(); // multiplication
					else if (eat('/')) x /= parseFactor(); // division
					else return x;
				}
			}

			double parseFactor() {
				if (eat('+')) return parseFactor(); // unary plus
				if (eat('-')) return -parseFactor(); // unary minus

				double x;
				int startPos = this.pos;
				if (eat('(')) { // parentheses
					x = parseExpression();
					eat(')');
				} else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
					while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
					x = Double.parseDouble(str.substring(startPos, this.pos));
				} else if (ch >= 'a' && ch <= 'z') { // functions
					while (ch >= 'a' && ch <= 'z') nextChar();
					String func = str.substring(startPos, this.pos);
					x = parseFactor();
					if (func.equals("sqrt")) x = Math.sqrt(x);
					else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
					else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
					else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
					else throw new RuntimeException("Unknown function: " + func);
				} else {
					throw new RuntimeException("Unexpected: " + (char)ch);
				}

				if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

				return x;
			}
		}.parse();
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

	} // end CSCIConvert

}