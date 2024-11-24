package Model;


import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.event.AncestorListener;
import javax.swing.plaf.basic.BasicOptionPaneUI.ButtonActionListener;
import javax.swing.JScrollPane;
import javax.swing.DefaultRowSorter;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import Controller.MangQuestionControl;

public class QuestionManagment extends JFrame   {

    JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;
	private JTextField searchField;
    private TableRowSorter<DefaultTableModel> sorter;
    static  SysData sysData = new SysData();
    static MangQuestionControl mangQuestionControl=new MangQuestionControl();
    boolean validInput = false;


    public QuestionManagment() {
        initialize();
        sysData = SysData.getInstance();
        sysData.LoadQuestions(); // Load questions from the JSON file
        populateTableWithData();

    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 724, 464);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Question");
        tableModel.addColumn("Correct Answer");
        tableModel.addColumn("Difficulty");

        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 63, 669, 267);
        frame.getContentPane().add(scrollPane);
        
        searchField = new JTextField();
        searchField.setColumns(20);
        searchField.setBounds(348, 27, 200, 25);
        frame.getContentPane().add(searchField);
        
        JButton btnAdd = new JButton("Add Question");
        btnAdd.setBounds(10, 354, 120, 30);
        btnAdd.addActionListener(e -> showAddQuestionPopup());
        frame.getContentPane().add(btnAdd);
        
        JButton btnEdit = new JButton("Edit Question");
        btnEdit.setBounds(175, 354, 120, 30);
        btnEdit.addActionListener(e -> editQuestion());
        frame.getContentPane().add(btnEdit);

        JButton btnDelete = new JButton("Delete Question");
        btnDelete.setBounds(346, 354, 120, 30);
        btnDelete.addActionListener(e -> deleteQuestion());
        frame.getContentPane().add(btnDelete);

        JButton btnShow = new JButton("Show Question");
        btnShow.setBounds(518, 354, 120, 30);
        btnShow.addActionListener(e -> showQuestion());
        frame.getContentPane().add(btnShow);

        // Create a button for performing the search
        JButton searchButton = new JButton("Search");
        searchButton.setBounds(558, 27, 80, 25);
        searchButton.addActionListener(new ActionListener() {
          
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				performSearch();
			}
        });
        frame.getContentPane().add(searchButton);

        // Set up a sorter for the table
        sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter((RowSorter<? extends TableModel>) sorter);
    }

    private void performSearch() {
        String query = searchField.getText().toLowerCase();
        ((DefaultRowSorter<DefaultTableModel, Integer>) sorter).setRowFilter(RowFilter.regexFilter(query));    
    }
    
    private void populateTableWithData() {
       
        for (Questions question : SysData.getInstance().getQuestions()) {
            Object[] rowData = {question.getQuestionText(), question.getCorrectOption(), question.getDiffculty()};
            System.out.println(question.getDiffculty());
            tableModel.addRow(rowData);
        }
    }


    private void showAddQuestionPopup() {
        JTextField questionField = new JTextField();
        JTextField answer1Field = new JTextField();
        JTextField answer2Field = new JTextField();
        JTextField answer3Field = new JTextField();
        JTextField answer4Field = new JTextField();
        JTextField correctAnswerField = new JTextField();
        JTextField difficultyField = new JTextField();

        Object[] fields = {
                "Question:", questionField,
                "Answer 1:", answer1Field,
                "Answer 2:", answer2Field,
                "Answer 3:", answer3Field,
                "Answer 4:", answer4Field,
                "Correct Answer (1-4):", correctAnswerField,
                "Difficulty:", difficultyField
        };

        int result = JOptionPane.showConfirmDialog(null, fields, "Add New Question", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
        	try {
                // Extract user input
                String questionText = questionField.getText();
                String[] answers = {answer1Field.getText(), answer2Field.getText(), answer3Field.getText(), answer4Field.getText()};
                int correctAnswerIndex = Integer.parseInt(correctAnswerField.getText()) - 1;  // Convert to 0-based index
                int difficulty = Integer.parseInt(difficultyField.getText());

                // Validate difficulty, correct answer, and answers format
                if (isValidDifficulty(difficulty) && isValidCorrectAnswer(correctAnswerIndex) && isValidAnswersFormat(answers)) {
                    // Create a new question object
                    Questions newQuestion = new Questions(questionText, answers, correctAnswerIndex, difficulty, sysData.getQuestions().size());

                    // Add the new question to sysData
                    mangQuestionControl.addNewQuestion(newQuestion);

                    // Update the table
                    tableModel.addRow(new Object[]{newQuestion.getQuestionText(), newQuestion.getCorrectOption(), newQuestion.getDiffculty()});
                    validInput = true; // Set flag to exit the loop
                } else {
                    StringBuilder errorMessage = new StringBuilder("Invalid input:\n");
                    // Display specific error messages based on validation conditions
                    if (!isValidDifficulty(difficulty)) {
                        errorMessage.append("Please enter a difficulty value between 1 and 3.\n");
                    } else if (!isValidCorrectAnswer(correctAnswerIndex)) {
                        errorMessage.append("Please enter a correct answer value between 1 and 4.\n");
                    } else if (!isValidAnswersFormat(answers)) {
                        errorMessage.append("Please provide a non-empty string for each answer.\n");
                    }
                    JOptionPane.showMessageDialog(null, errorMessage.toString());
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid number format. Please enter valid numbers for Correct Answer and Difficulty.");
            }
        } else {
            // Cancelled or closed the dialog
            validInput = true; // Exit the loop without adding a question
        }
    }
    
    private void editQuestion() {
        int selectedRow = table.getSelectedRow();

        if (selectedRow != -1) {
            // Get the selected question
            Questions selectedQuestion = SysData.getInstance().getQuestions().get(selectedRow);

            // Display a dialog for editing
            showEditQuestionDialog(selectedQuestion);

            // Refresh the table after editing
            refreshTable();
        } else {
            JOptionPane.showMessageDialog(null, "Please select a question to edit.");
        }
    }

    private void showEditQuestionDialog(Questions question) {
        JTextField questionField = new JTextField(question.getQuestionText());
        JTextField answer1Field = new JTextField(question.getOptions()[0]);
        JTextField answer2Field = new JTextField(question.getOptions()[1]);
        JTextField answer3Field = new JTextField(question.getOptions()[2]);
        JTextField answer4Field = new JTextField(question.getOptions()[3]);
        JTextField correctAnswerField = new JTextField(String.valueOf(question.getCorrectOption() + 1));  // Convert to 1-based index
        JTextField difficultyField = new JTextField(String.valueOf(question.getDiffculty()));

        Object[] fields = {
                "Question:", questionField,
                "Answer 1:", answer1Field,
                "Answer 2:", answer2Field,
                "Answer 3:", answer3Field,
                "Answer 4:", answer4Field,
                "Correct Answer (1-4):", correctAnswerField,
                "Difficulty:", difficultyField
        };

            int result = JOptionPane.showConfirmDialog(null, fields, "Edit Question", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                // Update the question object with new values
                question.setQuestionText(questionField.getText());
                System.out.println(questionField.getText());
                String[] answers = {answer1Field.getText(), answer2Field.getText(), answer3Field.getText(), answer4Field.getText()};
                question.setOptions(answers);
                int correctAnswerIndex = Integer.parseInt(correctAnswerField.getText()) - 1;
                question.setCorrectOption(correctAnswerIndex);
                int difficulty = Integer.parseInt(difficultyField.getText());
                question.setDiffculty(difficulty);

                // Call the SysData method to edit the question in the JSON file
                mangQuestionControl.editQuestion(question.getid(), question);

                // Refresh the table after editing
                refreshTable();
            }
        }


    private void refreshTable() {
        // Clear the current table data
        tableModel.setRowCount(0);

        // Repopulate the table with updated data
        populateTableWithData();
    }


    private void deleteQuestion() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int questionId = sysData.getQuestions().get(selectedRow).getid();
            // Call the SysData method to remove the question
            mangQuestionControl.removeQuestionLocaly(questionId);
            // Update the table
            tableModel.removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(null, "Please select a question to delete.");
        }
    }



    private void showQuestion() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String question = (String) table.getValueAt(selectedRow, 0);
            Integer correctAnswerIndex = (Integer) table.getValueAt(selectedRow, 1);
            Integer difficulty = (Integer) table.getValueAt(selectedRow, 2);

            // Get the question object from SysData
            List<Questions> questionsList = SysData.getInstance().getQuestions();
            Questions selectedQuestion = questionsList.get(selectedRow);

            // Extract answers from the selected question
            String[] answers = selectedQuestion.getOptions();

            // Build the message including all answers and highlight the correct one
            StringBuilder messageBuilder = new StringBuilder();
            messageBuilder.append("Question: ").append(question).append("\n");
            for (int i = 0; i < answers.length; i++) {
                messageBuilder.append(answers[i]).append("\n");
            }
            messageBuilder.append("Correct Answer: ").append(correctAnswerIndex + 1).append("\n");
            messageBuilder.append("Difficulty: ").append(difficulty);

            // Show the message dialog
            JOptionPane.showMessageDialog(null, messageBuilder.toString());
        } else {
            JOptionPane.showMessageDialog(null, "Please select a question to show.");
        }
    }

	private boolean isValidDifficulty(int difficulty) {
	    return difficulty >= 1 && difficulty <= 3;
	}
	
	private boolean isValidCorrectAnswer(int correctAnswer) {
	    return correctAnswer >= 0 && correctAnswer <= 3;
	}
	
	private boolean isValidAnswersFormat(String[] answers) {
	    // Add more specific validation logic if needed
	    return answers.length == 4 && !Arrays.stream(answers).anyMatch(String::isEmpty);
	}
}