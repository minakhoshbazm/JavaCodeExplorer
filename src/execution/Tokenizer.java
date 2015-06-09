package execution;
/**
 * Java files tokenizer
 * @author MWM
 */

import japa.*;
import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.Comment;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.*;
import japa.parser.ast.stmt.BlockStmt;
import japa.parser.ast.stmt.Statement;
import japa.parser.ast.visitor.VoidVisitorAdapter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.Object.*;
import java.util.*;


public class Tokenizer 
{
	ArrayList<ClassTokenized> classes ;
	
	public Tokenizer()
	{
		// initializes the arrayList for the classes
		classes = new ArrayList<ClassTokenized>();
	}
	
	public void tokenizeClass(String path) throws IOException, ParseException
	{
		// creates an input stream for the file to be parsed
        FileInputStream in = new FileInputStream(path);
        
        // prepare the parser
        CompilationUnit cu;
        try 
        {
            // parse the file
            cu = JavaParser.parse(in);
        } finally 
        {
            in.close();
        }

        // visit the source code for the file parsed
        List comments = cu.getComments();
        
        // if (comments != null) System.out.println("\n comments size : "+comments.toString()); else System.out.println("\n comments is null");
        
        // Extract all methods
        MethodVisitor mv = new MethodVisitor();
        new MethodVisitor().visit(cu, null);
        
        // Extract all classes
        ClassVisitor cv = new ClassVisitor();
        cv.visit(cu, null);
        
        // add all methods to the first (outer) class
        cv.addMethods(mv);
        
        // add all comments to the first (outer) class
        cv.addComments(comments);
        
        this.classes = cv.classes ;
        
	}
	
	
	void printTokenizerInfo()
	{
		System.out.println("\t *** Extractor Info ***");
		System.out.println(" # of classes : "+this.classes.size());
		for (int i=0 ; i < this.classes.size() ; i++)
		{
			this.classes.get(i).printClassInfo();
		}
	}

     
    // this class is for extracting all the info of one class and store it in one object
    private static class ClassVisitor extends VoidVisitorAdapter 
    {

    	static ArrayList<ClassTokenized> classes = new ArrayList<ClassTokenized>();
    	
    	static ArrayList<String> comments = new ArrayList<String>();
    	
        @Override
        public void visit(ClassOrInterfaceDeclaration n, Object arg) 
        {
            // temp class to save all the data related to one extracted class
        	ClassTokenized c = new ClassTokenized();
        	// temp variables to build the temp class
            c.name = new String(n.getName());
            c.body = new String(n.toString());
            List members = n.getMembers();
            List annotations = n.getAnnotations();
            List extendss = n.getExtends();
            List implementss = n.getImplements();
            List typeParameters = n.getTypeParameters();
            JavadocComment javaDoc = n.getJavaDoc();
            // data extraction
            if (members != null) 
            {
            	//System.out.println("\n members size : "+members.size());
            	c.setMembers(members);
            } 
            //else System.out.println("\n members is null");
            
            if (annotations != null) 
            {
            	//System.out.println("\n Annotations size : "+annotations.size());
            	c.setMembers(annotations);
            }
            //else System.out.println("\n annotations is null");
            
            if (extendss != null) 
            {
            	//System.out.println("\n extendss size : "+extendss.size());
            	c.setMembers(extendss);
            }
            //else System.out.println("\n extendss is null");
            
            if (implementss != null) 
            {
            	//System.out.println("\n implementss size : "+implementss.size());
            	c.setMembers(implementss);
            }
            //else System.out.println("\n implementss is null");
            
            if (typeParameters != null) 
            {
            	//System.out.println("\n typeParameters size : "+typeParameters.size());
            	c.setMembers(typeParameters);
            }
            //else System.out.println("\n typeParameters is null");
            
            c.setComments(this.comments); // retrieve comments
            
            c.setContent(); // put members and comments together
            
            this.classes.add(c); // add extracted class to the list of excracted classes
            
            
        }
        
        public void setComments(List comments)
        {
        	this.comments = (ArrayList<String>) comments;
        }
        
        public void addMethods(MethodVisitor mv)
        {
        	if (mv != null) 
            {
            	// This part is to be revised later
        		for (int i=0 ; i < mv.methods.size() ; i++)
        		{
        			classes.get(current_index).methods.add(mv.methods.get(i));
        		}
        		
            }
        	//else System.out.println("\n methodVisitor is null");
        }
        
        public void addComments(List<Comment> c)
        {
        	if (c != null) 
            {
            	// This part is to be revised later
        		for (int i=0 ; i < c.size() ; i++)
        		{
        			classes.get(current_index).comments.add(c.get(i).toString()) ;
        		}
        		
            }
        	//else System.out.println("\n comments is null");
        	current_index++;
        }
        
    }
    
    private static class MethodVisitor extends VoidVisitorAdapter 
    {

        static ArrayList<MethodTokenized> methods = new ArrayList<MethodTokenized>();
    	
    	@Override
        public void visit(MethodDeclaration n, Object arg) 
        {
    		MethodTokenized m = new MethodTokenized();
        	
    		m.name = new String(n.getName());
            m.body = new String(n.toString());
    		
            BlockStmt body = n.getBody();
            List<Statement> statements = new ArrayList<Statement>();
            statements = body.getStmts();
            JavadocComment javaDoc = n.getJavaDoc();
            
            if (statements != null) 
            {
            	for (int i=0 ; i < statements.size() ; i++)
        		{
        			m.members.add(statements.get(i).toString());
        		}
            }
            //else System.out.println("\n statements in methodVisitor is null");
            
            if (javaDoc != null) 
            {
            	m.comments.add(javaDoc.toString());
            }
            //else System.out.println("\n javaDoc in methodVisitor is null");
            
            m.setContent();
            
            methods.add(m);
        }
    }
    
    public static void main(String[] args) throws Exception 
    {
        // creates an input stream for the file to be parsed
        FileInputStream in = new FileInputStream("Population.java");

        CompilationUnit cu;
        try 
        {
            // parse the file
            cu = JavaParser.parse(in);
        } finally 
        {
            in.close();
        }

        // visit the source code for the file parsed
        List comments = cu.getComments();
        // if (comments != null) System.out.println("\n comments size : "+comments.toString()); else System.out.println("\n comments is null");
        
        // Extract all methods
        MethodVisitor mv = new MethodVisitor();
        new MethodVisitor().visit(cu, null);
        
        // Extract all classes
        ClassVisitor cv = new ClassVisitor();
        cv.visit(cu, null);
        
        // Extract all methods
        cv.addMethods(mv);
        
    }
    
    static int current_index = 0;

	public ArrayList<ClassTokenized> getClasses() {
		// TODO Auto-generated method stub
		return classes;
	}
    
}

