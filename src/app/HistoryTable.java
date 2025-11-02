package app;

import javax.swing.*;
import java.awt.Font;
import java.awt.Color;
import java.awt.BorderLayout;
import javax.swing.table.DefaultTableModel;
import java.awt.Dimension;
import javax.swing.table.JTableHeader;
import java.util.List;

class HistoryTable extends JFrame {
    public HistoryTable(List<String> record) {
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        //  Giao dien background
        getContentPane().setBackground(new Color(20, 20, 30));
        setLayout(new BorderLayout());

        // Cac cot
        String[] columnNames = {"Score", "Time"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        // for (String data : record) {
        //     model.addRow(data);
        // }

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
        //TODO: fix this
    }
} 