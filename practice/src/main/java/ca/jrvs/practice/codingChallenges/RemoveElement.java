package ca.jrvs.practice.codingChallenges;

public class RemoveElement {

    private int lengthWithoutVal;

    public int removeElement(int[] nums, int val) {

        if (nums.length == 0 || (nums.length == 1 && nums[0] == val))
            return 0;

        lengthWithoutVal = nums.length;
        int[] pointers = new int[] {0, nums.length - 1};

        moveEndPointer(nums, val, pointers);

        for (; pointers[0] < pointers[1]; pointers[0]++) {

            if (nums[pointers[0]] == val) {

                lengthWithoutVal--;
                int temp = nums[pointers[1]];
                nums[pointers[1]] = nums[pointers[0]];
                nums[pointers[0]] = temp;

                pointers[1]--;
                moveEndPointer(nums, val, pointers);
            }
        }

        return lengthWithoutVal;
    }

    private void moveEndPointer(int[] nums, int val, int[] pointers) {

        while (pointers[1] >= pointers[0] && nums[pointers[1]] == val) {

            lengthWithoutVal--;
            pointers[1]--;
        }
    }
}
