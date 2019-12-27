package br.rafaelfrigo.catchblockscounter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.repodriller.domain.Commit;
import org.repodriller.domain.Modification;
import org.repodriller.persistence.PersistenceMechanism;
import org.repodriller.persistence.csv.CSVFile;
import org.repodriller.scm.CommitVisitor;
import org.repodriller.scm.SCMRepository;

public class DevelopersVisitor implements CommitVisitor {

	private String resultPath;
	private Map<String, ArrayList<ClassInfo>> classes;

	public DevelopersVisitor() {
		this.classes = new HashMap<String, ArrayList<ClassInfo>>();
	}
	
	// Processa cada commit no histórico de um repositório 
	public void process(SCMRepository repo, Commit commit, PersistenceMechanism writer) {
		
		// Itera sobre os arquivos tiveram modificações no commit
		for(Modification m : commit.getModifications()) {
			
			if (m.fileNameEndsWith(".java")) {

				AstProcessor processor = new AstProcessor();
				
				MethodDeclarationVisitor visitor = new MethodDeclarationVisitor();
				
				processor.addVisitor("MethodDeclarationVisitor", visitor);
				
				processor.execute(m.getSourceCode());
				
				if (visitor.getMethodNameList().size() > 0){
					
					List<String> MethodNameList = visitor.getMethodNameList();
					List<TryStatementVisitor> tryList = visitor.getTryList();
					
					for (int i = 0; i < visitor.getMethodNameList().size(); i++) {
					
						for (String exception : tryList.get(i).getCatchExceptions()) { 
						
						
							ClassInfo info = new ClassInfo();
							
							info.setClassName(m.getFileName());
							info.setMethodName(MethodNameList.get(i));
							info.setCommitDate(commit.getDate());
							info.setTryStatements(tryList.get(i).getQuantity());
							info.setEmptyCatchBlocks(tryList.get(i).getEmptyCatchBlock());
							info.setException(exception);
							
							if (!this.classes.containsKey(commit.getHash())) {
								ArrayList<ClassInfo> infoList = new ArrayList<ClassInfo>();
		
								infoList.add(info);
			
								this.classes.put(commit.getHash(), infoList);
							} else {
								classes.get(commit.getHash()).add(info);
							}
						
						}
						
						
						
					}
				}
			}	
		}
	}	
	
	@Override
	public void finalize(SCMRepository repo, PersistenceMechanism writer) {
		String[] splittedOrigin = repo.getOrigin().split("/");
		
		this.resultPath = splittedOrigin[splittedOrigin.length-2];
		this.resultPath += "/" + splittedOrigin[splittedOrigin.length-1] + "/";
		
		createDir(this.resultPath);
	}
	
	/*
	 * Escreve o resultado da análise em um arquivo CSV.
	 * Cada commit é ordenado de forma crescente em relação a sua data
	 * 
	 * @param path camimnho completo com o nome do arquivo relatório
	 */
	void saveResultInOneFile() {
		CSVFile file = new CSVFile(this.resultPath + "result.csv");
		
		file.write("commithash", "commitdate", "classname", "methodname", "trystatements", "emptycatchblocks", "exception");
		
		classes.forEach((key, value) -> {
			value.forEach((info)->{
				String date = "" + info.getCommitDate().getTime();
				file.write(key, date, info.getClassName(), info.getMethodName(), info.getTryStatements(), info.getEmptyCatchBlocks(), info.getException());
			});
		});
	}
	
	void saveResultByClass() {

		Set<String> writtenMethods = new HashSet<String>();
		
		classes.forEach((key, value) -> {
			value.forEach((info)->{
				
				CSVFile file;
				
				String className = info.getClassName().split(".java")[0];
				className = className + ".csv";
				
				if (writtenMethods.add(className)) {
					file = new CSVFile(this.resultPath + className);
					file.write("commithash", "commitdate", "classname", "methodname", "trystatements", "emptycatchblocks", "exception");
				}else{
					file = new CSVFile(this.resultPath + className, true);
				}
				
				String date = "" + info.getCommitDate().getTime();
				file.write(key, date, info.getClassName(), info.getMethodName(), info.getTryStatements(), info.getEmptyCatchBlocks(), info.getException());
				
			});
		});
		
	}
	
	void createDir(String path) {

        Path p = Paths.get(path);
        if (!Files.exists(p)) {
            try {
                Files.createDirectories(p);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}
	
}