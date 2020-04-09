import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class Percolation
{
    private final WeightedQuickUnionUF unionObj;
    private WeightedQuickUnionUF ufSupport;
    private boolean[][] open;
    private final int n;
    private int noOfOpen = 0;
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n)
    {
        if(n>0)
        {
            this.n = n;
            unionObj = new WeightedQuickUnionUF(n*n+2);
            ufSupport=new WeightedQuickUnionUF(n*n+1);
            open = new boolean[n][n];
            for (int i = 0; i < n; i++)
            {
                for (int j = 0; j < n; j++)
                    open[i][j] = false;
            }
        }
        else
            throw new IllegalArgumentException();

    }
    // opens the site (row, col) if it is not open already
    public void open(int row, int col)
    {
        if (validate(row, col))
        {
            if (isOpen(row, col))
                return;
            else {
                int index = ((row - 1) * n) + col;
                open[row - 1][col - 1] = true;
                noOfOpen++;
                if (col != n) {
                    if (open[row - 1][col]) {
                        unionObj.union(index, index + 1);
                        ufSupport.union(index, index + 1);
                    }
                }
                if (col != 1) {
                    if (open[row - 1][col - 2]) {
                        unionObj.union(index, index - 1);
                        ufSupport.union(index, index - 1);
                    }
                }
                if (row != 1) {
                    int index1 = ((row - 2) * n) + col;
                    if (open[row - 2][col - 1]) {
                        unionObj.union(index, index1);
                        ufSupport.union(index, index1);
                    }
                } else {
                    unionObj.union(0, index);
                    ufSupport.union(0, index);
                }
                if (row != n) {
                    int index1 = ((row) * n) + col;
                    if (open[row][col - 1]) {
                        unionObj.union(index, index1);
                        ufSupport.union(index, index1);
                    }
                } else {
                    unionObj.union(n * n + 1, index);
                }
            }
        }
        else
            throw new IllegalArgumentException();
    }
    // is the site (row, col) open?
    public boolean isOpen(int row, int col)
    {
        if(validate(row, col))
            return open[row-1][col-1];
        else
            throw new IllegalArgumentException();
    }
    // is the site (row, col) full?
    public boolean isFull(int row, int col)
    {
        if(validate(row, col))
        {
            int index=((row-1)*n)+col;
            return ufSupport.connected(0,index);
        }
        else
            throw new IllegalArgumentException();
    }
    // returns the number of open sites
    public int numberOfOpenSites()
    {
        return noOfOpen;
    }
    // does the system percolate?
    public boolean percolates()
    {
        return unionObj.connected(0, n*n+1);
    }
    // test client (optional)
    public static void main(String[] args)
    {
        In in = new In(args[0]);
        int[] list = in.readAllInts();
        Percolation p = new Percolation(list[0]);
        for (int i = 1; i < list.length; i += 2)
        {
            p.open(list[i], list[i+1]);
            System.out.println(list[i]+" "+list[i+1]);
            System.out.println(p.isFull(list[i], list[i+1]));
        }
        boolean ans = p.percolates();
        if (ans)
            System.out.println("Percolates");
        else
            System.out.println("Does not percolate");

    }
    private boolean validate(int i, int j)
    {
        return i > 0 && i <= n && j > 0 && j <= n;
    }
}
