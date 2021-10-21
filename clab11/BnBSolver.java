import java.util.ArrayList;
import java.util.List;

/**
 * BnBSolver for the Bears and Beds problem. Each Bear can only be compared to Bed objects and each Bed
 * can only be compared to Bear objects. There is a one-to-one mapping between Bears and Beds, i.e.
 * each Bear has a unique size and has exactly one corresponding Bed with the same size.
 * Given a list of Bears and a list of Beds, create lists of the same Bears and Beds where the ith Bear is the same
 * size as the ith Bed.
 */
public class BnBSolver {
    private List<Bear> bears;
    private List<Bed> beds;

    public BnBSolver(List<Bear> bears, List<Bed> beds) {
        if (bears.size() != beds.size())
        	throw new IllegalArgumentException();
        var pair = quickSort(bears, beds);
        this.bears = pair.first();
        this.beds = pair.second();
    }

    private Pair<List<Bear>, List<Bed>> quickSort(List<Bear> bears, List<Bed> beds) {
		if (bears.size() <= 1) {
			return new Pair<>(bears, beds);	
		}
		
        Bed bearPivot = getRandomItem(beds);
        List<Bear> bearLess = new ArrayList<>();
        List<Bear> bearEqual = new ArrayList<>();
        List<Bear> bearGreater = new ArrayList<>();
		partition(bears, bearPivot, bearLess, bearEqual, bearGreater);

        Bear bedPivot = bearEqual.get(0);
        List<Bed> bedLess = new ArrayList<>();
        List<Bed> bedEqual = new ArrayList<>();
        List<Bed> bedGreater = new ArrayList<>();
		partition(beds, bedPivot, bedLess, bedEqual, bedGreater);

		var less = quickSort(bearLess, bedLess);
		var equal = new Pair<>(bearEqual, bedEqual);
		var greater = quickSort(bearGreater, bedGreater);

		var ans = catenate(less, equal);
		ans = catenate(ans, greater);
		return ans;
	}

	private Pair<List<Bear>, List<Bed>> catenate(Pair<List<Bear>, List<Bed>> p1,
												 Pair<List<Bear>, List<Bed>> p2) {
    	
    	List<Bear> bears = new ArrayList<>();
    	List<Bed> beds = new ArrayList<>();
		bears.addAll(p1.first());
		bears.addAll(p2.first());
		beds.addAll(p1.second());
		beds.addAll(p2.second());
    	return new Pair<>(bears, beds);
	}

	private <Item extends Comparable, E extends Comparable> void partition(
    		List<Item> unsorted, E pivot,
			List<Item> less, List<Item> equal, List<Item> greater) {
    	int cmp;
		for (Item item : unsorted) {
			cmp = pivot.compareTo(item);
			if (cmp < 0) {
				less.add(item);
			} else if (cmp > 0 ) {
				greater.add(item);
			} else {
				equal.add(item);
			}
		}
	}

    private <Item extends Comparable> Item getRandomItem(List<Item> items) {
        int pivotIndex = (int) (Math.random() * items.size());
        Item pivot = null;
        for (Item item : items) {
            if (pivotIndex == 0) {
                pivot = item;
                break;
            }
            pivotIndex--;
        }
        return pivot;
    }

    /**
     * Returns List of Bears such that the ith Bear is the same size as the ith Bed of solvedBeds().
     */
    public List<Bear> solvedBears() {
        return bears;
    }

    /**
     * Returns List of Beds such that the ith Bear is the same size as the ith Bear of solvedBears().
     */
    public List<Bed> solvedBeds() {
        return beds;
    }
}
