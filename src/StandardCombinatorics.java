import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StandardCombinatorics {
    /**
     * Returns a list of all binary strings of length n
     */
//    public static List<String> getBinaryStrings(int n) {
//        // Initialize lists for variables and constraints
//        List<Solver.Variable> variables = new ArrayList<>();
//        List<Solver.Constraint> constraints = new ArrayList<>();
//
//        // TODO: add your variables
//
//        // TODO: add your constraints
//
//        // Convert to arrays
//        Solver.Variable[] variablesArray = new Solver.Variable[variables.size()];
//        variablesArray = variables.toArray(variablesArray);
//        Solver.Constraint[] constraintsArray = new Solver.Constraint[constraints.size()];
//        constraintsArray = constraints.toArray(constraintsArray);
//
//        // Use solver
//        Solver solver = new Solver(variablesArray, constraintsArray);
//        List<int[]> result = solver.findAllSolutions();
//
//        // TODO: use result to construct answer
//        return new ArrayList<>();
//    }

    public static List<String> getBinaryStrings(int n) {
        List<Solver.Variable> variables = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            variables.add(new Solver.Variable(Arrays.asList(0, 1)));
        }

        Solver.Variable[] variablesArray = variables.toArray(new Solver.Variable[0]);
        Solver.Constraint[] constraintsArray = new Solver.Constraint[0];

        Solver solver = new Solver(variablesArray, constraintsArray, 0);
        List<int[]> result = solver.findAllSolutions();

        List<String> binaryStrings = new ArrayList<>();
        for (int[] solution : result) {
            StringBuilder sb = new StringBuilder();
            for (int value : solution) {
                sb.append(value);
            }
            binaryStrings.add(sb.toString());
        }

        return binaryStrings;
    }


    /**
     * Returns a list of all combinations of k elements from the set {1,...,n} without repetitions
     */
    public static List<int[]> getCombinationsWithoutRepetition(int n, int k) {
        List<Solver.Variable> variables = new ArrayList<>();
        //每个数字表示是否选择该数字1代表选择，0代表不选择
        for (int i = 1; i <= n; i++) {
            variables.add(new Solver.Variable(Arrays.asList(0, 1)));
        }

        //确保恰好有k个1
        Solver.Constraint[] constraintsArray = {
                new Solver.ExactlyKConstraint(k)
        };

        Solver solver = new Solver(variables.toArray(new Solver.Variable[0]), constraintsArray, k);
        List<int[]> binarySolutions = solver.findAllSolutions();

        List<int[]> combinations = new ArrayList<>();
        for (int[] binarySolution : binarySolutions) {
            int[] combination = new int[k];
            int index = 0;
            for (int i = 0; i < binarySolution.length; i++) {
                if (binarySolution[i] == 1) {
                    combination[index++] = i + 1;
                }
            }
            combinations.add(combination);
        }

        return combinations;
    }



    /**
     * Returns a list of all combinations of k elements from the set {1,...,n} with repetitions
     */
    public static List<int[]> getCombinationsWithRepetition(int n, int k) {
        List<Solver.Variable> variables = new ArrayList<>();

        for (int i = 0; i < k; i++) {
            List<Integer> domain = new ArrayList<>();
            for (int j = 1; j <= n; j++) {
                domain.add(j);
            }
            variables.add(new Solver.Variable(domain));
        }

        Solver.Constraint nonDecreasingConstraint = new Solver.NonDecreasingConstraint();
        Solver.Constraint[] constraintsArray = {nonDecreasingConstraint};

        Solver solver = new Solver(variables.toArray(new Solver.Variable[0]), constraintsArray, 0);
        List<int[]> result = solver.findAllSolutions();

        return result;
    }




    /**
     * Returns a list of all subsets in the set {1,...,n}
     */
    public static List<int[]> getSubsets(int n) {
        List<Solver.Variable> variables = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            variables.add(new Solver.Variable(Arrays.asList(0, 1)));
        }

        Solver.Constraint[] constraintsArray = new Solver.Constraint[0];

        Solver solver = new Solver(variables.toArray(new Solver.Variable[0]), constraintsArray, 0);
        List<int[]> rawResults = solver.findAllSolutions();

        List<int[]> subsets = new ArrayList<>();
        for (int[] solution : rawResults) {
            List<Integer> subset = new ArrayList<>();
            for (int i = 0; i < solution.length; i++) {
                if (solution[i] == 1) {
                    subset.add(i + 1);
                }
            }

            int[] subsetArray = subset.stream().mapToInt(Integer::intValue).toArray();
            subsets.add(subsetArray);
        }

        return subsets;
    }


    /**
     * Returns a list of all permutations in the set {1,...,n}
     */
    public static List<int[]> getSetPermutations(int n) {
        List<Solver.Variable> variables = new ArrayList<>();

        for (int i = 1; i <= n; i++) {
            List<Integer> domain = IntStream.rangeClosed(1, n).boxed().collect(Collectors.toList());
            variables.add(new Solver.Variable(domain));
        }

        Solver.Constraint uniqueConstraint = new Solver.UniqueConstraint();
        Solver.Constraint[] constraintsArray = {uniqueConstraint};

        Solver solver = new Solver(variables.toArray(new Solver.Variable[0]), constraintsArray, 0);
        List<int[]> rawResults = solver.findAllSolutions();

        return rawResults;
    }


}
