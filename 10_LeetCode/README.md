## 面试高频算法题目

## 排序

### [215. 数组中的第K个最大元素](https://leetcode-cn.com/problems/kth-largest-element-in-an-array/)

给定整数数组 `nums` 和整数 `k`，请返回数组中第 `**k**` 个最大的元素。

请注意，你需要找的是数组排序后的第 `k` 个最大的元素，而不是第 `k` 个不同的元素。

示例 1:

```
输入: [3,2,1,5,6,4] 和 k = 2
输出: 5
```

示例 2:

```shell
输入: [3,2,3,1,2,4,5,5,6] 和 k = 4
输出: 4
```


提示：

- `1 <= k <= nums.length <= 104`
- `-104 <= nums[i] <= 104`





![image-20220411230057305](F:\开发\10_刷题\LeetCode\面试高频算法题目.assets\image-20220411230057305.png)

方法二：快速排序

- 随机化切分元素。快速排序虽然快，但是在遇到特殊测试用例（顺序数组或者逆序数组）的时候，递归树会退化成链表，时间复杂度会变成 O(N^2)

- 一趟快速排序的算法是：

  1）设置两个变量i、j，[排序](https://baike.baidu.com/item/排序)开始的时候：i=0，j=N-1； [1] 

  2）以第一个数组元素作为关键数据，赋值给**key**，即**key**=A[0]； [1] 

  3）从j开始向前搜索，即由后开始向前搜索(j--)，找到第一个小于**key**的值A[j]，将A[j]和A[i]的值交换； [1] 

  4）从i开始向后搜索，即由前开始向后搜索(i++)，找到第一个大于**key**的A[i]，将A[i]和A[j]的值交换； [1] 

  5）重复第3、4步，直到i==j； (3,4步中，没找到符合条件的值，即3中A[j]不小于**key**,4中A[i]不大于**key**的时候改变j、i的值，使得j=j-1，i=i+1，直至找到为止。找到符合条件的值，进行交换的时候i， j指针位置不变。另外，i==j这一过程一定正好是i+或j-完成的时候，此时令循环结束）。 [1] 

<font color='red'>注意：</font> while循环中 是 left < right  里面也需要判断 填坑 （https://blog.csdn.net/qq_37084904/article/details/119852074）

```java
public class Solution {

    public int findKthLargest(int[] nums, int k) {
        int len = nums.length;
        int left = 0;
        int right = len - 1;

        // 转换一下，第 k 大元素的下标是 len - k
        int target = len - k;

        while (true) {
            int index = partition(nums, left, right);
            if (index == target) {
                return nums[index];
            } else if (index < target) {
                left = index + 1;
            } else {
                right = index - 1;
            }
        }
    }

    /**
     * 对数组 nums 的子区间 [left..right] 执行 partition 操作，返回 nums[left] 排序以后应该在的位置
     * 在遍历过程中保持循环不变量的定义：
     * nums[left + 1..j] < nums[left]
     * nums(j..i) >= nums[left]
     *
     * @param nums
     * @param left
     * @param right
     * @return
     */
    
    public int partition(int[] nums, int left, int right) {
        int pivot = nums[left]; // 选左边为轴

        while(left < right) {
            
            // 从右边找到第一个比pivot小的值
            while(left < right && nums[right] > pivot) right--;
            if(left < right) {
                swap(nums, left, right);
                left++;
            }

            // 找到左边第一个比pivot大的值
            while(left < right && nums[left] <= pivot) left++;
            if(left < right) {
                swap(nums, left, right);
                right--;
            }
          
        }
        // System.out.println(left);

        nums[left] = pivot;

        return left;

    }

    public void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}

```
#### 扩展

- 荷兰国旗算法

- 八种排序算法

  - ```java
     //selectSort 每次将当前元素替换为后面最小的元素
        public static void selectSort(int[] a){
            int N = a.length;
            for (int i = 0; i < N; i++) {
                int min = i;
                for (int j = i+1; j < N; j++) {
                    if(a[j]<a[min]) min=j;
                    int t =a[i];
                    a[i] = a[j];
                    a[j] = t;
                }
            }
        }
    ```

  - ```java
     //insertSort 每次将当前元素插入到前面已经排好序的元素中
        public static void insertSort(int[] a){
            int N = a.length;
            for (int i = 0; i < N; i++) {
                int temp = a[i];
                int j = i;
                for (; j > 0 && a[j] > temp; j--) {
                     a[j] = a[j-1];
                }
                a[j] = temp;
            }
        }
    ```

  - ```java
      //shellSort 将数组分组，并不断减小分组的步长直到为1，每次分组均进行插入排序
        public static void shellSort(int[] a){
            for (int step = a.length/2; step > 0; step/=2) {
                for (int i = step; i < a.length; i++) {
                    int temp = a[i];
                    int j = i;
                    for (; j >= step && a[j-step] > temp ; j-=step) {
                        a[j] = a[j-step];
                    }
                    a[j] = temp;
                }
            }
        }
    ```

  - ```java
    //mergeSort 递归 对两个有序节点序列进行合并来实现排序，分治思想
    
        //分解的方法
        public void mergeSort(int[] arr,int left,int right){
            //如果左边索引小于右边就可以一直分，l=r时，就是分到只剩一个数了
            if(left<right){
                int mid = (left + right) / 2;//左少右多
                //向左递归分解
                mergeSort(arr,left,mid);
                //向右递归分解
                mergeSort(arr,mid+1,right);
                //合并
                merge(arr,left,mid,right);
            }
        }
    
        //合并的方法
        /**
         *
         * @param arr 待排序的原始数组
         * @param left 左边有序序列的初始索引
         * @param mid 中间索引
         * @param right 右边结束索引
         * @return
         */
        public void merge(int[] arr, int left,int mid,int right) {
             int i = left;
             int j = mid +1;
             int[] temp = new int[right-left+1];//中转数组
             int t = 0;//temp数组的当前索引
    
            //合并数组，比较找最大
            while (i<=mid && j<=right){
                if(arr[i]<=arr[j])temp[t++] = arr[i++];
                else temp[t++] = arr[j++];
            }
            while (i<=mid) temp[t++] = arr[i++];
            while (j<=right) temp[t++] = arr[j++];
    
            //将temp数组拷贝到arr数组，并不是每次都拷贝所有
            t = 0;
            while (left<=right) arr[left++] = temp[t++];
        }
    ```

  - ```java
     //bubbleSort n-1遍历，每次找到未排序数组的最大值
        public void bubbleSort(int[] arr){
            for (int i = arr.length-1; i >= 0; i--) {
                for (int j = 0; j < i; j++) {
                    if(arr[j]>arr[j+1]){
                        int temp = arr[j];
                        arr[j] = arr[j+1];
                        arr[j+1] = temp;
                    }
                }
            }
        }
    ```

  - ```java
    //radixSort 按位数进行排序，借助桶bucket进行分配与收集
        public void radixSort(int[] arr){
            int max = 0;
            for (int i : arr) {
                if(i>max) max = i;
            }
            int count = (max+"").length();
    
            for (int i = 1; i <= count; i++) {
    
                //分配
                int[][] bucket = new int[10][arr.length];
                //bucketCount用于统计该桶中元素的数量
                int[] bucketCount = new int[10];
                for (int value : arr) {
                    bucket[value % (10 * i)][bucketCount[value % (10 * i)]++] = value;
                }
    
                //收集
                int k = 0;
                for (int j = 0; j < 10; j++) {
                    //如果桶中有数据，放入数组
                    if(bucketCount[j]!=0) {
                        //循环该桶，取出元素到arr中，每取一个元素，桶中元素-1
                        while (bucketCount[j]!=0) arr[k++] = bucket[j][--bucketCount[j]];
                    }
                }
            }
        }
    ```

  - ```java
     //heapSort 构建大顶堆或者小顶堆，将堆顶元素与堆尾元素交换后再调整，如此反复
        public void heapSort(int[] arr){
            //构建大顶堆 k为最后一个非叶子节点，逐渐-1，即从下向上，从右往左
            for(int k = arr.length/2 - 1;k>=0;k--){
                adjustHeap(arr,k,arr.length);
            }
    
            //排序 交换+调整
            int temp =0;
            for (int i = arr.length-1; i >= 0; i--) {
                temp =arr [0];
                arr[0] = arr[i];
                arr[i] = temp;
                adjustHeap(arr,0,i);
            }
        }
    
        /**
         *
         * @param arr 待调整数组
         * @param i 非叶子节点在数组中的索引
         * @param length 对多少个元素进行调整
         */
        public void adjustHeap(int[] arr,int i,int length){
            int temp = arr[i];//取出当前非叶子叶结点的值
            //k为当前节点的左子节点
            for(int k = 2*i+1;k<length;k=2*k+1){
               if(k+1<length && arr[k+1]>arr[k]){//右子节点大于左子节点
                   k++;//k指向右子节点
               }
               if(arr[k]>temp){//如果当前节点大于父节点就交换
                    arr[i] = arr[k];
                    i =k;//!!!!!!精髓，因为该子节点值大小发生了改变，可能会使其子根堆发生改变，索引要调整其子根堆
               }else {
                   break;//否则直接退出，因为其后面的节点一定满足堆定义
               }
            }
            arr[i] = temp;
        }
    ```

  - ```java
    //quickSort 每次选择一个元素并且将整个数组以这个元素分为两部分，小于该元素的放右边，大于该元素的放左边
        public void quickSort(int[] arr,int l,int r){
            if(l<r){ //跳出递归的条件
                //partition就是划分操作，将arr划分成满足条件的两个子表
                int pivotpos = partition(arr,l,r);
                //依次对左右两个子表进行递归排序
                quickSort(arr,l,pivotpos);
                quickSort(arr,pivotpos+1,r);
            }
        }
    
        public int partition(int[] arr,int l,int r){
            //以当前数组的最后一个元素作为中枢pivot，进行划分
            int pivot = arr[r];
            while (l<r){
               while (l<r && arr[l]<pivot) l++;
                arr[r] = arr[l];//将比中枢值大的移动到右端r处 由于r处为中枢或者该位置值已经被替换到l处，所以直接可以替换
               while (l<r && arr[r]>=pivot) r--;
                arr[l] = arr[r];//将比中枢值小的移动到左端l处 由于前面l处的值已经换到r处，所以该位置值也可以替换掉
            }
            //l==r时，重合，这个位置就是中枢的最终位置
            arr[l] = pivot;
            //返回存放中枢的最终位置
            return l;
        }
    ```

  - ```java
    // 计数排序
    class Solution {
        public int findKthLargest(int[] nums, int k) {
            int[] slots = new int[20001];
            for (int num : nums) {
                slots[num + 10000]++;
            }
            for (int i = slots.length - 1; i >= 0; i--) {
                k -= slots[i];
                if (k <= 0) {
                    return i - 10000;
                }
            }
            return 0;
        }
    }
    ```

  - 

### [148. 排序链表](https://leetcode-cn.com/problems/sort-list/)

给你链表的头结点 `head` ，请将其按 **升序** 排列并返回 **排序后的链表** 。

![image-20220412082034388](F:\开发\10_刷题\LeetCode\面试高频算法题目.assets\image-20220412082034388.png)

**进阶：**你可以在 `O(n log n)` 时间复杂度和常数级空间复杂度下，对链表进行排序吗？

```java
// 归并排序
class Solution {
    public ListNode sortList(ListNode head) {
        return mergeSort(head);
    }

    // 归并排序
    private ListNode mergeSort(ListNode head){
        // 如果没有结点/只有一个结点，无需排序，直接返回
        if (head==null||head.next==null) return head;
        // 快慢指针找出中位点
        ListNode slowp=head,fastp=head.next.next,l,r;
        while (fastp!=null&&fastp.next!=null){
            slowp=slowp.next;
            fastp=fastp.next.next;
        }
        // 对右半部分进行归并排序
        r=mergeSort(slowp.next);
        // 链表判断结束的标志：末尾节点.next==null
        slowp.next=null;
        // 对左半部分进行归并排序
        l=mergeSort(head);
        return mergeList(l,r);
    }
    // 合并链表
    private ListNode mergeList(ListNode l,ListNode r){
        // 临时头节点
        ListNode tmpHead=new ListNode(-1);
        ListNode p=tmpHead;
        while (l!=null&&r!=null){
            if (l.val<r.val){
                p.next=l;
                l=l.next;
            }else {
                p.next=r;
                r=r.next;
            }
            p=p.next;
        }
        p.next=l==null?r:l;
        return tmpHead.next;
    }
}
```



```java
// 快速排序
class Solution {
public ListNode sortList(ListNode head) {
        if(head==null||head.next==null) return head;
        // 没有条件，创造条件。自己添加头节点，最后返回时去掉即可。
        ListNode newHead=new ListNode(-1);
        newHead.next=head;
        return quickSort(newHead,null);
    }
    // 带头结点的链表快速排序
    private ListNode quickSort(ListNode head,ListNode end){
        if (head==end||head.next==end||head.next.next==end) return head;
        // 将小于划分点的值存储在临时链表中
        ListNode tmpHead=new ListNode(-1);
        // partition为划分点，p为链表指针，tp为临时链表指针
        ListNode partition=head.next,p=partition,tp=tmpHead;
        // 将小于划分点的结点放到临时链表中
        while (p.next!=end){
            if (p.next.val<partition.val){
                tp.next=p.next;
                tp=tp.next;
                p.next=p.next.next;
            }else {
                p=p.next;
            }
        }
        // 合并临时链表和原链表，将原链表接到临时链表后面即可
        tp.next=head.next;
        // 将临时链表插回原链表，注意是插回！（不做这一步在对右半部分处理时就断链了）
        head.next=tmpHead.next;
        quickSort(head,partition);
        quickSort(partition,end);
        // 题目要求不带头节点，返回结果时去除
        return head.next;
    }
}
```









## Map

#### [146. LRU 缓存](https://leetcode-cn.com/problems/lru-cache/)

请你设计并实现一个满足 [LRU (最近最少使用) 缓存](https://baike.baidu.com/item/LRU) 约束的数据结构。

实现 `LRUCache` 类：

- `LRUCache(int capacity)` 以 **正整数** 作为容量 `capacity` 初始化 LRU 缓存
- `int get(int key)` 如果关键字 `key` 存在于缓存中，则返回关键字的值，否则返回 `-1` 。
- `void put(int key, int value)` 如果关键字 `key` 已经存在，则变更其数据值 `value` ；如果不存在，则向缓存中插入该组 `key-value` 。

​	如果插入操作导致关键字数量超过 `capacity` ，则应该 **逐出** 最久未使用的关键字。

函数 `get` 和 `put` 必须以 `O(1)` 的平均时间复杂度运行。

```java
输入
["LRUCache", "put", "put", "get", "put", "get", "put", "get", "get", "get"]
[[2], [1, 1], [2, 2], [1], [3, 3], [2], [4, 4], [1], [3], [4]]
输出
[null, null, null, 1, null, -1, null, -1, 3, 4]

解释
LRUCache lRUCache = new LRUCache(2);
lRUCache.put(1, 1); // 缓存是 {1=1}
lRUCache.put(2, 2); // 缓存是 {1=1, 2=2}
lRUCache.get(1);    // 返回 1
lRUCache.put(3, 3); // 该操作会使得关键字 2 作废，缓存是 {1=1, 3=3}
lRUCache.get(2);    // 返回 -1 (未找到)
lRUCache.put(4, 4); // 该操作会使得关键字 1 作废，缓存是 {4=4, 3=3}
lRUCache.get(1);    // 返回 -1 (未找到)
lRUCache.get(3);    // 返回 3
lRUCache.get(4);    // 返回 4

```

1. 查看LinkedHashMap源码发现它可以解决，但是面试这种方式不推荐。
2. 自己定义双向链表 (在双向链表的实现中，使用一个伪头部（dummy head）和伪尾部（dummy tail）标记界限，这样在添加节点和删除节点的时候就不需要检查相邻的节点是否存在)

```java
public class LRUCache {
    class DLinkedNode {
        int key;
        int value;
        DLinkedNode prev;
        DLinkedNode next;
        public DLinkedNode() {}
        public DLinkedNode(int _key, int _value) {key = _key; value = _value;}
    }

    private Map<Integer, DLinkedNode> cache = new HashMap<Integer, DLinkedNode>();
    private int size;
    private int capacity;
    private DLinkedNode head, tail;

    public LRUCache(int capacity) {
        this.size = 0;
        this.capacity = capacity;
        // 使用伪头部和伪尾部节点
        head = new DLinkedNode();
        tail = new DLinkedNode();
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        DLinkedNode node = cache.get(key);
        if (node == null) {
            return -1;
        }
        // 如果 key 存在，先通过哈希表定位，再移到头部
        moveToHead(node);
        return node.value;
    }

    public void put(int key, int value) {
        DLinkedNode node = cache.get(key);
        if (node == null) {
            // 如果 key 不存在，创建一个新的节点
            DLinkedNode newNode = new DLinkedNode(key, value);
            // 添加进哈希表
            cache.put(key, newNode);
            // 添加至双向链表的头部
            addToHead(newNode);
            ++size;
            if (size > capacity) {
                // 如果超出容量，删除双向链表的尾部节点
                DLinkedNode tail = removeTail();
                // 删除哈希表中对应的项
                cache.remove(tail.key);
                --size;
            }
        }
        else {
            // 如果 key 存在，先通过哈希表定位，再修改 value，并移到头部
            node.value = value;
            moveToHead(node);
        }
    }

    private void addToHead(DLinkedNode node) {
        node.prev = head;
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
    }

    private void removeNode(DLinkedNode node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void moveToHead(DLinkedNode node) {
        removeNode(node);
        addToHead(node);
    }

    private DLinkedNode removeTail() {
        DLinkedNode res = tail.prev;
        removeNode(res);
        return res;
    }
}

```

### 



## 动态规划

