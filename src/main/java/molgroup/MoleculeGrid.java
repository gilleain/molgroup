package molgroup;

import java.awt.image.BufferedImage;
import java.util.List;

import org.openscience.cdk.depict.Depiction;
import org.openscience.cdk.depict.DepictionGenerator;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.smsd.labelling.AtomContainerPrinter;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class MoleculeGrid {
	
	private DepictionGenerator dg;
	private GridPane gr;
	private int w;
	private int h;
	private int dim;
	
	public MoleculeGrid(int w, int h, int dim) {
		this.dg = new DepictionGenerator();
		this.w = w;
		this.h= h;
		this.dim = dim;
	}
	
	public Pane render(List<IAtomContainer> mols) throws CDKException {
		gr = new GridPane();
		int counter = 0;
		int row = 0;
		int col = 0;
		int cellW = w / dim;
		int cellH = h / dim;
		AtomContainerPrinter printer = new AtomContainerPrinter();
		for (IAtomContainer ac : mols) {
			Depiction d = dg.withSize(cellW, cellH)
							.withFillToFit()
							.withMargin(5)
							.withAtomNumbers()
							.depict(ac);
			
			BufferedImage bufferedImage = d.toImg();
			Image image = SwingFXUtils.toFXImage(bufferedImage, null);
			BorderPane pane = new BorderPane();
			ImageView view = new ImageView(image);
			pane.setStyle("-fx-border-color: black; -fx-border-style: solid; -fx-border-width: 2px");
			pane.setCenter(view);
			System.out.println("At " + col + "," + row + " Adding " + ac.getID());
			gr.add(pane, col, row);
			counter++;
			if (counter % dim == 0) {
				row++;
				col = 0;
			} else {
				col++;
			}
		}
		return gr;
	}

}
