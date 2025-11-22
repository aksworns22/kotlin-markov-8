package markov;

import markov.output.Message;
import markov.output.MessageOutput;

import javax.swing.*;

public class MessageLogger extends JTextArea implements MessageOutput {
    MessageLogger(int rows, int cols) {
        super(rows, cols);
        setName("messageLog");
        setEditable(false);
    }

    @Override
    public void println(Message message) {
        append(message.getFull() + "\n");
    }
}
