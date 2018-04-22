package molgroup;

import java.util.ArrayList;
import java.util.List;

import org.openscience.cdk.graph.AtomContainerAtomPermutor;
import org.openscience.cdk.group.Permutation;
import org.openscience.cdk.group.PermutationGroup;
import org.openscience.cdk.interfaces.IAtomContainer;

public class MoleculeGroup {
	
	private IAtomContainer identityMol;
	
	private PermutationGroup permutationGroup;
	
	private List<Permutation> permutations;
	
	public MoleculeGroup(IAtomContainer mol) {
		this(mol, PermutationGroup.makeSymN(mol.getAtomCount()));
	}
	
	public MoleculeGroup(IAtomContainer mol, PermutationGroup permutationGroup) {
		this.identityMol = mol;
		this.permutationGroup = permutationGroup;
	}
	
	public MoleculeGroup(IAtomContainer mol, List<Permutation> permutations) {
		this.identityMol = mol;
		this.permutations = permutations;
	}
	
	public List<IAtomContainer> getMolecules() {
		List<IAtomContainer> mols = new ArrayList<>();
		if (permutationGroup == null) {
			apply(mols, permutations);
		} else {
			apply(mols, permutationGroup.all());
		}
		return mols;
	}
	
	private void apply(List<IAtomContainer> mols, List<Permutation> permutations) {
		AtomContainerAtomPermutor permutor = new AtomContainerAtomPermutor(identityMol);
		int counter = 0;
		for (Permutation p : permutations) {
			IAtomContainer pmol = permutor.containerFromPermutation(p.getValues());
			pmol.setID(String.valueOf(counter));
			mols.add(pmol);
			counter++;
		}
	}

}
