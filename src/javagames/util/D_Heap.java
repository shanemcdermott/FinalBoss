public class D_Heap
{
    private final int DEFAULT_SIZE = 10;
    private int d;
    private int last;
    private int[] heap;

    public D_Heap()
    {
        last = 0;
        d = 2;
        heap = new int[DEFAULT_SIZE];
    }

    public D_Heap(int dValue)
    {
        d = dValue;
        last = 0;
        heap = new int[DEFAULT_SIZE];
    }

    public void setDValue(int value)
    {
        d = value;
        percolateUp();
    }

    public void insert(int value)
    {
        last++;
        if(last>= heap.length)
            enlarge();
        
        int hole = last;
        for(heap[0] = Integer.MIN_VALUE; value < heap[getParent(hole)]; hole = getParent(hole))
        {
            heap[hole] = heap[getParent(hole)];
        }
        heap[hole] = value;
        
    }

    public void percolateUp()
    {
        int hole = last;
        int value = heap[last];
        
        for(heap[0] = Integer.MIN_VALUE; value < heap[getParent(hole)]; hole = getParent(hole))
        {
            heap[hole] = heap[getParent(hole)];
        }
        heap[hole] = value;
    }

    public void deleteMin()
    {

        heap[1] = heap[last--];
        percolateDown(1);
    }

    public void percolateDown(int hole)
    {
        int child;
        int temp = heap[hole];

        for(; hole*d <=last; hole = child)
        {
            child = hole * 2;
            if(child!=last && heap[child+1]<heap[child])
                    child++;
            if(heap[child]<temp)
            {
                heap[hole] = heap[child];
            }
            else
                break;
            
        }
        heap[hole] = temp;
    }


    public int getParent(int index)
    {
        int parent = 1;
        if(d==2)
        {
            parent = index/2;
        }
        else
        {
            parent = ((index-1)/d);
            if((index-1)%d!=0) 
                parent++;
        }
        //System.out.printf("Parent for %d: %d\n", index, parent);
        return parent;
    }

    public void enlarge()
    {

        int[] newHeap = new int[last*2];
        for(int i =0; i < heap.length; i++)
        {
            newHeap[i] = heap[i];
        }
        heap = newHeap;
    }

    public void displayHeap()
    {
        System.out.printf("Output: Heap (d = %d):", d);
        for(int i = 1; i<=last; i++)
        {
            System.out.printf(" %d", heap[i]);
        }
        System.out.println();
    }
}
