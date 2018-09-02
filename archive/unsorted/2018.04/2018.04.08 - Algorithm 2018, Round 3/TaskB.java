package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;

public final class TaskB {
    public void solve(int __, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        AtomicReference<NormalForm> last = new AtomicReference<>(NormalForm.ZERO);
        AtomicInteger answer = new AtomicInteger(0);
        AtomicBoolean impossible = new AtomicBoolean(false);
        IntStream.of(in.nextIntArray(counter))
                .mapToObj(NormalForm::fromInt)
                .forEachOrdered(number -> {
                    if (impossible.get()) {
                        return;
                    }
                    NormalForm lastNumber = last.get();
                    int cmp = COMPARATOR.compare(lastNumber, number);
                    if (cmp > 0) {
                        if (number == NormalForm.ZERO) {
                            impossible.set(true);
                            return;
                        }
                        int power = lastNumber.exponent - number.exponent;
                        if (LEXICOGRAPHICAL.compare(lastNumber.mantissa, number.mantissa) > 0) {
                            power++;
                        }
                        answer.addAndGet(power);
                        last.set(number.shift(power));
                    } else {
                        last.set(number);
                    }
                });
        out.println(impossible.get() ? -1 : answer);
    }

    static final Comparator<Integer> LEXICOGRAPHICAL = (lhs, rhs) -> {
        if (lhs == 0 && rhs == 0) {
            return 0;
        } else if (lhs == 0) {
            return -1;
        } else if (rhs == 0) {
            return 1;
        }
        int bit = Math.max(Integer.highestOneBit(lhs), Integer.highestOneBit(rhs));
        while (Integer.highestOneBit(lhs) != bit) {
            lhs <<= 1;
        }
        while (Integer.highestOneBit(rhs) != bit) {
            rhs <<= 1;
        }
        while (bit > 0) {
            int cmp = Integer.compare(lhs & bit, rhs & bit);
            if (cmp != 0) {
                return cmp;
            }
            bit >>= 1;
        }
        return 0;
    };

    static final Comparator<NormalForm> COMPARATOR = Comparator
            .comparingInt(NormalForm::getExponent)
            .thenComparing(NormalForm::getMantissa, LEXICOGRAPHICAL);

    static class NormalForm {
        static final NormalForm ZERO = new NormalForm(0, 0);

        static NormalForm fromInt(int number) {
            if (number == 0) {
                return ZERO;
            }
            int exponent = Integer.numberOfTrailingZeros(number);
            number >>= exponent;
            if (number > 1) {
                exponent += Integer.numberOfTrailingZeros(Integer.highestOneBit(number));
            }
            return new NormalForm(number, exponent);
        }

        final int mantissa;
        final int exponent;

        private NormalForm(int mantissa, int exponent) {
            if (exponent != 0 && mantissa == 0) {
                throw new IllegalArgumentException(mantissa + " " + exponent);
            }
            this.mantissa = mantissa;
            this.exponent = exponent;
        }

        public NormalForm shift(int power) {
            return this == ZERO ? ZERO : new NormalForm(mantissa, exponent + power);
        }

        public int getMantissa() {
            return mantissa;
        }

        public int getExponent() {
            return exponent;
        }

        @Override
        public String toString() {
            return "NormalForm{" +
                    "mantissa=" + mantissa +
                    ", exponent=" + exponent +
                    '}';
        }
    }
}
