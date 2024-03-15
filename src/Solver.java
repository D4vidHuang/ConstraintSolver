import java.util.*;
//
//class Solver {
//    static class Variable {
//        List<Integer> domain;
//        // you can add more attributes
//
//        /**
//         * Constructs a new variable.
//         * @param domain A list of values that the variable can take
//         */
//        public Variable(List<Integer> domain) {
//            this.domain = domain;
//        }
//    }
//
//    static abstract class Constraint {
//        /**
//         * Tries to reduce the domain of the variables associated to this constraint, using inference
//         */
//        abstract void infer(/* you can add params */);
//    }
//
//    // Example implementation of the Constraint interface.
//    // It enforces that for given variable X, it holds that 5 < X < 10.
//    //
//    // This particular constraint will most likely not be very useful to you...
//    // Remove it and design a few constraints that *can* help you!
//    static abstract class BetweenFiveAndTenConstraint {
//        Variable var;
//
//        public BetweenFiveAndTenConstraint(Variable var) {
//            this.var = var;
//        }
//
//        void infer() {
//            List<Integer> newDomain = new LinkedList<>();
//
//            for (Integer x : this.var.domain) {
//                if (5 < x && x < 10)
//                    newDomain.add(x);
//            }
//
//            this.var.domain = newDomain;
//        }
//    }
//
//    Variable[] variables;
//    Constraint[] constraints;
//    List<int[]> solutions;
//    // you can add more attributes
//
//    /**
//     * Constructs a solver.
//     * @param variables The variables in the problem
//     * @param constraints The constraints applied to the variables
//     */
//    public Solver(Variable[] variables, Constraint[] constraints) {
//        this.variables = variables;
//        this.constraints = constraints;
//
//        solutions = new LinkedList<>();
//    }
//
//    /**
//     * Searches for one solution that satisfies the constraints.
//     * @return The solution if it exists, else null
//     */
//    int[] findOneSolution() {
//        solve(false);
//
//        return !solutions.isEmpty() ? solutions.get(0) : null;
//    }
//
//    /**
//     * Searches for all solutions that satisfy the constraints.
//     * @return The solution if it exists, else null
//     */
//    List<int[]> findAllSolutions() {
//        solve(true);
//
//        return solutions;
//    }
//
//    /**
//     * Main method for solving the problem.
//     * @param findAllSolutions Whether the solver should return just one solution, or all solutions
//     */
//    void solve(boolean findAllSolutions) {
//        // here you can do any preprocessing you might want to do before diving into the search
//
//        search(findAllSolutions /* you can add more params */);
//    }
//
//    /**
//     * Solves the problem using search and inference.
//     */
//    void search(boolean findAllSolutions /* you can add more params */) {
//        // TODO: implement search using search and inference
//    }
//}

class Solver {
    static class Variable {
        List<Integer> domain;
        // you can add more attributes

        public Variable(List<Integer> domain) {
            this.domain = domain;
        }
    }

    static abstract class Constraint {
        abstract boolean check(int[] currentSolution);

        abstract void infer();
    }

    Variable[] variables;
    Constraint[] constraints;
    List<int[]> solutions;
    int k;

    public Solver(Variable[] variables, Constraint[] constraints, int k) {
        this.variables = variables;
        this.constraints = constraints;
        this.solutions = new LinkedList<>();
        this.k = k;
    }

    int[] findOneSolution() {
        solve(false);
        return !solutions.isEmpty() ? solutions.get(0) : null;
    }

    List<int[]> findAllSolutions() {
        solve(true);
        return solutions;
    }

    void solve(boolean findAllSolutions) {
        // Initialize an empty current solution list
        int[] currentSolution = new int[variables.length];
        // Start the recursive search with an empty solution
        backtrack(0, currentSolution, 0, findAllSolutions);
    }

    void backtrack(int currentIndex, int[] currentSolution, int selectedCount, boolean findAllSolutions) {
        if (currentIndex == variables.length) {
            if (constraints.length == 0 || checkConstraints(currentSolution)) {
                solutions.add(Arrays.copyOf(currentSolution, currentSolution.length));
                if (!findAllSolutions) return;
            }
        } else {
            for (Integer value : variables[currentIndex].domain) {
                currentSolution[currentIndex] = value;
                if (k == 0 || (selectedCount + value <= k && (variables.length - currentIndex + selectedCount) >= k)) {
                    backtrack(currentIndex + 1, currentSolution, selectedCount + value, findAllSolutions);
                }
            }
        }
    }




    boolean checkConstraints(int[] currentSolution) {
        for (Constraint constraint : constraints) {
            if (!constraint.check(currentSolution)) {
                return false;
            }
        }
        return true;
    }

    static class ExactlyKConstraint extends Constraint {
        int k;

        public ExactlyKConstraint(int k) {
            this.k = k;
        }

        @Override
        boolean check(int[] currentSolution) {
            int count = 0;
            for (int value : currentSolution) {
                if (value == 1) count++;
            }
            return count == k;
        }

        @Override
        void infer() {

        }
    }

    static class NonDecreasingConstraint extends Constraint {
        @Override
        boolean check(int[] currentSolution) {
            for (int i = 0; i < currentSolution.length - 1; i++) {
                if (currentSolution[i] > currentSolution[i + 1]) {
                    return false; // 如果当前元素大于下一个元素，则不是非递减的
                }
            }
            return true; // 所有元素都是非递减的
        }

        @Override
        void infer() {
            // 此方法可以用于基于当前解的部分信息推导出其他变量的值，但在这个简单的约束中不需要实现
        }
    }



}
