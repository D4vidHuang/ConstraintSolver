import org.w3c.dom.ls.LSInput;

import java.util.*;

public class StandardCombinatorics {
    /**
     * Returns a list of all binary strings of length n
     */
    public static List<String> getBinaryStrings(int n) {
        // Initialize lists for variables and constraints
        List<Solver.Variable> variables = new ArrayList<>();
        List<Solver.Constraint> constraints = new ArrayList<>();

        Integer[] domain = new Integer[]{0, 1};
        for (int i = 0; i < n; i++) {
            variables.add(new Solver.Variable(Arrays.asList(domain)));
        }
        // Convert to arrays
        Solver.Variable[] variablesArray = new Solver.Variable[variables.size()];
        variablesArray = variables.toArray(variablesArray);
        Solver.Constraint[] constraintsArray = new Solver.Constraint[constraints.size()];
        constraintsArray = constraints.toArray(constraintsArray);

        // Use solver
        Solver solver = new Solver(variablesArray, constraintsArray);
        int[] curSolution = new int[variablesArray.length];
        Arrays.fill(curSolution, -1);
        List<int[]> result = solver.findAllSolutions(n, -1, curSolution);
        ArrayList<String> res = new ArrayList<>();
        for (int[] arr : result) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < arr.length; i++) {
                sb.append(arr[i]);
            }
            res.add(sb.toString());
        }
//        System.out.println(res.size());
//        System.out.println(res);
        return res;
    }

    /**
     * Returns a list of all combinations of k elements from the set {1,...,n} without repetitions
     */
    public static List<int[]> getCombinationsWithoutRepetition(int n, int k) {
        // Initialize lists for variables and constraints
        List<Solver.Variable> variables = new ArrayList<>();
        List<Solver.Constraint> constraints = new ArrayList<>();

        // TODO: add your variables
        Integer[] domain = new Integer[n];
        for (int i = 0; i < n; i++) {
            domain[i] = i + 1;
        }
        for (int i = 0; i < k; i++) {
            variables.add(new Solver.Variable(Arrays.asList(domain)));
        }
        // TODO: add your constraints
        constraints.add(new Solver.WithOutRepConstraint());
        // Convert to arrays
        Solver.Variable[] variablesArray = new Solver.Variable[variables.size()];
        variablesArray = variables.toArray(variablesArray);
        Solver.Constraint[] constraintsArray = new Solver.Constraint[constraints.size()];
        constraintsArray = constraints.toArray(constraintsArray);

        // Use solver
        Solver solver = new Solver(variablesArray, constraintsArray);
        int[] curSolution = new int[variablesArray.length];
        Arrays.fill(curSolution, -1);
        List<int[]> result = solver.findAllSolutions(n, k, curSolution);
//        // Debug Print
//        for (int[] arr : result) {
//            StringBuilder sb = new StringBuilder();
//            sb.append("{ ");
//            for (int i = 0; i < arr.length; i++) {
//                sb.append(arr[i] + ", ");
//            }
//            sb.append("} ");
//            System.out.println(sb);
//        }
        return result;
    }

    /**
     * Returns a list of all combinations of k elements from the set {1,...,n} with repetitions
     */
    public static List<int[]> getCombinationsWithRepetition(int n, int k) {
        // Initialize lists for variables and constraints
        List<Solver.Variable> variables = new ArrayList<>();
        List<Solver.Constraint> constraints = new ArrayList<>();

        // variables
        Integer[] domain = new Integer[n];
        for (int i = 0; i < n; i++) {
            domain[i] = i + 1;
        }

        for (int i = 0; i < k; i++) {
            variables.add(new Solver.Variable(Arrays.asList(domain)));
        }
        // constraints
        constraints.add(new Solver.WithRepConstraint());

        // Convert to arrays
        Solver.Variable[] variablesArray = new Solver.Variable[variables.size()];
        variablesArray = variables.toArray(variablesArray);
        Solver.Constraint[] constraintsArray = new Solver.Constraint[constraints.size()];
        constraintsArray = constraints.toArray(constraintsArray);

        // Use solver
        Solver solver = new Solver(variablesArray, constraintsArray);
        int[] curSolution = new int[variablesArray.length];
        Arrays.fill(curSolution, -1);
        List<int[]> result = solver.findAllSolutions(n, k, curSolution);

        // TODO: use result to construct answer
        return result;
    }

    /**
     * Returns a list of all subsets in the set {1,...,n}
     */
    public static List<int[]> getSubsets(int n) {
        // Initialize lists for variables and constraints
        List<Solver.Variable> variables = new ArrayList<>();
        List<Solver.Constraint> constraints = new ArrayList<>();

        // variables
        Integer[] domain = new Integer[]{0, 1};
        for (int i = 0; i < n; i++) {
            variables.add(new Solver.Variable(Arrays.asList(domain)));
        }

        // Convert to arrays
        Solver.Variable[] variablesArray = new Solver.Variable[variables.size()];
        variablesArray = variables.toArray(variablesArray);
        Solver.Constraint[] constraintsArray = new Solver.Constraint[constraints.size()];
        constraintsArray = constraints.toArray(constraintsArray);

        // Use solver
        Solver solver = new Solver(variablesArray, constraintsArray);
        int[] curSolution = new int[variablesArray.length];
        Arrays.fill(curSolution, -1);
        List<int[]> result = solver.findAllSolutions(n, -1, curSolution);
        List<int[]> mapped = new ArrayList<>();
        for (int i = 0; i < result.size(); i++) {
            ArrayList<Integer> cur = new ArrayList<>();
            for (int j = 0; j < result.get(i).length; j++) {
                if (result.get(j)[j] != 0) cur.add(j+1);
            }
            int[] arr = new int[cur.size()];
            for (int j = 0; j < cur.size(); j++) {
                arr[j] = cur.get(j);
            }
            mapped.add(arr);
        }
        return mapped;
    }

    /**
     * Returns a list of all permutations in the set {1,...,n}
     */
    public static List<int[]> getSetPermutations(int n) {
        // Initialize lists for variables and constraints
        List<Solver.Variable> variables = new ArrayList<>();
        List<Solver.Constraint> constraints = new ArrayList<>();

        // variable domain
        Integer[] domain = new Integer[n];
        for (int i = 0; i < n; i++) {
            domain[i] = i + 1;
        }
        for (int i = 0; i < n; i++) {
            variables.add(new Solver.Variable(Arrays.asList(domain)));
        }
        // constraint
        constraints.add(new Solver.PermutationConstraint());

        // Convert to arrays
        Solver.Variable[] variablesArray = new Solver.Variable[variables.size()];
        variablesArray = variables.toArray(variablesArray);
        Solver.Constraint[] constraintsArray = new Solver.Constraint[constraints.size()];
        constraintsArray = constraints.toArray(constraintsArray);

        // Use solver
        Solver solver = new Solver(variablesArray, constraintsArray);
        int[] curSolution = new int[variablesArray.length];
        Arrays.fill(curSolution, -1);
        List<int[]> result = solver.findAllSolutions(n, -1, curSolution);


        return result;
    }
}
