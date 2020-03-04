package br.rafaelfrigo.catchblockscounter;

import java.util.Calendar;

public class ClassInfo {

	private Calendar commitDate;
	private String className;
	private int methodsQuantity;
	
	public Calendar getCommitDate() {
		return commitDate;
	}
	
	public void setCommitDate(Calendar commitDate) {
		this.commitDate = commitDate;
	}
	
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public int getMethodsQuantity() {
		return methodsQuantity;
	}

	public void setMethodsQuantity(int methodsQuantity) {
		this.methodsQuantity = methodsQuantity;
	}

	
	
}
