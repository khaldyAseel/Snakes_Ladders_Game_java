package Model;

import java.io.FileNotFoundException;


import java.util.Random;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.stream.JsonReader;


import java.util.Random;


public class SysData {
	private static SysData instance ;
	private ArrayList<Questions> questions = new ArrayList<Questions>();
	private Map<String, String> adminCredentials;
	private static HashMap<String, Questions> easyQuestions;
	private static HashMap<String, Questions> mediumQuestions;
	private static HashMap<String, Questions> HardQuestions;
	public static HashMap<String,Questions> questionsPOPUP;
	private static HashMap<Integer, String> questionPositions;


    public SysData() {
    	SysData.easyQuestions = new HashMap<String, Questions>(); 
		SysData.mediumQuestions = new HashMap<String, Questions>(); 
		SysData.HardQuestions = new HashMap<String, Questions>();
		SysData.questionsPOPUP=new HashMap<String, Questions>();
        adminCredentials = new HashMap<>();
        adminCredentials.put("admin1", "123");
        adminCredentials.put("admin2", "111");
        adminCredentials.put("admin3", "222");

		// Initialize the map somewhere in your Game class constructor or an initialization block
		questionPositions = new HashMap<>();
		questionPositions.put(3, "easy"); // position for easy question
		questionPositions.put(14, "medium"); // position for medium question
		questionPositions.put(29, "medium"); // position for medium question
		questionPositions.put(22, "hard"); 
		questionPositions.put(46, "hard"); // position for hard question
		questionPositions.put(17, "hard"); // position for hard question

        
    }

	
	public Map<String, String> getAdminCredentials() {
		return adminCredentials;
	}


	public void setAdminCredentials(Map<String, String> adminCredentials) {
		this.adminCredentials = adminCredentials;
	}


	public static HashMap<String, Questions> getEasyQuestions() {
		return easyQuestions;
	}


	public void setEasyQuestions(HashMap<String, Questions> easyQuestions) {
		SysData.easyQuestions = easyQuestions;
	}


	public static HashMap<String, Questions> getMediumQuestions() {
		return mediumQuestions;
	}


	public void setMediumQuestions(HashMap<String, Questions> mediumQuestions) {
		SysData.mediumQuestions = mediumQuestions;
	}


	public static HashMap<String, Questions> getHardQuestions() {
		return HardQuestions;
	}


	public void setHardQuestions(HashMap<String, Questions> hardQuestions) {
		HardQuestions = hardQuestions;
	}


	public static HashMap<String, Questions> getQuestionsPOPUP() {
		return questionsPOPUP;
	}


	public void setQuestionsPOPUP(HashMap<String, Questions> questionsPOPUP) {
		SysData.questionsPOPUP = questionsPOPUP;
	}


	public void setQuestions(ArrayList<Questions> questions) {
		this.questions = questions;
	}


	//  Singleton Instance
	public static SysData getInstance() {
		if(instance == null)
			instance = new SysData();
		return instance; 
	}

	public ArrayList<Questions> getQuestions() { //return all the questions 
		return questions;
	}
	
	public Map<String, String> getAdmins() { //return all the questions 
		return adminCredentials;
	}

