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
import edu.stanford.nlp.trees.SemanticHeadFinder;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.util.CoreMap;

public class StandfordCoreNLPService {
	 private StanfordCoreNLP pipeline=null;
public void LoadService(String ... properties){
	Properties props = new Properties();
    props.put("annotators",properties);//"annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref"
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
     Tree t=tree;
     while(!t.isLeaf()){
    	t=headFinder.determineHead(t); 
     }
     return t.toString();
    }

}
