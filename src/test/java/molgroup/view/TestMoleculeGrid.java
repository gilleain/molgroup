package molgroup.view;

import java.util.List;

import org.openscience.cdk.group.AtomContainerDiscretePartitionRefiner;
import org.openscience.cdk.group.PartitionRefinement;
import org.openscience.cdk.group.PermutationGroup;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.silent.SilentChemObjectBuilder;
import org.openscience.cdk.smiles.SmilesParser;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import molgroup.MoleculeGrid;
import molgroup.MoleculeGroup;

public class TestMoleculeGrid extends Application {

	public MoleculeGrid grid;

	public List<IAtomContainer> mols;

	@Override
	public void start(Stage mainStage) throws Exception {
		int panelW = 800;
		int panelH = 800;
		String smiles = "C1CCCC1";
		SmilesParser sp = new SmilesParser(SilentChemObjectBuilder.getInstance());
		IAtomContainer m = sp.parseSmiles(smiles);

		PermutationGroup symN = PermutationGroup.makeSymN(m.getAtomCount());
		AtomContainerDiscretePartitionRefiner refiner = PartitionRefinement.forAtoms().create();
		PermutationGroup autM = refiner.getAutomorphismGroup(m);
		
		MoleculeGroup molGroup = new MoleculeGroup(m, symN.transversal(autM));
		MoleculeGrid grid = new MoleculeGrid(panelW, panelH, 4);

		mainStage.setScene(new Scene(grid.render(molGroup.getMolecules()), panelW, panelH));
		mainStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
