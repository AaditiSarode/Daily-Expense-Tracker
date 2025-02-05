import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class ExpenseTracker {

    private JFrame frame;
    private JTextField dateField, categoryField, amountField;
    private JTable expenseTable;
    private DefaultTableModel tableModel;
    private ArrayList<Expense> expenses;
    
    public ExpenseTracker() {
        expenses = new ArrayList<>();
        frame = new JFrame("Expense Tracker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());
        
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel dateLabel = new JLabel("Date (yyyy-mm-dd):");
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        dateField = new JTextField();
        
        JLabel categoryLabel = new JLabel("Category:");
        categoryLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        categoryField = new JTextField();
        
        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        amountField = new JTextField();
        
        JButton addButton = new JButton("Add Expense");
        addButton.setBackground(new Color(0, 123, 255));
        addButton.setForeground(Color.WHITE);
        addButton.setFont(new Font("Arial", Font.BOLD, 14));
        
        inputPanel.add(dateLabel);
        inputPanel.add(dateField);
        inputPanel.add(categoryLabel);
        inputPanel.add(categoryField);
        inputPanel.add(amountLabel);
        inputPanel.add(amountField);
        inputPanel.add(new JLabel());  
        inputPanel.add(addButton);
        
        frame.add(inputPanel, BorderLayout.NORTH);
        
        tableModel = new DefaultTableModel(new Object[]{"Date", "Category", "Amount"}, 0);
        expenseTable = new JTable(tableModel);
        expenseTable.setRowHeight(30);
        expenseTable.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane tableScrollPane = new JScrollPane(expenseTable);
        frame.add(tableScrollPane, BorderLayout.CENTER);
        
        JPanel totalPanel = new JPanel();
        totalPanel.setBackground(new Color(240, 240, 240));
        totalPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel totalLabel = new JLabel("Total Expenses: $0.00");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalPanel.add(totalLabel);
        frame.add(totalPanel, BorderLayout.SOUTH);
        
        addWatermark(frame);
        
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String date = dateField.getText();
                    String category = categoryField.getText();
                    double amount = Double.parseDouble(amountField.getText());
                    
                    Expense expense = new Expense(date, category, amount);
                    expenses.add(expense);
                    
                    tableModel.addRow(new Object[]{date, category, amount});
                    
                    updateTotal(totalLabel);
                    
                    dateField.setText("");
                    categoryField.setText("");
                    amountField.setText("");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid input for amount", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        frame.setVisible(true);
    }

    private void addWatermark(JFrame frame) {

        JLabel watermarkLabel = new JLabel("Expense Tracker", SwingConstants.CENTER);
        watermarkLabel.setFont(new Font("Serif", Font.ITALIC, 40));
        watermarkLabel.setForeground(new Color(0, 0, 0, 50));  // Semi-transparent black
        watermarkLabel.setBounds(150, 100, 300, 500);
        
        frame.getLayeredPane().add(watermarkLabel, JLayeredPane.DEFAULT_LAYER);
    }

    private void updateTotal(JLabel totalLabel) {
        double total = 0.0;
        for (Expense expense : expenses) {
            total += expense.getAmount();
        }
        totalLabel.setText("Total Expenses: $" + String.format("%.2f", total));
    }

    public static void main(String[] args) {
        new ExpenseTracker();
    }
}

class Expense {
    private String date;
    private String category;
    private double amount;

    public Expense(String date, String category, double amount) {
        this.date = date;
        this.category = category;
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "date='" + date + '\'' +
                ", category='" + category + '\'' +
                ", amount=" + amount +
                '}';
    }
}
