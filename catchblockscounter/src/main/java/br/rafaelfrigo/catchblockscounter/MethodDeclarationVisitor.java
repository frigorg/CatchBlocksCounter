package br.rafaelfrigo.catchblockscounter;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;

public class MethodDeclarationVisitor extends ASTVisitor {
	
	private int methodQuantity;
	
	@Override
	public boolean visit(MethodDeclaration node) {
		
		this.methodQuantity++;
		
		return true;
	}
	
	public int methodQuantity() {
		return methodQuantity;
	}
	
}
