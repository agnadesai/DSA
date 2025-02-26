package Arrays;

public class JumpGame {
    public static void main(String[] args) {
        int[] array = new int[]{1,2,3};
        String[] str = new String[]{"A", "B"};

        extracted(array);
    }

    private static boolean extracted(int[] array) {
        int lastPosition = array[array.length-1];

        for(int i = lastPosition; i>=0; i--) {
            if(i+ array[i] >= lastPosition) {
                lastPosition = i;
            }
        }
        return lastPosition == 0;
    }
}
