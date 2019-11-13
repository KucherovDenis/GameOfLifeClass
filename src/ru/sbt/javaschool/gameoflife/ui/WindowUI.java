package ru.sbt.javaschool.gameoflife.ui;

import ru.sbt.javaschool.gameoflife.GameException;
import ru.sbt.javaschool.gameoflife.creators.FileCreator;
import ru.sbt.javaschool.gameoflife.creators.GameCreator;
import ru.sbt.javaschool.gameoflife.creators.RandomCreator;
import ru.sbt.javaschool.gameoflife.entities.CellState;
import ru.sbt.javaschool.gameoflife.entities.FieldSize;
import ru.sbt.javaschool.gameoflife.entities.Generation;
import ru.sbt.javaschool.gameoflife.entities.GenerationBroker;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WindowUI implements UserInterface {

    private static final String GAME_TITLE = "Game of Life";
    private static final String DIALOG_INPUT_FILE_TITLE = "Выберите файл";
    private static final String CURRENT_DIRECTORY = ".";
    private static final String INPUT_FILE_DESCRIPTION = "TXT-File & XLS-File";
    private final String[] INPUT_FILE_EXTENSIONS = new String[]{"txt", "xls", "xlsx"};
    private final String MSG_FILE_NOT_SELECTED = "Создание игры не возможно.\n Не выбран файл загрузки.";
    private final String[] selection = new String[]{"из файла", "случайным образом"};
    private static final String CREATION_DIALOG_TITLE = "Создание игры";
    private static final String CREATION_DIALOG_MESSAGE = "Выберите способ создания игры: ";
    private static final String ENDING_DIALOG_TITLE = "Конец игры";
    private static final String ENDING_DIALOG_MESSAGE = "Игра окончена";
    private static final String ERROR_DIALOG_TITLE = "Ошибка";
    private static final String INPUT_DIALOG_FIELD_SIZE = "Укажите размер поля в формате %s.\nПо умолчанию 20*20.";

    private final int FIELD_SIZE = 500;
    private final int STATUS_PANEL_HEIGHT = 95;
    private final JFrame frame;
    private final JLabel lblMessage;

    private int cellWidth = -1;
    private int cellHeight = -1;
    private Generation currentGeneration = null;

    private final Canvas canvasPanel;
    private volatile boolean isStop = false;

    public WindowUI() {
        frame = new JFrame(GAME_TITLE);
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
        JButton btnStop = new JButton("Стоп");
        btnStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnStop.setEnabled(false);
                isStop = true;
            }
        });
        msgPanel.add(lblMessage);
        msgPanel.add(btnStop);


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
        JFileChooser fileChooser = new JFileChooser(CURRENT_DIRECTORY);
        fileChooser.setDialogTitle(DIALOG_INPUT_FILE_TITLE);
        FileNameExtensionFilter filter = new FileNameExtensionFilter(INPUT_FILE_DESCRIPTION, INPUT_FILE_EXTENSIONS);
        fileChooser.setFileFilter(filter);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showOpenDialog(frame);

        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().getAbsolutePath();
        } else {
            throw new GameException(MSG_FILE_NOT_SELECTED);
        }
    }

    private FieldSize getFieldSize() {
        String val = JOptionPane.showInputDialog(frame,
                String.format(INPUT_DIALOG_FIELD_SIZE, FieldSize.format()));

        return FieldSize.get(val);
    }

    @Override
    public GameCreator getCreator() {
        GameCreator creator;

        int result = JOptionPane.showOptionDialog(
                frame,
                CREATION_DIALOG_MESSAGE,
                CREATION_DIALOG_TITLE,
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE, null,
                selection, selection[1]);

        if (result == 0) {
            String fileName = getFileName();
            creator = new FileCreator(fileName);
        } else {
            try {
                FieldSize fieldSize = getFieldSize();
                creator = new RandomCreator(fieldSize);
            } catch (RuntimeException e) {
                creator = new RandomCreator();
            }
        }
        return creator;
    }

    @Override
    public void draw(GenerationBroker generation) {
        if (cellWidth == -1) {
            cellWidth = canvasPanel.getWidth() / generation.getSizeX();
        }
        if (cellHeight == -1) {
            cellHeight = canvasPanel.getHeight() / generation.getSizeY();
        }
        currentGeneration = new Generation(generation);
        canvasPanel.repaint();
    }

    private void dispose() {
        frame.setVisible(false);
        frame.dispose();
    }

    @Override
    public void close() {
        JOptionPane.showMessageDialog(frame,
                ENDING_DIALOG_MESSAGE,
                ENDING_DIALOG_TITLE,
                JOptionPane.INFORMATION_MESSAGE);

        dispose();
    }

    @Override
    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(frame,
                message,
                ERROR_DIALOG_TITLE,
                JOptionPane.ERROR_MESSAGE);

        dispose();
    }

    @Override
    public boolean isStop() {
        return isStop;
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
