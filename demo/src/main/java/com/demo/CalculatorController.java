package com.demo;

import static java.lang.Math.*;

import java.util.Arrays;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class CalculatorController {

    String [] basicOperators ={"+", "-", ":", "X", "^"};
    String [] scientificOperators = {"ln", "log","tan", "sin", "cos", "sqrt", "abs", "X!","%", "+/-"} ;

    @FXML
    private  TextField display ;
    @FXML 
    private VBox buttonContainer;

    private String operator= "";
    private double firstOperand= 0;
    private double operand = 0;
    private boolean start = true ;
    private String lastOperator = "";
    private double lastOperand = 0;
    private boolean isOn = true;
    
     
    public void setFirstOperand(double operand){firstOperand = operand;}
    public double getFirstOperand(){return firstOperand;}
           
    @FXML
    public void handleButtonAction(@SuppressWarnings("exports") ActionEvent event){
       
        if(!isOn)return;
       
        Button buttonClicked = (Button) event.getSource();
        String buttonText = buttonClicked.getText();

        if (start) {
            display.setText("");
            start = false;
        }

        if(Arrays.asList(basicOperators).contains(buttonText)){

            if(!operator.equals(""))handleEquals(event);

            if(display.getText().equals("")){
                display.setText("Error");
                start = true;
            }else{
                operator = buttonText;
                if(display.getText().equals("e"))firstOperand = Math.E;
                else firstOperand = Double.parseDouble(display.getText()) ;
                display.setText("") ;
            }

        }else if( Arrays.asList(scientificOperators).contains(buttonText)){
            
            if(!display.getText().equals("") && !display.getText().equals("Error")){
                if(display.getText().equals("e"))operand = Math.E;
                else operand = Double.parseDouble(display.getText());
                scientificCalcutation(buttonText);
            }else{
                display.setText("Error");
                start = true;
            }
            
        }else{

            display.setText(display.getText() + buttonText);  

        }
    }

    @FXML
    public void handleClear(@SuppressWarnings("exports") ActionEvent event){
        Button buttonClicked = (Button) event.getSource();
        String buttonText = buttonClicked.getText();
        
        if(buttonText.equals("DEL") ){
            if(display.getText().length()>0){
            String newText = display.getText().substring(0,display.getText().length() -1);
            display.setText(newText);
           }
           
        }else{
            operator = "";
            firstOperand = 0;
            start = true;
            lastOperand = 0;
            lastOperator = "";
            display.setText("");
        }
    }

    @FXML
    public void handleEquals(@SuppressWarnings("exports") ActionEvent event){     
       
        if ( !operator.isEmpty()) { 
            lastOperand = Double.parseDouble(display.getText()); 
            basicCalculations(getFirstOperand(),operator);
            lastOperator = operator ; 
            operator = "";
        }else if(!lastOperator.isEmpty()){
           basicCalculations(lastOperand, lastOperator);
        }else{
            display.setText("Error");
        }
        
    }

    @FXML
    public void handlePoint(@SuppressWarnings("exports") ActionEvent event){
        if(display.getText().equals(""))display.setText("0.");
        else display.setText(display.getText() + ".");
    }

  
    public void basicCalculations(double operands, String operator){
        if (!display.getText().equals("Error")){
            double secondOperand;
            if(display.getText().equals("e")) secondOperand = Math.E;
            else  secondOperand = Double.parseDouble(display.getText());
            double value = 0 ;
                try{
                    switch (operator) {
                        case "+":
                        value = operands + secondOperand;break;
                        case "-":
                        value = operands - secondOperand;break;
                        case ":":
                        if(secondOperand!=0){value = operands / secondOperand;break;}
                        else{display.setText("Error");break;}
                        case "X":
                        value = operands * secondOperand;break;  
                        case "^":
                        value = pow(operands, secondOperand);break;
                        default:
                        display.setText("Error");break;
                    }
                    
                    if(value % 1 == 0)
                    display.setText(String.valueOf((int)value));
                    else display.setText(String.valueOf(value)); 
                    
                }catch(NumberFormatException e){
                    display.setText("Error");
                }
               
        }else{
            display.setText("");
        }     
        
    }
    
    
    public void scientificCalcutation(String operator2){
        if(!display.getText().equals("Error")){
            double value = 0;
            try {
            
                switch (operator2) {
                    case "sin":
                        value = Math.sin(Math.toRadians(operand));break;
                    case "cos":
                        value = Math.cos(Math.toRadians(operand));break;
                    case "tan":
                        value= Math.tan(Math.toRadians(operand));break;
                    case "log":
                        value = Math.log10(operand);break;
                    case "ln":
                        value = Math.log(operand); break;
                    case "sqrt":
                        value = Math.sqrt(operand);break;
                    case "X!":
                        value = calculateFactorialRecursive(operand);break;
                    case "%":
                        value = operand / 100;break;
                    case "abs":
                        value = Math.abs(operand);break;
                    case "+/-":
                        value = (operand>0) ? -operand: Math.abs(operand);break;
                    default:
                        display.setText("Error");break;
                }
                if(value % 1 == 0)
                display.setText(String.valueOf((int)value));
                else
                display.setText(String.valueOf(value));


            } catch (Exception e) {
            display.setText("Error");
            }

        }else{
            display.setText("");
        }  
        
    } 

    public void initialize() {
        display.addEventFilter(javafx.scene.input.KeyEvent.ANY, event -> {
        event.consume(); 
        });
    }

    public  double  calculateFactorialRecursive(double number){
        if (number == 0) {
            return 1;
        } else {
            return number * calculateFactorialRecursive(number - 1);
        }
    }

    @FXML
    public void handleFunction(@SuppressWarnings("exports") ActionEvent event){
        Button buttonClicked = (Button) event.getSource();
        String buttonText = buttonClicked.getText();

        if(buttonText.equals("OFF")){
            isOn = false;
            setButtonsEnabled(false);
            setTextFieldEnabled(false); 
            display.setText("");
            start = true;
        }else{
            isOn = true;
            setButtonsEnabled(true);
            setTextFieldEnabled(true);
            display.setText(""); 
            start = true; 
        }
        
    }

    private void setTextFieldEnabled(boolean editable) {
        display.setEditable(editable);  
        display.setDisable(!editable); 
       
    }
    private void setButtonsEnabled(Boolean enabled){
        buttonContainer.getChildren().forEach(node -> {
            if (node instanceof Button) { 
                node.setDisable(!enabled);
            }
        });
    }
     
}