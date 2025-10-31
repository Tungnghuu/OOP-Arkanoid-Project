package app;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class GameHistoryTable extends JFrame {
    public GameHistoryTable(List<Object[]> records) {
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        //  Giao dien background
        getContentPane().setBackground(new Color(20, 20, 30));
        setLayout(new BorderLayout());

        // Cac cot
        String[] columnNames = {"Number","playerId", "Time", "Score"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (Object[] data : records) {
            model.addRow(data);
        }

        JTable table = new JTable(model);
        table.setFont(new Font("Consolas", Font.BOLD, 16));
        table.setForeground(Color.WHITE);
        table.setBackground(new Color(25, 25, 40));
        table.setRowHeight(45);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(new Color(0, 180, 255));
        table.setSelectionForeground(Color.BLACK);

        //  kieu phan header
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Consolas", Font.BOLD, 18));
        header.setBackground(new Color(0, 120, 255));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(0, 45));
        header.setReorderingAllowed(false);

        // Render cac hang
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {

                Component c = super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);

                if (!isSelected) {
                    if (row % 2 == 0) {
                        c.setBackground(new Color(35, 35, 55));
                    } else {
                        c.setBackground(new Color(45, 45, 70));
                    }
                    c.setForeground(new Color(0, 255, 180));
                } else {
                    c.setBackground(new Color(0, 200, 255));
                    c.setForeground(Color.BLACK);
                }
                setHorizontalAlignment(SwingConstants.CENTER);
                return c;
            }
        };

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(0, 200, 255), 2, true));
        scrollPane.setBackground(new Color(20, 20, 30));

        //  Tieu de
        JLabel title = new JLabel("Leaderboard", SwingConstants.CENTER);
        title.setFont(new Font("Consolas", Font.BOLD, 26));
        title.setForeground(new Color(0, 255, 180));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        add(title, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
}
