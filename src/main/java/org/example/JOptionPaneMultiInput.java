package org.example;
import javax.swing.*;
import java.util.ArrayList;

public class JOptionPaneMultiInput {
    JTextField xField ;//= new JTextField(5);
    JTextField yField ;//= new JTextField(5);

    ArrayList<String> rooms;
    JOptionPaneMultiInput(ArrayList<String> rooms){
        this.rooms = rooms;
        this.rooms.forEach(room -> System.out.println(room));
        JTextField xField = new JTextField(5);
        JTextField yField = new JTextField(5);

        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("username:"));
        myPanel.add(xField);
        myPanel.add(Box.createHorizontalStrut(15)); // a spacer
        myPanel.add(new JLabel("chatroom:"));
        myPanel.add(yField);
        myPanel.add(Box.createVerticalStrut(5)); // a spacer
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(("Rooms are:"));
        String str = "Rooms are:";
        rooms.forEach(room -> {
           stringBuilder.append(room);

        });

        myPanel.add(new JLabel(stringBuilder.toString()));


        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Please Enter username and chatroom", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            System.out.println("x value: " + xField.getText());
            System.out.println("y value: " + yField.getText());
        }
    }

}
