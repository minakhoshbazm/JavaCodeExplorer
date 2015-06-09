package execution;
/**
 * Java files tokenizer
 * @author MWM
 */

import japa.*;
import japa.parser.JavaParser;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.*;
import japa.parser.ast.visitor.VoidVisitorAdapter;

import java.io.FileInputStream;
import java.lang.Object.*;
import java.util.*;


public class MethodTokenized 
{
	String name;
	String body;
	
	ArrayList<String> members;
	ArrayList<String> comments;
	ArrayList<String> content;
	
	
	public MethodTokenized()
	{
		
		members = new ArrayList<String>();
		comments = new ArrayList<String>();
		content = new ArrayList<String>();		
	}
	
	public void setMembers(List members)
	{
		for (int i=0 ; i < members.size() ; i++)
		{
			this.members.add(members.get(i).toString());
		}
	}
	
	public void setComments(List comments)
	{
		for (int i=0 ; i < comments.size() ; i++)
		{
			this.comments.add(comments.get(i).toString());
		}
	}
	
	public void setContent()
	{
		for (int i=0 ; i < comments.size() ; i++)
		{
			this.content.add(comments.get(i));
		}
		
		for (int i=0 ; i < members.size() ; i++)
		{
			this.content.add(members.get(i));
		}
		
	}
	
	public String getBody() {
		return body;
	}
	
	
public String getName() {
	return name;
}
}
