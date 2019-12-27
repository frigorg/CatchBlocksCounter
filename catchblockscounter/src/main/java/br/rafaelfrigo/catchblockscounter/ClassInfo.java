package br.rafaelfrigo.catchblockscounter;

import java.util.Calendar;

public class ClassInfo {

	private Calendar commitDate;
	private String className;
	private String methodName;
	private int tryStatements;
	private int emptyCatchBlocks;
	private String exception;
	
	public Calendar getCommitDate() {
		return commitDate;
	}
	
	public int getTryStatements() {
		return tryStatements;
	}
	
	public int getEmptyCatchBlocks() {
		return emptyCatchBlocks;
	}

	public void setCommitDate(Calendar commitDate) {
		this.commitDate = commitDate;
	}

	public void setTryStatements(int tryStatements) {
		this.tryStatements = tryStatements;
	}

	public void setEmptyCatchBlocks(int emptyCatchBlocks) {
		this.emptyCatchBlocks = emptyCatchBlocks;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}
	
}
