import javax.swing.*;
import java.lang.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.*;

public class MainFrame extends JFrame{


    private enum Status {
        VALID,
        INVALIDNUM,
        INVALIDEXP
    }

    private Long cardNumba;
    private LocalDate expirationDate;
    private int CVVNumba;
    private Status cardStatus;

    private JLabel CardNumber;
    private JTextField tfCardNumber;
    private JLabel Expiration;
    private JTextField TFCVV;
    private JButton CHECKCARDButton;
    private JPanel MainFrame;
    private JLabel CVV;
    private JTextField ExpirationTB;

    MainFrame(){

        setTitle("Card Checker");
        setSize(500, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setContentPane(MainFrame);
        setVisible(true);

        CHECKCARDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                cardNumba = Long.parseLong(tfCardNumber.getText());
                CVVNumba = Integer.parseInt(TFCVV.getText());
                expirationDate = createDate(ExpirationTB.getText());
                validateCard();
                printStatus();

            }
        });

    }

    public boolean checkExpirationDate() {
        LocalDate todaysDate = LocalDate.now();
        return todaysDate.isBefore(expirationDate);
    }

    public boolean checkCardNumber() {

        Stack<Long> s = new Stack<>();
        Long temp;
        temp = Long.parseLong(tfCardNumber.getText());

        while (temp > 0) {
            s.push(temp % 10);
            temp /= 10;
        }

        int sum = 0;
        int counter = 1;
        while (!s.empty()) {
            Long num;
            num = s.peek();
            if (counter % 2 == 0) {
                num *= 2;
                if (num > 9) {
                    sum += num % 10;
                    num /= 10;
                    sum += num;
                }
            }
            else {
                sum += num;
            }
            s.pop();
            counter++;
        }

        return sum % 10 == 0;

    }

    private boolean validateCard(){

        if (checkCardNumber() && checkExpirationDate()) {
            cardStatus = Status.VALID;
            return true;
        }
        else {
            if (!checkCardNumber()) {
                cardStatus = Status.INVALIDNUM;
            }
            else {
                cardStatus = Status.INVALIDEXP;
            }
            return false;
        }


    }

    private void printStatus() {

        if (cardStatus == null) {
            isValid();
        }

        switch (cardStatus) {

            case VALID: {
                JOptionPane.showMessageDialog(null, "VALID");
                break;
            }
            case INVALIDNUM: {
                JOptionPane.showMessageDialog(null, "INVALIDNUM");
                break;
            }
            case INVALIDEXP: {
                JOptionPane.showMessageDialog(null, "INVALIDEXP");
                break;
            }
            default: {
                JOptionPane.showMessageDialog(null, "UNKNOWN");
            }
        }
    }

    private String convertDate(String oldDate) {

        String newDate = "20";

        for (int i = 3; i < 5; i++){ // Copy year

            newDate += oldDate.charAt(i);

        }

        newDate += "-";

        for (int i = 0; i < 2; i++){ // Copy month

            newDate += oldDate.charAt(i);

        }


        newDate += "-" + "01"; // Add day

        return newDate;

    }

    private  LocalDate createDate(String temp){

        LocalDate date = LocalDate.parse(convertDate(temp));
        return date;

    }

    public static void main(String[] args){

        MainFrame MF = new MainFrame();

    }

}
