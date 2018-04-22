package molgroup;

import java.awt.image.BufferedImage;

import org.openscience.cdk.depict.Depiction;
import org.openscience.cdk.depict.DepictionGenerator;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class MoleculePane {
	
	private DepictionGenerator dg;
	private int w;
	private int h;
	
	public MoleculePane(int w, int h) {
		this.dg = new DepictionGenerator();
		this.w = w;
		this.h = h;
	}
	
	public Pane getPane(IAtomContainer mol) throws CDKException {
		Depiction d = dg.withSize(w, h)
				.withFillToFit()
				.withMargin(5)
				.withAtomNumbers()
				.depict(mol);
		BufferedImage bufferedImage = d.toImg();
		Image image = SwingFXUtils.toFXImage(bufferedImage, null);
		BorderPane pane = new BorderPane();
		ImageView view = new ImageView(image);
		pane.setStyle("-fx-border-color: black; -fx-border-style: solid; -fx-border-width: 2px");
		pane.setCenter(view);
		return pane;
	}

}
