#include <stdio.h>

int arr[512];

int main() {
  arr[0] = 0;
  arr[1] = 1;
  for (int i = 2;i < 512;i++) {
    arr[i] = arr[i - 1] + arr[i - 2];
  }
  
  printf("%d\n", arr[511]);
  return 0;
}
