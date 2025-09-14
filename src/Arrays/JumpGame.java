package Arrays;

public class JumpGame {
    public static void main(String[] args) {
        int[] array = new int[]{2,3,1,1,4};
        String[] str = new String[]{"A", "B"};

        extracted(array);
    }

    private static boolean extracted(int[] array) {
        int lastPosition = array[array.length-1];

        for(int i = lastPosition; i>=0; i--) {
            System.out.println("i "+ i + " array of i " + array[i] + " last position " + lastPosition);
            if(i+ array[i] >= lastPosition) {
                lastPosition = i;
            }
        }
        return lastPosition == 0;
    }
}
