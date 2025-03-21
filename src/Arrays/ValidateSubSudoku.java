package Arrays;

/**
 * [1 2 3]
 * [2 3 1]
 * [3 1 2] -> should return true
 *
 * [1 1]
 * [2 2] should return false
 */
public class ValidateSubSudoku {

    public boolean isValidSubSudoku(int[][] matrix) {
        int n = matrix[0].length;

        for(int i=0; i< n; i++) {
            boolean[] seen = new boolean[n+1];
                for(int j=0 ; j<n; j++) {
                    int num = matrix[i][j];
                    if(num<1 || num>n || seen[num]) {
                        return false;
                    }
                    seen[num] = true;
                }
            }


            for(int j = 0 ;j< n ; j++) {
                boolean[] seen = new boolean[n+1];
                for(int i=0 ; i<n; i++) {
                    int num = matrix[i][j];
                    if(num<1 || num>n || seen[num]) {
                        return false;
                    }
                    seen[num] = true;
                }
            }

        return true;
    }
}
