package execution;
/**
 * Java files tokenizer
 * @author MWM
 */

import japa.*;
import japa.parser.JavaParser;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.*;
import japa.parser.ast.stmt.BlockStmt;
import japa.parser.ast.stmt.Statement;
import japa.parser.ast.visitor.VoidVisitorAdapter;

import java.io.FileInputStream;
import java.lang.Object.*;
import java.util.*;


public class Execution 
{
	public static void main(String[] args) throws Exception 
    {
        // Calls Extractor for a given java file
        Tokenizer e = new Tokenizer();
     // Calls Extractor for a given java file
        e.tokenizeClass("Comparator.java");
        e.tokenizeClass("Population.java");
        e.printTokenizerInfo();
        for(ClassTokenized c: e.getClasses()) {
        	for(MethodTokenized m: c.getMethods()) {
        		System.out.printf("Adding %s.%s\n", c.getName(), m.getName());
//        		s.addMethod(c.getName(), m.getName(), m.getBody());
        	}
        	}
        
    }


}
