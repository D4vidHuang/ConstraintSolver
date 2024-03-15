import java.util.*;

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

        // Initialize variables with domains {0, 1} for each bit position
        for (int i = 0; i < n; i++) {
            variables.add(new Solver.Variable(Arrays.asList(0, 1)));
        }

        Solver.Variable[] variablesArray = variables.toArray(new Solver.Variable[0]);
        Solver.Constraint[] constraintsArray = new Solver.Constraint[0]; // No constraints for binary strings

        Solver solver = new Solver(variablesArray, constraintsArray, 0);
        List<int[]> result = solver.findAllSolutions();

        List<String> binaryStrings = new ArrayList<>();
        for (int[] solution : result) {
            StringBuilder sb = new StringBuilder();
            for (int value : solution) {
                sb.append(value); // Append each bit (0 or 1) to form the binary string
            }
            binaryStrings.add(sb.toString()); // Add the binary string to the list
        }

        return binaryStrings; // Returns a list of separate binary strings
    }


    /**
     * Returns a list of all combinations of k elements from the set {1,...,n} without repetitions
     */
    public static List<int[]> getCombinationsWithoutRepetition(int n, int k) {
        List<Solver.Variable> variables = new ArrayList<>();
        // 初始化变量，每个数字表示是否选择该数字（1代表选择，0代表不选择）
        for (int i = 1; i <= n; i++) {
            variables.add(new Solver.Variable(Arrays.asList(0, 1)));
        }

        // 添加一个约束确保恰好有k个1
        Solver.Constraint[] constraintsArray = {
                new Solver.ExactlyKConstraint(k)
        };

        // 使用solver寻找所有解决方案
        Solver solver = new Solver(variables.toArray(new Solver.Variable[0]), constraintsArray, k);
        List<int[]> binarySolutions = solver.findAllSolutions();

        // 将二进制解决方案转换为从1到n的组合
        List<int[]> combinations = new ArrayList<>();
        for (int[] binarySolution : binarySolutions) {
            int[] combination = new int[k];
            int index = 0;
            for (int i = 0; i < binarySolution.length; i++) {
                if (binarySolution[i] == 1) {
                    combination[index++] = i + 1; // 因为我们的变量是从1开始的
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
        // Initialize lists for variables and constraints
        List<Solver.Variable> variables = new ArrayList<>();
        List<Solver.Constraint> constraints = new ArrayList<>();

        // TODO: add your variables

        // TODO: add your constraints

        // Convert to arrays
        Solver.Variable[] variablesArray = new Solver.Variable[variables.size()];
        variablesArray = variables.toArray(variablesArray);
        Solver.Constraint[] constraintsArray = new Solver.Constraint[constraints.size()];
        constraintsArray = constraints.toArray(constraintsArray);

        // Use solver
        Solver solver = new Solver(variablesArray, constraintsArray, 0);
        List<int[]> result = solver.findAllSolutions();

        // TODO: use result to construct answer
        return new ArrayList<>();
    }

    /**
     * Returns a list of all subsets in the set {1,...,n}
     */
    public static List<int[]> getSubsets(int n) {
        // Initialize lists for variables and constraints
        List<Solver.Variable> variables = new ArrayList<>();
        List<Solver.Constraint> constraints = new ArrayList<>();

        // TODO: add your variables

        // TODO: add your constraints

        // Convert to arrays
        Solver.Variable[] variablesArray = new Solver.Variable[variables.size()];
        variablesArray = variables.toArray(variablesArray);
        Solver.Constraint[] constraintsArray = new Solver.Constraint[constraints.size()];
        constraintsArray = constraints.toArray(constraintsArray);

        // Use solver
        Solver solver = new Solver(variablesArray, constraintsArray, 0);
        List<int[]> result = solver.findAllSolutions();

        // TODO: use result to construct answer
        return new ArrayList<>();
    }

    /**
     * Returns a list of all permutations in the set {1,...,n}
     */
    public static List<int[]> getSetPermutations(int n) {
        // Initialize lists for variables and constraints
        List<Solver.Variable> variables = new ArrayList<>();
        List<Solver.Constraint> constraints = new ArrayList<>();

        // TODO: add your variables

        // TODO: add your constraints

        // Convert to arrays
        Solver.Variable[] variablesArray = new Solver.Variable[variables.size()];
        variablesArray = variables.toArray(variablesArray);
        Solver.Constraint[] constraintsArray = new Solver.Constraint[constraints.size()];
        constraintsArray = constraints.toArray(constraintsArray);

        // Use solver
        Solver solver = new Solver(variablesArray, constraintsArray, 0);
        List<int[]> result = solver.findAllSolutions();

        // TODO: use result to construct answer
        return new ArrayList<>();
    }
}
