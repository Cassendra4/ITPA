package uom.cse.itpa.nlppb.core;

import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.trees.HeadFinder;
import edu.stanford.nlp.trees.SemanticHeadFinder;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.trees.tregex.TregexMatcher;
import edu.stanford.nlp.trees.tregex.TregexPattern;
import edu.stanford.nlp.util.CoreMap;

public class StandfordCoreNLPService {
	 private StanfordCoreNLP pipeline=null;
	 private TregexPattern tPattern = TregexPattern.compile("SBARQ");
	 private TregexPattern nounPrasetPattern = TregexPattern.compile("NP");
	 private TregexPattern SQPrasePattern = TregexPattern.compile("SQ");
	 private TregexPattern pPTregexPattern=TregexPattern.compile("PP");
	 private TregexPattern specialPattern1=TregexPattern.compile("PP << NP");
	 private TregexPattern specialPattern2=TregexPattern.compile("PP << IN");
public void LoadService(Properties props){
	//"annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref"
    pipeline = new StanfordCoreNLP(props);
}

public String getHeadWord(String sentence){
	Annotation document = new Annotation(sentence);
    pipeline.annotate(document);
    // these are all the sentences in this document
    // a CoreMap is essentially a Map that uses class objects as keys and has values with custom types
    List<CoreMap> sentences = document.get(SentencesAnnotation.class);
   CoreMap question=sentences.get(0);
      // traversing the words in the current sentence
      // a CoreLabel is a CoreMap with additional token-specific methods
      SemanticHeadFinder headFinder=new SemanticHeadFinder();
      // this is the parse tree of the current sentence
     Tree tree = question.get(TreeAnnotation.class);
    System.out.println(tree);
     return findHeadWord(tree, headFinder);
    }
public String findHeadWord(Tree tree,SemanticHeadFinder headFinder){
	 TregexMatcher tMatcher = tPattern.matcher(tree);
	 Tree t=tree;
     if(tMatcher.find()) {
    	TregexMatcher nounPraseMatcher=nounPrasetPattern.matcher(tMatcher.getMatch());
    	 if(nounPraseMatcher.find()){
    		 iterateHeadWord(nounPraseMatcher.getMatch(), headFinder);
    	 }
    	 else{
    		 findHeadWord(tPattern.matcher(tree).getMatch(), headFinder);
    	 }
     }
     else if(SQPrasePattern.matcher(tree).find()){
    	 TregexMatcher nounPraseMatcher=nounPrasetPattern.matcher(SQPrasePattern.matcher(tree).getMatch());
    	 if(nounPraseMatcher.find()){
    		 return iterateHeadWord(nounPraseMatcher.getMatch(), headFinder);
    	 }
    	 else{
    		 findHeadWord(SQPrasePattern.matcher(tree).getMatch(), headFinder);
    	 }
     }
     else if(specialPattern1.matcher(tree.getChild(1)).find()){
    	 System.out.println(tree.getChild(1).getChild(1));
    	 Tree t2=tree.getChild(1).getChild(1);
    	 return iterateHeadWord(t2,headFinder);
     }
     else{
	
    while(!t.isLeaf()){
   	t=headFinder.determineHead(t); 
   	System.out.println(t);
    }
     }
    return t.toString();
	
}
public String iterateHeadWord(Tree t2,HeadFinder headFinder){
	
	
	 while(!t2.isLeaf()){
		   	t2=headFinder.determineHead(t2); 
		   	System.out.println(t2);
		    }
		    return t2.toString();
}
}
