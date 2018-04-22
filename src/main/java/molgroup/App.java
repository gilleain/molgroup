package molgroup;

import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.exception.InvalidSmilesException;
import org.openscience.cdk.group.AtomContainerDiscretePartitionRefiner;
import org.openscience.cdk.group.PartitionRefinement;
import org.openscience.cdk.group.PermutationGroup;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.silent.SilentChemObjectBuilder;
import org.openscience.cdk.smiles.SmilesParser;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class App extends Application {
	
	private Label nText;	// n, the number of vertices in the mol
	private Label symNText; // the size of the sym group on n = n!
	private Label autText;	// the size of the automorphism group
	private BorderPane root;
	
	@Override
	public void start(Stage mainStage) throws Exception {
		Pane controlPanel = makeControlPane();
		Pane infoPane = makeInfoPane();
		
		root = new BorderPane();
		root.setTop(controlPanel);
		root.setLeft(infoPane);
		mainStage.setScene(new Scene(root, 1200, 800));
		mainStage.show();
	}
	
	private Pane makeInfoPane() {
		Label nLabel = new Label("|N|");
		nText = new Label("0");
		
		Label symNLabel = new Label("|Sym(N)|");
		symNText = new Label("?");
		
		Label autLabel = new Label("|Aut(M)|");
		autText = new Label("?");
		
		GridPane infoPane = new GridPane();
		infoPane.add(nLabel, 0, 0);
		infoPane.add(nText, 1, 0);
		
		infoPane.add(symNLabel, 0, 1);
		infoPane.add(symNText, 1, 1);
		
		infoPane.add(autLabel, 0, 2);
		infoPane.add(autText, 1, 2);
		
		infoPane.setHgap(10);
		infoPane.setMinWidth(50);
		infoPane.setStyle("-fx-padding: 10");
		return infoPane;
	}
	
	private void setSmiles(String smiles) {
		SmilesParser sp = new SmilesParser(SilentChemObjectBuilder.getInstance());
		try {
			IAtomContainer mol = sp.parseSmiles(smiles);
			int n = mol.getAtomCount();
			nText.setText(String.valueOf(n));
			symNText.setText(String.valueOf(factorial(n)));
			
			AtomContainerDiscretePartitionRefiner refiner = PartitionRefinement.forAtoms().create();
			PermutationGroup autGroup = refiner.getAutomorphismGroup(mol);
			autText.setText(String.valueOf(autGroup.getSize()));
			
			MoleculePane identityMol = new MoleculePane(300, 300);
			root.setCenter(identityMol.getPane(mol));
		} catch (InvalidSmilesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CDKException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private int factorial(int n) {
		if (n > 1) {
			return n * factorial(n - 1);
		} else {
			return 1;
		}
	}

	private Pane makeControlPane() {
		TextField smilesField = new TextField();
		Button button = new Button("Go");
		button.setOnAction(e -> setSmiles(smilesField.getText()));
		HBox controlPanel = new HBox(smilesField, button);
		controlPanel.setMinHeight(50);
		controlPanel.setStyle("-fx-padding: 10; -fx-spacing: 10");
		return controlPanel;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
