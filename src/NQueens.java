import java.util.*;

public class NQueens {
    /**
     * Returns the number of N-Queen solutions
     */
    public static int getNQueenSolutions(int n) {
        // Initialize lists for variables and constraints
        List<Solver.Variable> variables = new ArrayList<>();
        List<Solver.Constraint> constraints = new ArrayList<>();

        // Variable
        Integer[] domain = new Integer[n];
        for (int i = 0; i < n; i++) {
            domain[i] = i + 1;
        }
        for (int i = 0; i < n; i++) {
          variables.add(new Solver.Variable(Arrays.asList(domain)));
        }
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
        return -1;
    }
}
