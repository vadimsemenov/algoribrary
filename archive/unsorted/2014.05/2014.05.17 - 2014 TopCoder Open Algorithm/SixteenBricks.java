package tasks;

import java.util.Arrays;

public class SixteenBricks {
    public int maximumSurface(int[] height) {
        if (height.length != 16) throw new AssertionError(height.length);
        Arrays.sort(height);
        int result = 16;
        for (int i = 0; i < 8; i++) result += 4 * height[16 - 1 - i];            // 16
        for (int i = 0; i < 4; i++) result -= 2 * height[16 - 8 - 2 - 1 - i];    // 6
        for (int i = 0; i < 2; i++) result -= 4 * height[16 - 8 - 2 - 4 - 1 - i];// 2
        return result;
    }
}
