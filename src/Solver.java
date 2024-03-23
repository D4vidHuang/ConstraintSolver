import java.sql.SQLOutput;
import java.util.*;

class Solver {
    static class Variable {
        List<Integer> domain;
        // you can add more attributes

        /**
         * Constructs a new variable.
         * @param domain A list of values that the variable can take
         */
        public Variable(List<Integer> domain) {
            this.domain = domain;
        }
    }

    static abstract class Constraint {
        /**
         * Tries to reduce the domain of the variables associated to this constraint, using inference
         */
        abstract void infer(int[] currentSolution, int index, int n, Variable var);
    }

    static class NQueenConstraint extends Constraint{
        // current solution [q1, q2 ... qn]
        // No need to check column since we have n-size array for n column
        // Constraint qi = qj only if i != j to ensure no 2 queen are at same row
        // Constraint |i - j| != |qi - qj| to ensure no 2 queen on same diagonal
        @Override
        void infer(int[] currentSolution, int index, int n, Variable var) {
            HashSet<Integer> occ = new HashSet<>();

            for (int i = 0; i < index; i++) {
                // Row constraint
                occ.add(currentSolution[i]);
                // Diagonal constraint
                int diff = Math.abs(i - index);
                int d1 = currentSolution[i] + diff;
                int d2 = currentSolution[i] - diff;
                if (d1 >= 0 && d1 < n) occ.add(d1);
                if (d2 >= 0 && d2 < n) occ.add(d2);
            }

            ArrayList<Integer> newDomain = new ArrayList<>();

            for (int i = 0; i < n; i++) {
                if (occ.contains(i)) continue;
                newDomain.add(i);
            }

            var.domain = newDomain;
        }
    }

//    static class SubSetConstraint extends Constraint {
//
//        @Override
//        void infer(int[] currentSolution, int index, int n, Variable var) {
//
//        }
//    }

    static class WithOutRepConstraint extends Constraint {

        @Override
        void infer(int[] currentSolution, int index, int n, Variable var) {
            HashSet<Integer> occ = new HashSet<>();
            int max = -1;
            for (int i = 0; i < index; i++) {
                if (currentSolution[i] > max) max = currentSolution[i];
                occ.add(currentSolution[i]);
            }
            ArrayList<Integer> newDomain = new ArrayList<>();
            for (int i = 1; i <= n; i++) {
                if (occ.contains(i) || i <= max) continue;
                newDomain.add(i);
            }
            var.domain = newDomain;
        }
    }

    static class WithRepConstraint extends Constraint {

        @Override
        void infer(int[] currentSolution, int index, int n, Variable var) {
            int max = -1;
            for (int i = 0; i < index; i++) {
                if (currentSolution[i] > max) max = currentSolution[i];
            }
            ArrayList<Integer> newDomain = new ArrayList<>();
            for (int i = 1; i <= n; i++) {
                if (i < max) continue;
                newDomain.add(i);
            }
            var.domain = newDomain;
        }
    }

    static class PermutationConstraint extends Constraint {

        @Override
        void infer(int[] currentSolution, int index, int n, Variable var) {
            HashSet<Integer> occ = new HashSet<>();
            for (int i = 0; i < index; i++) {
               occ.add(currentSolution[i]);
            }
            ArrayList<Integer> newDomain = new ArrayList<>();
            for (int i = 1; i <= n; i++) {
                if (occ.contains(i)) continue;
                newDomain.add(i);
            }
            var.domain = newDomain;
        }
    }

    static class RowConstraint extends Constraint {
        @Override
        void infer(int[] currentSolution, int index, int n, Variable var) {
//            HashSet<Integer> occ = new HashSet<>();
            int[] cnt = new int[n+1];
            int rowStartIndex = index / n * n;
            for (int i = 0; i < n; i++) {
                int curIndex = rowStartIndex + i;
                if (currentSolution[curIndex] >= 1) cnt[currentSolution[curIndex]]++;
            }
            ArrayList<Integer> newDomain = new ArrayList<>();
            for (int i = 1; i <= n; i++) {
                if (cnt[i] >= 1) continue;
//                if (occ.contains(i)) continue;
                newDomain.add(i);
            }
            var.domain = newDomain;
        }
    }

    static class ColumnConstraint extends Constraint {
        @Override
        void infer(int[] currentSolution, int index, int n, Variable var) {
            HashSet<Integer> occ = new HashSet<>();
            int columnStartIndex = index % n;
            for (int i = 0; columnStartIndex + i < currentSolution.length; i += n) {
                int curIndex = columnStartIndex + i;
                if (currentSolution[curIndex] >= 1) occ.add(currentSolution[curIndex]);
            }
//            ArrayList<Integer> newDomain = new ArrayList<>();
//            for (Integer i : var.domain) {
//                if (occ.contains(i)) continue;
//                newDomain.add(i);
//            }
            // Remove the occurred element from the domain

            var.domain.removeAll(occ);
//            ArrayList<Integer> newDomain = new ArrayList<>();
//            for (int i = 1; i <= n; i++) {
//                if (occ.contains(i)) continue;
//                newDomain.add(i);
//            }
//
//            var.domain = newDomain;
        }
    }

