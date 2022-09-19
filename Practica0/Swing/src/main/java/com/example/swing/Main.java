package com.example.swing;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) {
        Panel panel = new Panel();
        final JFrame frame = new JFrame();
        panel.setLayout(new FlowLayout());

        final TextField textField = new TextField("¡Hola Mundo!");
        Button button = new Button("Send");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JOptionPane.showMessageDialog(frame, textField.getText());
            }
        });
        panel.add(button);
        panel.add(textField);

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Aplication");
        frame.pack();
        frame.setVisible(true);
    }
}