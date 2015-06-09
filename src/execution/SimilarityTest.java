package execution;

public class SimilarityTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
//tokenize the classes
			//Similarity s = new Similarity();
Tokenizer t = new Tokenizer();
for(String arg: args) { 
	t = new Tokenizer();
	t.tokenizeClass("Population.java");
//load the classes into the Lucene index
for(ClassTokenized c: t.getClasses()) {
	for(MethodTokenized m: c.getMethods()) {
		System.out.printf("Adding %s.%s\n", c.getName(), m.getName());
//		s.addMethod(c.getName(), m.getName(), m.getBody());
	}
	}
}
//test each method against each class
//results are printed in CSV format
//System.out.println("C1, C2, M, Score");
/*
for(ClassTokenized c1: t.getClasses()) {
	for(MethodTokenized m: c1.getMethods()) {
		boolean found = false;
	for(ClassTokenized c2: t.getClasses()) {
		if(c1 == c2) {
			found = true;
			continue;
		}
		if(!found)
			continue;
		double score = s.compareSimilarity(c1.getName(), c2.getName(), m.getName());
//		System.out.printf("%s,%s,%s,%g\n", c1.getName(), c2.getName(), m.getName(), score);
	}
	}
}
*/
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
