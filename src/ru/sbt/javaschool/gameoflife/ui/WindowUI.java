package ru.sbt.javaschool.gameoflife.ui;

import jdk.nashorn.internal.scripts.JO;
import ru.sbt.javaschool.gameoflife.creators.GameCreator;
import ru.sbt.javaschool.gameoflife.entities.GenerationBroker;

import javax.swing.*;
import java.awt.*;

public class WindowUI implements UserInterface {

    private static final String TITLE = "Game of Life";
    private final int FIELD_SIZE = 500;
    private final int STATUS_PANEL_HEIGHT = 150;
    private final JFrame frame;
    private final JLabel lblMessage;

    private final Canvas canvasPanel;

    public WindowUI() {
        frame = new JFrame(TITLE);
        frame.setSize(FIELD_SIZE, FIELD_SIZE + STATUS_PANEL_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int startX = (gd.getDisplayMode().getWidth() - FIELD_SIZE) / 2;
        int startY =  (gd.getDisplayMode().getHeight() - FIELD_SIZE - STATUS_PANEL_HEIGHT) / 2;
        frame.setLocation(startX, startY);
        frame.setResizable(false);

        canvasPanel = new Canvas();
        canvasPanel.setBackground(Color.white);

        JPanel msgPanel = new JPanel();
        lblMessage = new JLabel();
        msgPanel.add(lblMessage);

        frame.add(BorderLayout.CENTER, canvasPanel);
        frame.add(BorderLayout.SOUTH, msgPanel);
    }

    @Override
    public void init() {
        frame.setVisible(true);
    }

    @Override
    public void showMessage(String message) {
        lblMessage.setText(message);
    }

    @Override
    public GameCreator getCreator() {

        String[] select = new String[] {"из файла", "случайным образом"};
        Object result = JOptionPane.showOptionDialog(
                frame,
                "Выберите способ создания игры: ",
                "Создание игры",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, select, select[1]);

        return null;
    }

    @Override
    public void draw(GenerationBroker generation) {

    }

    @Override
    public void close() {

    }

    @Override
    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(frame, message, "Ошибка", JOptionPane.ERROR_MESSAGE);
        System.exit(-1);
    }

    private class Canvas extends JPanel {
        @Override
        public void paint(Graphics g) {
            super.paint(g);
        }
    }
}
