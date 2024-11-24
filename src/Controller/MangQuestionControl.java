package Controller;

import java.io.FileNotFoundException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.stream.JsonWriter;

import Model.Questions;
import Model.SysData;



public class MangQuestionControl {
	private static MangQuestionControl instance = null;
	private ArrayList<Questions> questions = new ArrayList<Questions>();
	public static SysData sysData= new SysData();
	private List<Questions> easyQuestions;
	private List<Questions> mediumQuestions;
	private List<Questions> hardQuestions;
    private Random random;


	
	//  Singleton Instance
	public static MangQuestionControl getInstance() {
		if (instance == null) {
			instance = new MangQuestionControl();
		}
		return instance;
	}
	

	public ArrayList<Questions> getQuestions() { //return all the questions 
		sysData.LoadQuestions();
		return sysData.getQuestions();
	}

	public void addNewQuestion(Questions q) { // add question to the arrayList of questions and call function that write the question to Json file 

		if (q != null && !(getQuestions().contains(q))) {
			int size = sysData.getQuestions().size();
			q.setId(size);
			sysData.LoadQuestions();
			sysData.getQuestions().add(q);
			sysData.writeQuestionsToJsonFile();
		}

	}
	
	
	public void removeQuestionLocaly(Integer QuestionId) {
		int i = -1;
		int index = 0;
		sysData.LoadQuestions();
		
		for (Questions q : sysData.getQuestions()) {

			if (q.getid() == QuestionId) {

				i = index;
				break;
			}

			index++;
		}

		if (i == -1) {
			return;
		}
		//update the id of all question -  because of the removing. 
	
		if (i != -1) {
			sysData.getQuestions().remove(i);
			for (int c = i + 1; c < sysData.getQuestions().size(); c++) {
				sysData.getQuestions().get(c).setId(QuestionId);
				QuestionId++;

			}
		}
		
		sysData.writeQuestionsToJsonFile();
		
	}
	

	
	public void editQuestion(int questionId, Questions updatedQuestion) {
	    // Get the list of questions
	    List<Questions> questions = this.getQuestions();
	    for (Questions question : questions) {
	        if (question.getid() == questionId) {

	            // Update the question with new values
	            question.setQuestionText(updatedQuestion.getQuestionText());
	            question.setOptions(updatedQuestion.getOptions());
	            question.setCorrectOption(updatedQuestion.getCorrectOption());
	            question.setDiffculty(updatedQuestion.getDiffculty());
	            updateQuestionToJson(questions);

	            return;
	        }
	    }

	}
	
	private boolean updateQuestionToJson(List<Questions> questions) {
	    // Create a JsonObject to hold the updated questions
	    JsonObject updatedJson = new JsonObject();

	    // Create a JsonArray for the questions
	    JsonArray questionsJsonArray = new JsonArray();

	    for (Questions question : questions) {
	        JsonObject questionJsonObject = new JsonObject();
	        questionJsonObject.addProperty("question", question.getQuestionText());

	        JsonArray answersArray = new JsonArray();
	        for (String answer : question.getOptions()) {
	            answersArray.add(answer);
	        }
	        questionJsonObject.add("answers", answersArray);

	        Integer correct = question.getCorrectOption();
	        Integer difficulty = question.getDiffculty();

	        questionJsonObject.addProperty("correct_ans", correct.toString());
	        questionJsonObject.addProperty("difficulty", difficulty.toString());

	        questionsJsonArray.add(questionJsonObject);
	    }

	    // Add the questions array to the updated Json object
	    updatedJson.add("questions", questionsJsonArray);

	    // Write the updated JSON back to the file with proper indentation
	    try (Writer writer = new FileWriter("src/QuestionsAndAnswers.json")) {
	        JsonWriter jsonWriter = new JsonWriter(writer);
	        jsonWriter.setIndent(" ");
	        new Gson().toJson(updatedJson, jsonWriter);
	        return true; 
	    } catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	
	public Questions getRandomQuestion(String level) {
        List<Questions> questions;
        switch (level.toLowerCase()) {
            case "easy":
                questions = easyQuestions;
                break;
            case "medium":
                questions = mediumQuestions;
                break;
            case "hard":
                questions = hardQuestions;
                break;
            default:
                return null;
        }

        if (!questions.isEmpty()) {
            int randomIndex = random.nextInt(questions.size());
            return questions.get(randomIndex);
        }

        return null;
    }
	    
	public boolean validateAdminCredentials(String userName, String password) {
        String storedPassword = sysData.getAdmins().get(userName);
        return storedPassword != null && storedPassword.equals(password);
    }

	

}
