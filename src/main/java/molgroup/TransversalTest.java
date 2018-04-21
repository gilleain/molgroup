package molgroup;

import java.util.List;

import org.openscience.cdk.exception.InvalidSmilesException;
import org.openscience.cdk.graph.AtomContainerAtomPermutor;
import org.openscience.cdk.group.AtomContainerDiscretePartitionRefiner;
import org.openscience.cdk.group.PartitionRefinement;
import org.openscience.cdk.group.Permutation;
import org.openscience.cdk.group.PermutationGroup;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.silent.SilentChemObjectBuilder;
import org.openscience.cdk.smiles.SmilesParser;

public class TransversalTest {
	public static void main(String[] args) throws InvalidSmilesException {
//		 String smiles = "C1CCC1"; // 4-cycle
//		String smiles = "N1CCC1"; // 4-cycle
//		 String smiles = "C12C3C4C1C5C2C3C45";
		
//		String smiles = "C1CCCCC1";	// 6-cycle
		String smiles = "C1(C2)(C3)C23C1"; // propellane

		SmilesParser sp = new SmilesParser(SilentChemObjectBuilder.getInstance());
		IAtomContainer m = sp.parseSmiles(smiles);
		print(m);
		AtomContainerDiscretePartitionRefiner refiner = PartitionRefinement.forAtoms().create();
		// PermutationGroup rot = new PermutationGroup(8);

		PermutationGroup autM = refiner.getAutomorphismGroup(m);

		PermutationGroup symN = PermutationGroup.makeSymN(m.getAtomCount());

//		print("autM T in symN", m, symN.transversal(autM));
		
		// specific to C6
//		PermutationGroup fNorm = new PermutationGroup(6);
//		fNorm.enter(new Permutation(0, 5, 4, 3, 2, 1));	// f
//		fNorm.enter(new Permutation(3, 4, 5, 0, 1, 2));	// r^3
//		print("fnorm all", m, fNorm.all());
		
		// specific to propellane
		PermutationGroup rot = new PermutationGroup(5);
		rot.enter(new Permutation(0, 2, 4, 3, 1));	// r
		rot.enter(new Permutation(3, 4, 2, 0, 1));	// f
		print("rot", m, rot.all());

//		print("fnorm in autM", m, autM.transversal(fNorm));
		
		print("autM all", m, autM.all());
		
	}

	private static void print(String label, IAtomContainer mol, List<Permutation> plist) {
		System.out.println(label);
		AtomContainerAtomPermutor permutor = new AtomContainerAtomPermutor(mol);
		int i = 1;
		for (Permutation p : plist) {
			boolean id = p.isIdentity();
			IAtomContainer pmol = permutor.containerFromPermutation(p.getValues());
			System.out.println(i + "\t" + p.toCycleString() + "\t" + p + "\t" + print(mol) + (id ? "\tI" : ""));
			i++;
		}
		System.out.println();
	}

	private static String print(IAtomContainer m) {
		StringBuffer sb = new StringBuffer();
		int i = 0;
		for (IAtom atom : m.atoms()) {
			sb.append(atom.getSymbol() + "" + i);
			i++;
		}
		sb.append(" ");
		int j = m.getBondCount();
		for (IBond bond : m.bonds()) {
			int a0 = m.indexOf(bond.getBegin());
			int a1 = m.indexOf(bond.getEnd());
			if (a0 < a1) {
				sb.append(a0 + ":" + a1);
			} else {
				sb.append(a1 + ":" + a0);
			}
			if (j > 1) {
				sb.append(",");
			}
			j--;
		}
		return sb.toString();
	}
}
