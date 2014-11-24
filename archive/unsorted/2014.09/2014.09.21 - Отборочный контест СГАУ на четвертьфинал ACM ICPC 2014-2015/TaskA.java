package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskA {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int a = in.nextInt();
        int b = in.nextInt();
        int c = in.nextInt();
        int r = in.nextInt();
        double cosa = cos(b, c, a);
        double alpha = Math.acos(cosa);
        double cosb = cos(a, c, b);
        double betha = Math.acos(cosb);
        double cosc = cos(a, b, c);
        double gamma = Math.acos(cosc);
        /*
        double aaa = a / cosa;
        double aa = Math.sqrt(aaa * aaa - r * r);
        double bbb = b / cosb;
        double bb = Math.sqrt(bbb * bbb - r * r);
        double ccc = c / cosc;
        double cc = Math.sqrt(ccc * ccc - r * r);
        */
        double p = (a + b + c) / 2.0;
        double area = Math.sqrt(p * (p - a) * (p - b) * (p - c));
        // System.err.println(area);
        // answer -= (aa + bb + cc) * r / 2.0 - r * r * (Math.acos(cosa) + Math.acos(cosb) + Math.acos(cosc));
        double part = area -
                r * r * (1 / Math.tan(alpha / 2) + 1 / Math.tan(betha / 2) + 1 / Math.tan(gamma / 2)) +
                r * r * ((Math.PI - alpha) + (Math.PI - betha) + (Math.PI - gamma)) / 2;
        out.println(part / area);
    }

    private double cos(int b, int c, int a) {
        return (b * b + c * c - a * a) / 2.0 / b / c;
    }
}
