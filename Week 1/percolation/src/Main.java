import edu.princeton.cs.algs4.In;

public class Main
{
    public static void main(String[] args)
    {
        PercolationStats s = new PercolationStats(20, 100);
        System.out.println(s.mean());
        System.out.println(s.stddev());
        System.out.println(s.confidenceHi());
        System.out.println(s.confidenceLo());
    }
}
