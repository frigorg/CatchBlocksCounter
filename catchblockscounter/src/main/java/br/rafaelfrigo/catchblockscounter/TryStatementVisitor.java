package br.rafaelfrigo.catchblockscounter;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.Block;

public class TryStatementVisitor extends ASTVisitor {

	private List<String> catchExceptions;
	private int quantity = 0;
	private int noCatchBlocks = 0;
	private int emptyCatchBlock = 0;
	
	public TryStatementVisitor() {
		this.catchExceptions = new ArrayList<String>();
	}
	
	@Override
	public boolean visit(TryStatement node) {
		
		this.quantity++;
		List<CatchClause> catchClauses = node.catchClauses();
		
		if (catchClauses.isEmpty()) {  
			this.noCatchBlocks++;
		}else {
			for (CatchClause clause : catchClauses) {

				DIT dit = new DIT();
				
				System.out.println(clause.getException().getType().toString());
				System.out.println(dit.calculate(clause.getException()));
				System.out.println("----------------");
				
				getCatchExceptions().add(clause.getException().getType().toString());
				Block body = clause.getBody();
				
				List<Block> statements = body.statements();
				
				if (statements.isEmpty()) emptyCatchBlock++;
			}
		}
		
		return true;
	}
	
	public int getQuantity() {
		return quantity;
	}

	public int getNoCatchBlocks() {
		return noCatchBlocks;
	}

	public int getEmptyCatchBlock() {
		return emptyCatchBlock;
	}

	public List<String> getCatchExceptions() {
		return catchExceptions;
	}
	
	
	
}
