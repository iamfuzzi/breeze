package me.fuzzi.breeze.resources;

import me.fuzzi.breeze.util.Resources;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;

public class ResourceMonitor {
    private static JFrame frame;
    private static JLabel usedMemoryLabel;
    private static JTextPane messageArea;

    private static MemoryMXBean memoryBean;

    // Шрифт JetBrains Mono
    private static Font jetBrainsMono;

    // Переменные для перемещения окна
    private static Point initialClick;

    public static void start() {
        // Загрузка шрифта из ресурсов
        try {
            byte[] fontData = Resources.getResourceAsBytes("breezedefaults/JetBrainsMono-Medium.ttf");
            jetBrainsMono = Font.createFont(Font.TRUETYPE_FONT, new java.io.ByteArrayInputStream(fontData)).deriveFont(14f);
        } catch (Exception e) {
            e.printStackTrace();
            // Если шрифт не загрузился, используем стандартный
            jetBrainsMono = new Font("Monospaced", Font.PLAIN, 14);
        }

        memoryBean = ManagementFactory.getMemoryMXBean();

        frame = new JFrame("Breeze | Resource Monitor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700); // Увеличиваем размер окна
        frame.setUndecorated(true); // Убираем декорации окна
        frame.setLocationRelativeTo(null); // Центрируем окно

        // Устанавливаем черный фон для окна
        frame.getContentPane().setBackground(Color.BLACK);

        // Создание панели для перемещения окна
        JPanel dragPanel = new JPanel();
        dragPanel.setBackground(new Color(30, 30, 30)); // Темно-серый цвет
        dragPanel.setPreferredSize(new Dimension(frame.getWidth(), 30)); // Высота 30px
        dragPanel.setBorder(BorderFactory.createEmptyBorder()); // Убираем обводку

        // Добавляем обработчики событий мыши для перемещения окна
        dragPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint(); // Запоминаем начальную точку клика
            }
        });
        dragPanel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                // Получаем текущее положение окна
                int thisX = frame.getLocation().x;
                int thisY = frame.getLocation().y;

                // Вычисляем смещение
                int xMoved = e.getX() - initialClick.x;
                int yMoved = e.getY() - initialClick.y;

                // Перемещаем окно
                int X = thisX + xMoved;
                int Y = thisY + yMoved;
                frame.setLocation(X, Y);
            }
        });

        // Создание панели с разделителем
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(700); // Устанавливаем начальное положение разделителя (700px для чата, 300px для информации)
        splitPane.setEnabled(false); // Запрещаем изменение положения разделителя
        splitPane.setBackground(Color.BLACK); // Черный фон для разделителя
        splitPane.setBorder(BorderFactory.createEmptyBorder()); // Убираем обводку у разделителя
        splitPane.setDividerSize(0); // Скрываем разделитель

        // Левая панель для сообщений (область чата)
        JPanel messagePanel = new JPanel(new BorderLayout());
        messagePanel.setBackground(Color.BLACK); // Черный фон для панели
        messagePanel.setBorder(BorderFactory.createEmptyBorder()); // Убираем обводку

        messageArea = new JTextPane();
        messageArea.setEditable(false);
        messageArea.setFont(jetBrainsMono); // Устанавливаем шрифт
        messageArea.setBackground(Color.BLACK); // Черный фон для текстовой области
        messageArea.setForeground(Color.WHITE); // Белый цвет текста по умолчанию
        messageArea.setBorder(BorderFactory.createEmptyBorder()); // Убираем обводку

        JScrollPane scrollPane = new JScrollPane(messageArea);
        scrollPane.setBackground(Color.BLACK); // Черный фон для скролл-панели
        scrollPane.getViewport().setBackground(Color.BLACK); // Черный фон для области просмотра
        scrollPane.setBorder(BorderFactory.createEmptyBorder()); // Убираем обводку
        messagePanel.add(scrollPane, BorderLayout.CENTER);

        // Правая панель для статистики (область информации)
        JPanel statsPanel = new JPanel(new GridLayout(1, 1));
        statsPanel.setBackground(Color.BLACK); // Черный фон для панели
        statsPanel.setBorder(BorderFactory.createEmptyBorder()); // Убираем обводку

        usedMemoryLabel = new JLabel("Used Memory: ");
        usedMemoryLabel.setFont(jetBrainsMono); // Устанавливаем шрифт
        usedMemoryLabel.setForeground(Color.WHITE); // Белый цвет текста
        usedMemoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
        usedMemoryLabel.setBackground(Color.BLACK); // Черный фон для метки
        usedMemoryLabel.setBorder(BorderFactory.createEmptyBorder()); // Убираем обводку

        statsPanel.add(usedMemoryLabel);

        splitPane.setLeftComponent(messagePanel);
        splitPane.setRightComponent(statsPanel);

        // Добавляем панель для перемещения и основную панель в окно
        frame.setLayout(new BorderLayout());
        frame.add(dragPanel, BorderLayout.NORTH); // Панель для перемещения вверху
        frame.add(splitPane, BorderLayout.CENTER); // Основная панель в центре

        Timer timer = new Timer(500, e -> updateResourceUsage());
        timer.start();

        frame.setVisible(true);
    }

    private static void updateResourceUsage() {
        usedMemoryLabel.setText("Used Memory: " + memoryBean.getHeapMemoryUsage().getUsed() / (1024 * 1024) + " MB");
    }

    public static final MonitorStream out = new MonitorStream(false);
    public static final MonitorStream err = new MonitorStream(true);

    public static class MonitorStream {
        private final boolean isErrorStream;

        public MonitorStream(boolean isErrorStream) {
            this.isErrorStream = isErrorStream;
        }

        public void print(Object o) {
            SwingUtilities.invokeLater(() -> {
                Color color = isErrorStream ? Color.RED : Color.WHITE; // Белый для обычного текста, красный для ошибок
                appendToMessageArea(o.toString(), color);
            });
        }

        public void println(Object o) {
            SwingUtilities.invokeLater(() -> {
                Color color = isErrorStream ? Color.RED : Color.WHITE; // Белый для обычного текста, красный для ошибок
                appendToMessageArea(o.toString() + "\n", color);
            });
        }

        private void appendToMessageArea(String text, Color color) {
            StyledDocument doc = (StyledDocument) messageArea.getDocument();
            try {
                SimpleAttributeSet attributes = new SimpleAttributeSet();
                StyleConstants.setForeground(attributes, color);
                StyleConstants.setFontFamily(attributes, jetBrainsMono.getFamily()); // Устанавливаем шрифт
                StyleConstants.setFontSize(attributes, jetBrainsMono.getSize()); // Устанавливаем размер шрифта
                doc.insertString(doc.getLength(), text, attributes);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
    }
}