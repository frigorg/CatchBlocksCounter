package br.rafaelfrigo.catchblockscounter;

import java.util.Arrays;

import org.repodriller.RepoDriller;
import org.repodriller.RepositoryMining;
import org.repodriller.Study;
import org.repodriller.filter.commit.OnlyModificationsWithFileTypes;
import org.repodriller.filter.diff.OnlyDiffsWithFileTypes;
import org.repodriller.filter.range.Commits;
import org.repodriller.persistence.csv.CSVFile;
import org.repodriller.scm.CollectConfiguration;
import org.repodriller.scm.GitRemoteRepository;
import org.repodriller.scm.GitRepository;

public class CatchBlocksCounter implements Study {
	
	private String repositoryURL;
	
	public CatchBlocksCounter(String repositoryURL) {
		this.repositoryURL = repositoryURL;
	}
	
	public static void main(String[] args) {
		
		if (args.length < 1) {
			System.out.println("CatchBlocksCounter needs a git remote repository url.");
			System.exit(1);
		}
		
		new RepoDriller().start(new CatchBlocksCounter(args[0]));
	}

	public void execute() {
		
		DevelopersVisitor visitor = new DevelopersVisitor();
		
		new RepositoryMining()
			.in(GitRemoteRepository.singleProject(this.repositoryURL))
			.through(Commits.all())
			.process(visitor)
			.filters(
					new OnlyModificationsWithFileTypes(Arrays.asList(".java"))
				)
			.mine();
		
//		visitor.saveResultInOneFile();
		visitor.saveResultByClass();
	}

}
