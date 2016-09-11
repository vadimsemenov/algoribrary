package tasks;

public class BuffingPixie {
    public int fastestVictory(int HP, int normalAttack, int buffedAttack) {
        long hp = HP;
        long a = normalAttack;
        long b = buffedAttack;
        if (a * 2 >= b) {
            return (int) ((hp + a - 1) / a);
        }
        long base = hp / b;
        hp -= base * b;
        if (hp <= 0) {
            return (int) base * 2;
        }
        if (hp <= a) {
            return (int) base * 2 + 1;
        }
        return (int) base * 2 + 2;
    }
}