    static class CellConstraint extends Constraint {
        @Override
        void infer(int[] currentSolution, int index, int n, Variable var) {
            HashSet<Integer> occ = new HashSet<>();
            int cellSize = (int) Math.sqrt(n);
            // Calculate the column's start index of the cell
            int columnStartIndex = index % n;
            int cellColumnStartIndex = columnStartIndex / cellSize * cellSize;
            // Calculate the row's start index of the cell
            int row = index / n;
            int rowStartIndex = row / cellSize * cellSize;

            // Calculate the cell's start index
            int cellStartIndex = rowStartIndex * n + cellColumnStartIndex;
//            if (index == 3) {
//                System.out.println();
//                System.out.println("index " + index);
//                System.out.println("cellSize " + cellSize);
//                System.out.println("columnStartIndex " + columnStartIndex);
//                System.out.println("cellColumnStartIndex " + cellColumnStartIndex);
//                System.out.println("row " + row);
//                System.out.println("rowStartIndex " + rowStartIndex);
//                System.out.println("cellStartIndex " + cellStartIndex);
//            }
            for (int i = 0; i < cellSize; i++) {
                int curIndex = cellStartIndex + i;
                for (int j = 0; j < cellSize; j++) {
                    int tmp = curIndex + j * n;
                    occ.add(currentSolution[tmp]);
                }
            }
            var.domain.removeAll(occ);
//            ArrayList<Integer> newDomain = new ArrayList<>();
//            for (int i = 1; i <= n; i++) {
//                if (occ.contains(i)) continue;
//                newDomain.add(i);
//            }
//
//            var.domain = newDomain;
        }
    }

    // Example implementation of the Constraint interface.
    // It enforces that for given variable X, it holds that 5 < X < 10.
    //
    // This particular constraint will most likely not be very useful to you...
    // Remove it and design a few constraints that *can* help you!
    static abstract class BetweenFiveAndTenConstraint {
        Variable var;

        public BetweenFiveAndTenConstraint(Variable var) {
            this.var = var;
        }

        void infer() {
            List<Integer> newDomain = new LinkedList<>();

            for (Integer x : this.var.domain) {
                if (5 < x && x < 10)
                    newDomain.add(x);
            }

            this.var.domain = newDomain;
        }
    }

    Variable[] variables;
    Constraint[] constraints;
    List<int[]> solutions;
    // you can add more attributes

    /**
     * Constructs a solver.
     * @param variables The variables in the problem
     * @param constraints The constraints applied to the variables
     */
    public Solver(Variable[] variables, Constraint[] constraints) {
        this.variables = variables;
        this.constraints = constraints;

        solutions = new ArrayList<>();
    }

    /**
     * Searches for one solution that satisfies the constraints.
     * @return The solution if it exists, else null
     */
    int[] findOneSolution(int n, int k, int[] currentSolution) {
        solve(false, n , k, currentSolution);

        return !solutions.isEmpty() ? solutions.get(0) : null;
    }

    /**
     * Searches for all solutions that satisfy the constraints.
     * @return The solution if it exists, else null
     */
    List<int[]> findAllSolutions(int n, int k, int[] currentSolution) {
        solve(true, n, k, currentSolution);

        return solutions;
    }

    /**
     * Main method for solving the problem.
     * @param findAllSolutions Whether the solver should return just one solution, or all solutions
     */
    void solve(boolean findAllSolutions, int n, int k, int[] currentSolution) {
        // here you can do any preprocessing you might want to do before diving into the search
        search(findAllSolutions, currentSolution, 0, n , k);
    }

    /**
     * Solves the problem using search and inference.
     */
    void search(boolean findAllSolutions, int[] currentSolution, int index, int n, int k) {
        if (!solutions.isEmpty() && !findAllSolutions) return;
        if (index == variables.length) {
            solutions.add(currentSolution.clone());
//            if (!findAllSolutions) return;
//            solutions.add(currentSolution);
        } else {
            // If the current cell has no pre-assigned value
            if (currentSolution[index] == -1) {
                // Reduce the domain
                for (Constraint c : constraints) {
                    c.infer(currentSolution, index, n, variables[index]);
                    if (variables[index].domain.isEmpty()) return;
                }
            }
            for (Integer cur : variables[index].domain) {
                currentSolution[index] = cur;
                search(findAllSolutions, currentSolution.clone(), index + 1, n, k);
            }
        }
    }
}