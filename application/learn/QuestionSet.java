package application.learn;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents a set of questions. Stores questions based on the creations made in the VARpedia app
 * @author Lynette
 *
 */
public class QuestionSet {

	private List<Question> questions = new ArrayList<Question>();
	
	/**
	 * Creates a QuestionSet object. Upon creation, questions are created and added to the set
	 * if there are any existing quiz videos
	 */
	public QuestionSet() {
		// Check for any existing quiz videos
		List<String> qVideos = new ArrayList<String>();
		String command = "ls Quizzes/ | grep mp4$";
		
		ProcessBuilder pb = new ProcessBuilder("bash", "-c", command);
		try {
			Process question = pb.start();
			BufferedReader stdout = new BufferedReader(new InputStreamReader(question.getInputStream()));
			
			int exitStatus = question.waitFor();
			if (exitStatus == 0) {
				String line;
				while ((line = stdout.readLine()) != null) {
					qVideos.add(line);
				}
			}
			
			// Create questions using the quiz videos
			if (! qVideos.isEmpty()) {
				for (String q: qVideos) {
					Question term = new Question(new File("Quizzes/"+q), q.substring(0, q.length()-4));
					this.addQuestion(term);
				}
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Adds given question to the set
	 * @param question Question to be added
	 */
	public void addQuestion(Question question) {
		questions.add(question);
	}
	
	/**
	 * Returns a question randomly from the set
	 * @return random Question
	 */
	public Question getQuestion() {
		int index;
		if (questions.size() == 1) {
			index = 0;
		} else {
			Random rand = new Random();
			index = rand.nextInt(questions.size());
		}
		
		return questions.get(index);
	}
	
	/**
	 * Returns the size of the set
	 * @return set size (int)
	 */
	public int numberOfQuestions() {
		return questions.size();
	}
}
