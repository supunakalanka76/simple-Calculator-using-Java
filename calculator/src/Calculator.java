import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class Calculator extends JFrame implements ActionListener {
    private JTextField display;
    private JButton[] numberButtons = new JButton[10];
    private JButton addButton, subButton, mulButton, divButton, eqButton, clrButton, dotButton, signButton, percentButton;
    private JPanel panel;

    private double num1 = 0, num2 = 0, result = 0;
    private char operator;

    public Calculator() {
        // Set up the JFrame
        setTitle("Calculator");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

        // Display field
        display = new JTextField();
        display.setFont(new Font("Arial", Font.BOLD, 36));
        display.setEditable(false);
        display.setHorizontalAlignment(SwingConstants.RIGHT);
        display.setBackground(new Color(30, 30, 30));
        display.setForeground(Color.WHITE);
        display.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        add(display, BorderLayout.NORTH);

        // Create button styles
        Font buttonFont = new Font("Arial", Font.BOLD, 24);
        Color darkGray = new Color(50, 50, 50);
        Color lightGray = new Color(150, 150, 150);
        Color orange = new Color(255, 149, 0);

        // Number buttons
        for (int i = 0; i < 10; i++) {
            numberButtons[i] = createButton(String.valueOf(i), buttonFont, Color.WHITE, darkGray);
        }

        // Operator buttons
        addButton = createButton("+", buttonFont, Color.WHITE, orange);
        subButton = createButton("-", buttonFont, Color.WHITE, orange);
        mulButton = createButton("×", buttonFont, Color.WHITE, orange);
        divButton = createButton("÷", buttonFont, Color.WHITE, orange);
        eqButton = createButton("=", buttonFont, Color.WHITE, orange);
        clrButton = createButton("C", buttonFont, Color.BLACK, lightGray);
        dotButton = createButton(".", buttonFont, Color.WHITE, darkGray);
        signButton = createButton("±", buttonFont, Color.WHITE, darkGray);
        percentButton = createButton("%", buttonFont, Color.WHITE, darkGray);

        // Setting up the panel with grid layout
        panel = new JPanel();
        panel.setLayout(new GridLayout(5, 4, 10, 10));
        panel.setBackground(new Color(30, 30, 30));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Add buttons to panel
        panel.add(clrButton);
        panel.add(signButton);
        panel.add(percentButton);
        panel.add(divButton);

        panel.add(numberButtons[7]);
        panel.add(numberButtons[8]);
        panel.add(numberButtons[9]);
        panel.add(mulButton);

        panel.add(numberButtons[4]);
        panel.add(numberButtons[5]);
        panel.add(numberButtons[6]);
        panel.add(subButton);

        panel.add(numberButtons[1]);
        panel.add(numberButtons[2]);
        panel.add(numberButtons[3]);
        panel.add(addButton);

        panel.add(numberButtons[0]);
        panel.add(dotButton);
        panel.add(eqButton);

        add(panel, BorderLayout.CENTER);

        // Add keyboard support
        display.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e);
            }
        });

        // Set focus to the display so that it can capture key events
        display.setFocusable(true);
        display.requestFocusInWindow();
    }

    private JButton createButton(String text, Font font, Color fg, Color bg) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setForeground(fg);
        button.setBackground(bg);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setOpaque(true);
        button.addActionListener(this);
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < 10; i++) {
            if (e.getSource() == numberButtons[i]) {
                display.setText(display.getText().concat(String.valueOf(i)));
            }
        }

        if (e.getSource() == clrButton) {
            display.setText("");
            num1 = num2 = result = 0;
            operator = ' ';
        } else if (e.getSource() == addButton) {
            num1 = Double.parseDouble(display.getText());
            operator = '+';
            display.setText("");
        } else if (e.getSource() == subButton) {
            num1 = Double.parseDouble(display.getText());
            operator = '-';
            display.setText("");
        } else if (e.getSource() == mulButton) {
            num1 = Double.parseDouble(display.getText());
            operator = '*';
            display.setText("");
        } else if (e.getSource() == divButton) {
            num1 = Double.parseDouble(display.getText());
            operator = '/';
            display.setText("");
        } else if (e.getSource() == eqButton) {
            num2 = Double.parseDouble(display.getText());
            calculateResult();
        } else if (e.getSource() == dotButton) {
            if (!display.getText().contains(".")) {
                display.setText(display.getText().concat("."));
            }
        } else if (e.getSource() == signButton) {
            double current = Double.parseDouble(display.getText());
            display.setText(String.valueOf(current * -1));
        } else if (e.getSource() == percentButton) {
            double current = Double.parseDouble(display.getText());
            display.setText(String.valueOf(current / 100));
        }
    }

    private void handleKeyPress(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_0, KeyEvent.VK_NUMPAD0 -> display.setText(display.getText().concat("0"));
            case KeyEvent.VK_1, KeyEvent.VK_NUMPAD1 -> display.setText(display.getText().concat("1"));
            case KeyEvent.VK_2, KeyEvent.VK_NUMPAD2 -> display.setText(display.getText().concat("2"));
            case KeyEvent.VK_3, KeyEvent.VK_NUMPAD3 -> display.setText(display.getText().concat("3"));
            case KeyEvent.VK_4, KeyEvent.VK_NUMPAD4 -> display.setText(display.getText().concat("4"));
            case KeyEvent.VK_5, KeyEvent.VK_NUMPAD5 -> display.setText(display.getText().concat("5"));
            case KeyEvent.VK_6, KeyEvent.VK_NUMPAD6 -> display.setText(display.getText().concat("6"));
            case KeyEvent.VK_7, KeyEvent.VK_NUMPAD7 -> display.setText(display.getText().concat("7"));
            case KeyEvent.VK_8, KeyEvent.VK_NUMPAD8 -> display.setText(display.getText().concat("8"));
            case KeyEvent.VK_9, KeyEvent.VK_NUMPAD9 -> display.setText(display.getText().concat("9"));
            case KeyEvent.VK_ADD -> {
                num1 = Double.parseDouble(display.getText());
                operator = '+';
                display.setText("");
            }
            case KeyEvent.VK_SUBTRACT -> {
                num1 = Double.parseDouble(display.getText());
                operator = '-';
                display.setText("");
            }
            case KeyEvent.VK_MULTIPLY -> {
                num1 = Double.parseDouble(display.getText());
                operator = '*';
                display.setText("");
            }
            case KeyEvent.VK_DIVIDE -> {
                num1 = Double.parseDouble(display.getText());
                operator = '/';
                display.setText("");
            }
            case KeyEvent.VK_ENTER -> {
                num2 = Double.parseDouble(display.getText());
                calculateResult();
            }
            case KeyEvent.VK_DECIMAL -> {
                if (!display.getText().contains(".")) {
                    display.setText(display.getText().concat("."));
                }
            }
            case KeyEvent.VK_C -> {
                display.setText("");
                num1 = num2 = result = 0;
                operator = ' ';
            }
        }
    }

    private void calculateResult() {
        switch (operator) {
            case '+' -> result = num1 + num2;
            case '-' -> result = num1 - num2;
            case '*' -> result = num1 * num2;
            case '/' -> result = num2 != 0 ? num1 / num2 : Double.POSITIVE_INFINITY;
        }
        display.setText(String.valueOf(result));
        num1 = result;
    }

    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        calculator.setVisible(true);
    }
}
