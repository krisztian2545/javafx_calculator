package calculator;

import calculator.model.Calculator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class CalculatorController {

    @FXML
    private TextField display;

    private Calculator calculator;
    private boolean startNumber = true;
    private double number1;
    private String operator = "";

    @FXML
    private void initialize() {
        calculator = new Calculator();
    }

    @FXML
    public void processDigit(ActionEvent event) {
        String digitPressed = ((Button) event.getSource()).getText();
        System.out.println(digitPressed);
        if ( startNumber || display.getText().equals("0") ) {
            display.setText(digitPressed);
        } else {
            display.setText(display.getText() + digitPressed);
        }
        startNumber = false;
    }

    @FXML
    public void processDot(ActionEvent event) {

        if( (startNumber && !operator.isEmpty()) || !display.getText().contains(".") ) {
            if ( startNumber || display.getText().equals("0") ) {
                display.setText("0.");
            } else {
                display.setText(display.getText() + ".");
            }
            startNumber = false;
        }

    }

    @FXML
    public void allClear(ActionEvent event) {
        operator = "";
        startNumber = true;
        display.setText("0");
    }

    @FXML
    public void processOperator(ActionEvent event) {
        String operatorPressed = ((Button) event.getSource()).getText();
        System.out.println(operatorPressed);

        switch (operatorPressed) {
            case "=":
                if (operator.isEmpty()) {
                    return;
                }
                double number2 = Double.parseDouble(display.getText());
                double result = calculator.calculate(number1, number2, operator);
                System.out.println("result: " + result);
                display.setText(formatDouble(result));
                operator = "";
                break;

            case "±":
                double temp = -1 * Double.parseDouble(display.getText());
                display.setText(formatDouble(temp));
                break;

            default:
                if (! operator.isEmpty()) {
                    return;
                }
                number1 = Double.parseDouble(display.getText());
                operator = operatorPressed;
                startNumber = true;
                break;
        }
    }

    private String formatDouble(double d) {
        if(d == 0) // to not print -0
            d = Math.abs(d);
        return d == (long) d ? String.format("%.0f", d) : String.format("%.1f", d);
    }

}
