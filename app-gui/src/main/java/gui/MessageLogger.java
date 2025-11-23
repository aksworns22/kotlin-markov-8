package gui;

import markov.output.Message;
import markov.output.MessageOutput;

import javax.swing.*;

public class MessageLogger extends JTextArea implements MessageOutput {
    MessageLogger() {
        setName("messageLog");
        setEditable(false);
    }

    @Override
    public void println(Message message) {
        append(message.getFull() + "\n");
    }
}
