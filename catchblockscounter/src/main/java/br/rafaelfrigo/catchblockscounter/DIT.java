package br.rafaelfrigo.catchblockscounter;

import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;

public class DIT {

	public int calculate(SingleVariableDeclaration binding) {
		
		if (binding != null) 
			return this.calculate(binding.resolveBinding());
		
		return 1;
	} 
	
	public int calculate(IVariableBinding binding) {
		
		if (binding != null) 
			return this.calculate(binding.getDeclaringClass());
		
		return 1;
	} 
	
	public int calculate(ITypeBinding binding) {
		
		if (binding != null) 
			return iterate(binding);
			
		return 1;
	} 
	
	private int iterate(ITypeBinding binding) {

		int dit = 1;
		
		ITypeBinding father = binding.getSuperclass();
		if (father != null) {
			String fatherName = father.getQualifiedName();
			while (!fatherName.endsWith("Object")) {
				dit++;
				father = father.getSuperclass();
				fatherName = father.getQualifiedName();
			}
		}
		return dit;
	}
	
}
