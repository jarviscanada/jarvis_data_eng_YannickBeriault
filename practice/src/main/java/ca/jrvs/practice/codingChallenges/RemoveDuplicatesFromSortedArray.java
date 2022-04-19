package ca.jrvs.practice.codingChallenges;

public class RemoveDuplicatesFromSortedArray {

    public int removeDuplicates(int[] nums) {

        int precedingNumber = nums[0];
        int k = nums.length;

        for (int index = 1; index < nums.length; index++) {

            if (nums[index] == precedingNumber) {

                k--;

                for (int j = index + 1; j < nums.length; j++) {

                    if (nums[j] != precedingNumber)
                        precedingNumber = nums[index++] = nums[j];
                    else
                        k--;
                }

                break;
            }

            precedingNumber = nums[index];
        }

        return k;
    }

    public int removeDuplicatesSlowVersion(int[] nums) {

        int precedingNumber = nums[0];
        int tailIndex = nums.length;

        for (int index = 1; index < tailIndex;) {

            if (nums[index] == precedingNumber) {

                for (int j = index; j < tailIndex - 1; j++)
                    nums[j] = nums[j + 1];

                tailIndex--;
            } else {

                precedingNumber = nums[index];
                index++;
            }
        }

        return tailIndex;
    }
}
