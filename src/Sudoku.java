import java.util.*;

public class Sudoku {
    /**
     * Returns the filled in sudoku grid.
     *
     * @param grid the partially filled in grid. unfilled positions are -1.
     * @return the fully filled sudoku grid.
     */
    public static int[][] solve(int[][] grid) {
        // Initialize lists for variables and constraints
        List<Solver.Variable> variables = new ArrayList<>();
        List<Solver.Constraint> constraints = new ArrayList<>();
        // variables
        int[] curSolution = new int[grid.length * grid[0].length];
        Integer[] domain = new Integer[grid.length];
        for (int i = 0; i < grid.length; i++) {
            domain[i] = i + 1;
        }
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == -1) {
                    variables.add(new Solver.Variable(Arrays.asList(domain)));
                    curSolution[i * grid[0].length + j] = -1;
                } else {
                    variables.add(new Solver.Variable(Arrays.asList(new Integer[] {grid[i][j]})));
                    curSolution[i * grid[0].length + j] = grid[i][j];
                }
            }
        }
        // constraints
        constraints.add(new Solver.RowConstraint());
        constraints.add(new Solver.ColumnConstraint());
        constraints.add(new Solver.CellConstraint());
        // Convert to arrays
        Solver.Variable[] variablesArray = new Solver.Variable[variables.size()];
        variablesArray = variables.toArray(variablesArray);
        Solver.Constraint[] constraintsArray = new Solver.Constraint[constraints.size()];
        constraintsArray = constraints.toArray(constraintsArray);

        // Use solver
//        Solver solver = new Solver(variablesArray, constraintsArray, 0);
        Solver solver = new Solver(variablesArray, constraintsArray);

        // Because the sudoku has non-fixed size, pass the size of the grid as the first argument
        int[] result = solver.findOneSolution(grid.length,-1, curSolution);
        StringBuilder sb = new StringBuilder();
        if (result == null) {
            return null;
        }
        for (int i = 0; i < result.length; i++) {
            grid[i / grid.length][i % grid.length] = result[i];
        }
        sb.append("\nSolved Sudoku:\n");
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                sb.append(grid[i][j]);
                sb.append(" ");
            }
            sb.append("\n");
        }
        System.out.println(sb);
        return grid;
    }
}
