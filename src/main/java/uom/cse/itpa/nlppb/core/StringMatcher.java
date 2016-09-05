package uom.cse.itpa.nlppb.core;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringMatcher {

    Pattern allCapsPattern = Pattern.compile ("[A-Z]+");
    Pattern allLowerCasePattern = Pattern.compile ("[a-z]+");
    Pattern allDigitsPattern=Pattern.compile("\\d+");
    Pattern allLettersPattern=Pattern.compile("[A-Za-z ]");
public String MatchStringList(List<String> words,String sentence){
	for(String word:words){
		if(sentence.contains(word)){
			return word;
		}
	}
	return null;
}

public boolean AllUpperCase(String sentence){
	String question=sentence.substring(0, 1).toLowerCase() + sentence.substring(1);
    Matcher allCapsMatcher = allCapsPattern.matcher(question);
    return allCapsMatcher.find();

}

public boolean AllLoweCase(String sentence){
	String question=sentence.substring(0, 1).toLowerCase() + sentence.substring(1);
    Matcher allCLowerMatcher = allLowerCasePattern.matcher(question);
    return allCLowerMatcher.find();

}

public boolean AllDigits(String sentence){
	String question=sentence.substring(0, 1).toLowerCase() + sentence.substring(1);
    Matcher allDigitsMatcher = allDigitsPattern.matcher(question);
    return allDigitsMatcher.find();

}
public boolean AllLetters(String sentence){
	String question=sentence.substring(0, 1).toLowerCase() + sentence.substring(1);
    Matcher allLettersMatcher = allLettersPattern.matcher(question);
    return allLettersMatcher.find();

}



}
