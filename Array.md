Array

Initializ:
int [] arr = new int[5];
int[] arr2 = {1, 2, 3, 5, 4};

properties:
length:  arr.length

get and set:
arr[2];
arr[1] = 0;

iterate all items:
for(int item : arr) {
  System.out.print(" " + item);
}

sort:
Arrays.sort(arr2);

Import function:
System.arraycopy:
public static void arraycopy(Object srcArr, int srcStartPos, Object destArr, int destStartPos, int copyLength)


