package br.rafaelfrigo.catchblockscounter;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;

public class MethodDeclarationVisitor extends ASTVisitor {
	
	private List<String> methodNameList;
	private List<TryStatementVisitor> tryList;

	public MethodDeclarationVisitor() {
		this.methodNameList = new ArrayList<String>();
		this.tryList = new ArrayList<TryStatementVisitor>();
	}
	
	@Override
	public boolean visit(MethodDeclaration node) {
		
		TryStatementVisitor trystatement = new TryStatementVisitor();
		
		node.accept(trystatement);
		
		if (trystatement.getQuantity() > 0) {
			getMethodNameList().add(node.getName().toString());
			getTryList().add(trystatement);
		}
		
		return true;
	}

	public void endVisit​(MethodDeclaration node) {
		
	}
	
	
	public List<String> getMethodNameList() {
		return methodNameList;
	}
	
	public List<TryStatementVisitor> getTryList() {
		return tryList;
	}	
}
