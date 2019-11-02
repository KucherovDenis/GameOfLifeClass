package ru.sbt.javaschool.gameoflife.ui;

import ru.sbt.javaschool.gameoflife.GameException;
import ru.sbt.javaschool.gameoflife.creators.FileCreator;
import ru.sbt.javaschool.gameoflife.creators.GameCreator;
import ru.sbt.javaschool.gameoflife.creators.RandomCreator;
import ru.sbt.javaschool.gameoflife.entities.CellState;
import ru.sbt.javaschool.gameoflife.entities.Generation;
import ru.sbt.javaschool.gameoflife.entities.GenerationBroker;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;

public class WindowUI implements UserInterface {

    private static final String TITLE = "Game of Life";
    private final int FIELD_SIZE = 500;
    private final int STATUS_PANEL_HEIGHT = 95;
    private final JFrame frame;
    private final JLabel lblMessage;

    private int cellWidth = -1;
    private int cellHeight = -1;
    private Generation currentGeneration = null;


    private final Canvas canvasPanel;

    public WindowUI() {
        frame = new JFrame(TITLE);
        frame.setSize(FIELD_SIZE, FIELD_SIZE + STATUS_PANEL_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int startX = (gd.getDisplayMode().getWidth() - FIELD_SIZE) / 2;
        int startY = (gd.getDisplayMode().getHeight() - FIELD_SIZE - STATUS_PANEL_HEIGHT) / 2;
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

    private String getFileName() {
        JFileChooser fileChooser = new JFileChooser(".");
        fileChooser.setDialogTitle("Выберите файл");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("TXT-File & XLS-File", "txt", "xls", "xlsx");
        fileChooser.setFileFilter(filter);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showOpenDialog(frame);

        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().getAbsolutePath();
        } else {
            throw new GameException("Создание игры не возможно.\n Не выбран файл загрузки.");
        }
    }

    @Override
    public GameCreator getCreator() {
        GameCreator creator;
        String[] select = new String[]{"из файла", "случайным образом"};
        int result = JOptionPane.showOptionDialog(
                frame,
                "Выберите способ создания игры: ",
                "Создание игры",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, select, select[1]);
        if (result == 0) {
            String fileName = getFileName();
            creator = new FileCreator(fileName);
        } else {
            creator = new RandomCreator();
        }
        return creator;
    }

    @Override
    public void draw(GenerationBroker generation) {
        if (cellWidth == -1) {
            cellWidth = canvasPanel.getWidth() / generation.getSizeX();
        }
        if (cellHeight == -1) {
            cellHeight = canvasPanel.getHeight();
            cellHeight = canvasPanel.getHeight() / generation.getSizeY();
        }
        currentGeneration = new Generation(generation);
        canvasPanel.repaint();
    }

    @Override
    public void close() {
        JOptionPane.showMessageDialog(frame, "Игра окончена", "Конец игры", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
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
            if (currentGeneration == null) return;
            for (int x = 0; x < currentGeneration.getSizeX(); x++) {
                for (int y = 0; y < currentGeneration.getSizeY(); y++) {
                    if (currentGeneration.getCell(x, y).getState() == CellState.ALIVE) {
                        g.fillRect(x * cellWidth, y * cellHeight, cellWidth, cellHeight);
                    }
                }
            }

        }
    }
}
