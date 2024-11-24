package Model;

import java.util.Arrays;
import java.util.Objects;

public class Questions {
	    private String questionText;
	    private String[] options;
	    private int correctOption;
		private Integer id;
		private int diffculty; 

        
	    public Questions(String questionText, String[] options, int correctOption, int diffculty , int id ) {
	        this.questionText = questionText;
	        this.options = options;
	        this.correctOption = correctOption;
	        this.id=id;
	        this.diffculty = diffculty ; 
	    }
	    
	    public Questions(String questionText, String[] options, int correctOption, int diffculty) {
	        this.questionText = questionText;
	        this.options = options;
	        this.correctOption = correctOption;
	        this.diffculty = diffculty ; 
	    }

		public String getQuestionText() {
			return questionText;
		}

		public void setQuestionText(String questionText) {
			this.questionText = questionText;
		}

		public String[] getOptions() {
			return options;
		}

		public void setOptions(String[] options) {
			this.options = options;
		}

		public int getCorrectOption() {
			return correctOption;
		}

		public void setCorrectOption(int correctOption) {
			this.correctOption = correctOption;
		}
		public void setId(int id) {
			this.id = id;
		}
		public int getid() {
			return id;
		}
		public void setDiffculty(int diffculty) {
			this.diffculty= diffculty;
		}
		public int getDiffculty() {
			return diffculty;
		}

		@Override
		public String toString() {
			return "Questions [questionText=" + questionText + ", options=" + Arrays.toString(options)
					+ ", correctOption=" + correctOption + ", id=" + id + ", diffculty=" + diffculty + "]";
		}

	 
	 

	}


