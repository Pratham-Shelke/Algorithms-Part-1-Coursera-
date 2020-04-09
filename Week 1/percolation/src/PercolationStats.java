import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats
{

    private final double mn;
    private final double std;
    private final double config1;
    private final double config2;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials)
    {
        if(n<=0)
            throw new IllegalArgumentException();
        if(trials<=0)
            throw new IllegalArgumentException();
        double CONFIDENCE_95 = 1.96;
        Percolation p;
        double[] x;

        x = new double[trials];
        for (int i = 0; i < trials; i++)
        {
            p = new Percolation(n);
            int op = 0;
            while (!p.percolates())
            {
                int column;
                int row;
                do {
                    column = 1 + StdRandom.uniform(n);
                    row = 1 + StdRandom.uniform(n);
                } while (p.isOpen(row, column));

                p.open(row, column);
                op++;
            }

            double val = (double) op/(double) (n*n);
            x[i] = val;
        }
        mn = StdStats.mean(x);
        std = StdStats.stddev(x);
        config1 = mn-((CONFIDENCE_95 *std)/squareRoot(trials));
        config2 = mn+((CONFIDENCE_95 *std)/squareRoot(trials));

    }

    // sample mean of percolation threshold
    public double mean()
    {
        return mn;
    }

    // sample standard deviation of percolation threshold
    public double stddev()
    {
        return std;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo()
    {
        return config1;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi()
    {
        return config2;
    }

    // test client (see below)
    public static void main(String[] args)
    {
        PercolationStats s = new PercolationStats(200, 100);
        System.out.println(s.mean());
        System.out.println(s.stddev());
        System.out.println(s.confidenceHi());
        System.out.println(s.confidenceLo());

    }

    private static double squareRoot(int number) {
        double temp;

        double sr = (double) number / 2;

        do {
            temp = sr;
            sr = (temp + (number / temp)) / 2;
        } while ((temp - sr) != 0);

        return sr;
    }

}
