package br.rafaelfrigo.catchblockscounter;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class AstProcessor {

	private Map<String, ASTVisitor> visitorList = new HashMap<String, ASTVisitor>();
		
	void execute(String sourceCode) {
		CompilationUnit parse = this.parse(sourceCode);
		
		for (String key : visitorList.keySet()) {
			parse.accept(visitorList.get(key));
		}
	}
	
	/*
	 * Cria uma AST a partir de um código fonte
	 * 
	 *  @param sourceCode uma string contendo o código fonte de uma classe
	 */
	private CompilationUnit parse(String sourceCode) {
		
		ASTParser parser = ASTParser.newParser(AST.JLS11);
		
		parser.setResolveBindings(true);
		parser.setBindingsRecovery(true);
		
		Map<String, String> options = JavaCore.getOptions();
		JavaCore.setComplianceOptions(JavaCore.VERSION_11, options);
		parser.setCompilerOptions(options);
		
		parser.setSource(sourceCode.toCharArray());
		
		return (CompilationUnit) parser.createAST(null);
	}
		
	ASTVisitor getVisitor(String visitorName) {
		return visitorList.get(visitorName);
	}
	
	void addVisitor(String visitorName, ASTVisitor visitor) {
		visitorList.put(visitorName, visitor);
	}
	
}
