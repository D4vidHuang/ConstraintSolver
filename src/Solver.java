import java.util.*;

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
//                if (constraints.length > 0 && !checkConstraints(currentSolution)) continue;
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
                    return false;
                }
            }
            return true;
        }

        @Override
        void infer() {

        }
    }

    static class UniqueConstraint extends Constraint {
        @Override
        boolean check(int[] currentSolution) {
            StringBuilder sb = new StringBuilder();
            sb.append("{ ");
            for (int i = 0; i < currentSolution.length; i++) {
                sb.append(currentSolution[i] + ", ");
            }
            System.out.println(sb);
            Set<Integer> seen = new HashSet<>();
            for (int value : currentSolution) {
                if (seen.contains(value) && value != 0) {
                    System.out.println("false");
                    return false;
                }
                seen.add(value);
            }
            return true;
        }

        @Override
        void infer() {

        }
    }




}
