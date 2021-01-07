package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Controller {
	
    @FXML private Label resultLabel;    
    private String Operator;
    private String Number_One;
    private String Number_Two;
    private boolean OpInput;
    
    public Controller() {
        this.Operator = "";
        this.Number_One = "";
        this.Number_Two = "";
        this.OpInput = true;
    }

	public String getOperator() {
		return Operator;
	}

	public void setOperator(String operator) {
		Operator = operator;
	}

	public String getNumber_One() {
		return Number_One;
	}

	public void setNumber_One(String number_One) {
		Number_One = number_One;
	}

	public String getNumber_Two() {
		return Number_Two;
	}

	public void setNumber_Two(String number_Two) {
		Number_Two = number_Two;
	}

	public boolean isOpInput() {
		return OpInput;
	}

	public void setOpInput(boolean opInput) {
		OpInput = opInput;
	}

	// Check Only One Point
	boolean onlySinglePoint(String ch) 
	{
		boolean test = true;
		int lg = ch.length();
		int i = 0;
		if(ch.charAt(0) == '-') {
			i = 1;
		}
		while(i < lg && test) {
			if(ch.charAt(i) == '.') {
				test = false;
			}
			i++;
		}
		return test;	
	}
	
    // Round Total
    double roundAvoid(double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }
    
    // Opposite Number
    String opositeNumber(String ch) {
    	StringBuilder sb = new StringBuilder(ch);
    	if(sb.toString() != "") {
    		if(sb.charAt(0) != '-') {
        		sb.insert(0,'-');    		
        	}else if(sb.charAt(0) == '-') {
        		sb.deleteCharAt(sb.indexOf("-"));   		
        	}	
    	}
    	return sb.toString();   	
    }
	
	// Calculate Data
    double calculate(String operator, double x, double y) {
        switch (operator) {
            case "+":
            	return roundAvoid(x + y, 3);  
            case "-":
            	return roundAvoid(x - y, 3);
            case "*":
            	return roundAvoid(x * y, 3);
            case "/":
            	if(y == 0) {
            		return 0;
            	}
            	return roundAvoid((double)x / (double)y, 3);
            case "%":
            	return (int)(x) % (int)(y);
            default:
            	return 0;
        }
    }
    
    @FXML
    public void onClickedNumber(ActionEvent event) {
    	Button btn = (Button) event.getSource();	
    	final String btnText = btn.getText();
    	
    	// Get Numeric Data
        if (btnText.matches("[0-9\\.]")) {

        	if(getNumber_One().length() == 0) {
         		 if(getNumber_Two().length() == 0) {
         			 if(btnText.equals(".")) {
         				setNumber_Two("0.");
         			 }else {
         				setNumber_Two(getNumber_Two()+btnText);
         			 }
                 }else if(getNumber_Two().length() <= 8) {
                	 if(btnText.equals(".")) {
                		 if(onlySinglePoint(getNumber_Two())) {
                			 setNumber_Two(getNumber_Two()+btnText);
                		  }
                     }else {
                    	 setNumber_Two(getNumber_Two()+btnText);			
                     }
                 }  		
        	}else {
        		 if(getNumber_Two().length() == 0) {
         			 if(btnText.equals(".")) {
         				setNumber_Two("0.");
         			 }else {
         				setNumber_Two(getNumber_Two()+btnText);
         			 }
                 }else if(getNumber_Two().length() <= 8) {
                	 if(btnText.equals(".")) {
                		 if(onlySinglePoint(getNumber_Two())) {
                			 setNumber_Two(getNumber_Two()+btnText);
                		  }
                     }else {
                    	 setNumber_Two(getNumber_Two()+btnText);			
                     }
                 }
        	}	
        	resultLabel.setText(getNumber_Two());	
            return;
        }  
    }
    
	@FXML
    public void onClickedOperator(ActionEvent event) {
    	Button btn = (Button) event.getSource();	
    	final String btnText = btn.getText();    	
 	
    	// Pressing AC Button
        if (btnText.equals("AC")){
        	setOpInput(true);
        	setOperator(""); 	
            setNumber_One(""); 
            setNumber_Two("");
            resultLabel.setText("0");
            return;
        }
        
        // Pressing +/-
        if(btnText.equals("+/-")) {
        	if(getNumber_One().length()  == 0 && getNumber_Two().length() != 0 ) {
        		String chTwo = new String(opositeNumber(getNumber_Two()));	
        		setNumber_Two(chTwo);
        		resultLabel.setText(chTwo);
        	}
        	
        	if(getNumber_One().length()  != 0 && getNumber_Two().length() != 0 ) {
        		String chTwo = new String(opositeNumber(getNumber_Two()));	
        		setNumber_Two(chTwo);
        		resultLabel.setText(chTwo);
        	}
       		return;
        }
        
        // Pressing Operations
        if(btnText.matches("[-+*/\\%]")) {
        	if(isOpInput()) {
        		setOperator(btnText);
        		setNumber_One(getNumber_Two()+ getNumber_One());
        		setNumber_Two("");
        		resultLabel.setText("0");
        		setOpInput(false);
        	}
            return; 
        }
   
        // Pressing =
        if(btnText.equals("=")) {
        	if(getNumber_One().length() == 0) {
        		setNumber_One("");
        		setNumber_Two("");
        		resultLabel.setText("0");
        	}else if(getNumber_Two().length() == 0){
        		setNumber_Two("");
        		setNumber_One("");
        		resultLabel.setText("0");
        	}else {
                double num = calculate(getOperator(),Double.parseDouble(getNumber_One()),Double.parseDouble(resultLabel.getText()));
                resultLabel.setText(String.valueOf(num)); 
                setNumber_One(String.valueOf(num));
                setNumber_Two("");
                setOperator("");
                setOpInput(true);   		
        	}
            return;
        }
    }	
}
