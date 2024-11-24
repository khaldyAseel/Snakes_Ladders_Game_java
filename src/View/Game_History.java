package View;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.text.SimpleDateFormat;

import java.util.Comparator;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import Controller.GameController;
import Model.GameDetails;
import Model.GameObserver;
import javax.swing.ImageIcon;

public class Game_History extends JFrame {
	private JTable table;
    private DefaultTableModel tableModel;
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblNewLabel;
    private GameController gameController = new GameController(null);

	public Game_History() {
		 setTitle("Game History");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 20, 1529, 939);
		contentPane = new JPanel();

		setContentPane(contentPane);
		   // Make the frame undecorated (no title bar, no minimize/maximize/close buttons)
        setUndecorated(true);
		 // Define the columns for the JTable
        String[] columnNames = {"Time", "Difficulty", "Winner"};

        // Use Gson to read the JSON file and convert it to a List of GameDetails objects
        Gson gson = new Gson();
	    java.lang.reflect.Type gameListType = new TypeToken<ArrayList<GameDetails>>(){}.getType();
        List<GameDetails> gameList = null;
        try (FileReader reader = new FileReader("game_history.json")) {
            gameList = gson.fromJson(reader, gameListType);
            // Sort the list by time
            Collections.sort(gameList, new Comparator<GameDetails>() {
                SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss");
                @Override
                public int compare(GameDetails o1, GameDetails o2) {
                    try {
                        return dateFormat.parse(o1.time).compareTo(dateFormat.parse(o2.time));
                    } catch (ParseException e) {
                        throw new IllegalArgumentException(e);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Convert the List of GameDetails to a 2D Object array for the JTable data
        Object[][] data = new Object[gameList.size()][3];

        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column)
            {	
                return false; // Make all cells non-editable
            }
        };
        
        // Create the JTable with the data and column names
        tableModel.addColumn("Winner");
        tableModel.addColumn("Difficulty");
        tableModel.addColumn("Time");
        table = new JTable(tableModel);
        table.setBounds(10, 11, 748, 448);
        contentPane.add(table);
     // Center text in table cells
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);
        table.getTableHeader().setReorderingAllowed(false); // Prevent column reordering
        table.setFont(new Font("Serif", Font.BOLD, 20)); // Set the table font size

        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.CENTER); // Center text
        headerRenderer.setForeground(Color.BLACK);
     // Apply the renderer to each column header
        for (int i = 0; i < table.getModel().getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }

        // Custom renderer for table cells
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(JLabel.CENTER); // Center text
        cellRenderer.setBackground(new Color(204, 182, 37));
        cellRenderer.setFont(new Font("Serif", Font.BOLD, 30)); // Set font


        // Apply the renderer to each column
        for (int i = 0; i < table.getModel().getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }
        // Set row height and remove grid lines for a cleaner look
        table.setRowHeight(30); // Adjust row height as needed
        table.setShowGrid(false);
           table.setShowHorizontalLines(false);
           table.setShowVerticalLines(false);
           // Create a JLabel to display the sorting note
           JLabel lblSortingNote = new JLabel("Note: The table is sorted by time in ascending order. Quickest wins are at the top.");
           lblSortingNote.setBounds(0, 0, 800, 17);
           lblSortingNote.setHorizontalAlignment(SwingConstants.CENTER); // Center the label text
           lblSortingNote.setFont(new Font("Tahoma", Font.PLAIN, 14)); // Set the desired font
           lblSortingNote.setForeground(new Color(128, 128, 128)); // Set a grey color for the note text

           // Add the note label to the content pane at the top (NORTH)
           contentPane.add(lblSortingNote);
           contentPane.setLayout(null);
           contentPane.setLayout(null);
           JScrollPane scrollPane = new JScrollPane(table);
           scrollPane.setBounds(230, 150, 963, 583);
           contentPane.add(scrollPane);
           table.setFillsViewportHeight(true);
           table.setForeground(new Color(0, 100, 0));
         
        // Add rows to the table model
           if (gameList != null) {
               for (GameDetails details : gameList) {
                   Object[] row = new Object[] {details.winnerName , details.difficulty,details.time };
                   tableModel.addRow(row);
               }
           }
        // Calculate the position for the "Back" button
           int buttonWidth = 100;
           int buttonHeight = 50;
           int buttonX = 800 - buttonWidth - 30; // 30 pixels padding from the right
           int buttonY = 500 - buttonHeight - 30;
                      
                      lblNewLabel = new JLabel("");
                      lblNewLabel.setIcon(new ImageIcon(Game_History.class.getResource("/images/history.jpeg")));
                      lblNewLabel.setBounds(-48, 0, 1610, 943);
                      contentPane.add(lblNewLabel);
                      
                      JLabel lblNewLabel_1 = new JLabel("");
                      lblNewLabel_1.setBounds(1314, 847, 205, 70);
                      contentPane.add(lblNewLabel_1);
                      lblNewLabel_1.addMouseListener(new MouseAdapter() {
                          @Override
                          public void mouseClicked(MouseEvent e) {
                               Game_History.this.setVisible(false); // Close the current GameHistory window
                               new MainScreen().setVisible(true); // Open the MainScreen window
                               gameController.buttonClick();
                           }
                      });
                                           
		
	}
	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}

	public DefaultTableModel getTableModel() {
		return tableModel;
	}

	public void setTableModel(DefaultTableModel tableModel) {
		this.tableModel = tableModel;
	}
}