	public void LoadQuestions() { // get all the question from json file 

		ArrayList<Questions> questions = new ArrayList<Questions>();
		this.getQuestions().clear();

		Gson gson = new Gson();
		JsonReader reader = null;
		try {
			reader = new JsonReader(new FileReader("QuestionsAndAnswers.json"));
		//	reader = new JsonReader(new FileReader("src/QuestionsAndAnswers.json"));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		JsonObject jsonObject = new JsonParser().parse(reader).getAsJsonObject();

		final JsonArray data = jsonObject.getAsJsonArray("questions");

		for (JsonElement element : data) {

			Questions q;

			String questionText = ((JsonObject) element).get("question").getAsString();

			JsonArray answersArray = (((JsonObject) element).getAsJsonArray("answers"));
			@SuppressWarnings("unchecked")
			ArrayList<String> answers = gson.fromJson(answersArray, ArrayList.class);
			
			Integer correct = ((JsonObject) element).get("correct_ans").getAsInt();
			Integer diffculty = ((JsonObject) element).get("difficulty").getAsInt();
             
			 String[] answerArray = new String[answers.size()];
		        answers.toArray(answerArray);
	
			
			if (!this.getQuestions().isEmpty()) {
			
				q = new Questions(questionText,answerArray, correct, diffculty, this.getQuestions().size());
				this.getQuestions().add(q);

			} else {
				q = new Questions(questionText,answerArray, correct, diffculty,0);
				this.getQuestions().add(q);

			}

			questions.add(q);
			questionsPOPUP.put(q.getQuestionText(),q);
		}

		this.getQuestions();
	
	}
	public boolean saveQuestions(List<Questions> questions) {
	    // Write JSON file
	    try (FileWriter file = new FileWriter("src/QuestionsAndAnswers.json")) {
	        JsonArray questionsArray = new JsonArray();

	        for (Questions question : this.getQuestions()) {
	            JsonObject questionObject = new JsonObject();

	            // Question text
	            questionObject.addProperty("question", question.getQuestionText());

	            // Answers array
	            JsonArray answersArray = new JsonArray();
	            for (int i = 0; i < question.getOptions().length; i++) {
	                answersArray.add(new JsonPrimitive(question.getOptions()[i]));
	            }
	            questionObject.add("answers", answersArray);

	            // Correct answer index
	            questionObject.addProperty("correct_ans", question.getCorrectOption());

	            // diffculty level
	            questionObject.addProperty("difficulty", question.getDiffculty());

	            questionsArray.add(questionObject);
	        }

	        JsonObject json = new JsonObject();
	        json.add("questions", questionsArray);

	        file.write(json.toString());
	        file.flush();
	        return true;
	    } catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	////////////////////////////////////////////

	//Checks the difficulty and add the questions to the right places
		public static void putQuestions(HashMap <String, Questions> questions)
		{
			SysData.getInstance().LoadQuestions(); // Load questions from the JSON file
			SysData.easyQuestions.clear();
			SysData.mediumQuestions.clear();
			SysData.HardQuestions.clear();
			for(Questions q : questionsPOPUP.values())
			{
			if(q.getDiffculty()==1)
				SysData.easyQuestions.put(q.getQuestionText(), q);
			if(q.getDiffculty() == 2)
				SysData.mediumQuestions.put(q.getQuestionText(), q);
			if(q.getDiffculty() == 3)
				SysData.HardQuestions.put(q.getQuestionText(), q);
			}
		}
		
		

		// Method to get a random question based on the player's position
		public static Questions getQuestionForPosition(int position) {
		    String difficulty = questionPositions.get(position);
		    
		    if (difficulty != null) {
		        switch (difficulty) {
		            case "easy":
		                return getRandomQuestion(easyQuestions);
		            case "medium":
		                return getRandomQuestion(mediumQuestions);
		            case "hard":
		                return getRandomQuestion(HardQuestions);
		            default:
		                return null; // No question for this position
		        }
		    }
		    return null;
		}
		public static Questions getQuestionForPosition(String difficulty) {
			  if (difficulty != null) {
			        switch (difficulty) {
			            case "easy":
			                return getRandomQuestion(easyQuestions);
			            case "medium":
			                return getRandomQuestion(mediumQuestions);
			            case "hard":
			                return getRandomQuestion(HardQuestions);
			            default:
			                return null; // No question for this position
			        }
			    }
			    return null;
			}

		public static Questions getQuestionLevel(String difficulty) {		    
		    if (difficulty != null) {
		        switch (difficulty) {
		            case "easy":
		                return getRandomQuestion(easyQuestions);
		            case "medium":
		                return getRandomQuestion(mediumQuestions);
		            case "hard":
		                return getRandomQuestion(HardQuestions);
		            default:
		                return null; // No question for this position
		        }
		    }
		    return null;
		}
		// Helper method to get a random question from a map of questions
		private static Questions getRandomQuestion(HashMap<String, Questions> questionsMap) {
		    if (questionsMap == null || questionsMap.isEmpty()) {
		        return null;
		    }
		    List<String> keys = new ArrayList<>(questionsMap.keySet());
		    String randomKey = keys.get(new Random().nextInt(keys.size()));
		    return questionsMap.get(randomKey);
		}
		public static String getRandomQuestion(String [] difficultyLevels) {
		    if (difficultyLevels == null ) {
		        return null;
		    }
		    Random random = new Random();
	        int randomIndex = random.nextInt(difficultyLevels.length);
	        String randomDifficulty = difficultyLevels[randomIndex];
		    
		    return randomDifficulty;
		}
		
	public void writeQuestionsToJsonFile() {
	    JsonArray questionsArray = new JsonArray();
	    for (Questions q : this.getQuestions()) {
	        JsonObject questionObject = new JsonObject();

	        // Question text
	        questionObject.addProperty("question", q.getQuestionText());

	        // Answers array
	        JsonArray answersArray = new JsonArray();
	        for (int i = 0; i < q.getOptions().length; i++) {
	            answersArray.add(q.getOptions()[i]);
	        }
	        questionObject.add("answers", answersArray);

	        // Correct answer index
	        questionObject.addProperty("correct_ans", String.valueOf(q.getCorrectOption()));

	        // Difficulty level
	        questionObject.addProperty("difficulty", String.valueOf(q.getDiffculty()));

	        questionsArray.add(questionObject);
	    }

	    JsonObject root = new JsonObject();
	    root.add("questions", questionsArray);

	    // Write to file
	    try (Writer w = new FileWriter("src/QuestionsAndAnswers.json")) {
	        Gson gson = new GsonBuilder().setPrettyPrinting().create();
	        gson.toJson(root, w);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

}
