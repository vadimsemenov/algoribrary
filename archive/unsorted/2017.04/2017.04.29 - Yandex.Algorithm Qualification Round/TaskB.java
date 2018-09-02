package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.*;

public final class TaskB {
    public void solve(int __, InputReader in, PrintWriter out) {
        int qty = in.nextInt();
        Map<String, Office> offices = new LinkedHashMap<>(qty);
        for (int i = 0; i < qty; ++i) {
            Office office = Office.readOffice(in);
            offices.put(office.name, office);
        }
        int queries = in.nextInt();
        while (queries --> 0) {
            int n = in.nextInt();
            String[] interesting = in.nextStringArray(n);
            int mask = -1;
            for (String name : interesting) {
                Office office = offices.get(name);
                int officeMask = 0;
                for (int room : office.rooms) officeMask |= room;
                mask &= officeMask;
            }
            if (mask == 0) {
                out.println("No");
            } else {
                out.print("Yes");
                int lowest = Integer.lowestOneBit(mask);
                for (String name : interesting) {
                    Office office = offices.get(name);
                    for (int i = 0; ; ++i) {
                        if ((office.rooms[i] & lowest) != 0) {
                            out.print(' ');
                            out.print(office.roomNames[i]);
                            break;
                        }
                    }
                }
                out.println();
            }
        }
    }

    static class Office {
        final String name;
        final int[] rooms;
        final String[] roomNames;

        private Office(String name, int[] rooms, String[] roomNames) {
            this.name = name;
            this.rooms = rooms;
            this.roomNames = roomNames;
        }

        static Office readOffice(InputReader in) {
            String name = in.next();
            int qty = in.nextInt();
            int[] rooms = new int[qty];
            String[] roomNames = new String[qty];
            for (int i = 0; i < qty; ++i) {
                String code = in.next();
                roomNames[i] = in.next();
                for (int hour = 0; hour < 24; ++hour) rooms[i] |= (code.charAt(hour) == '.' ? 1 : 0) << hour;
            }
            return new Office(name, rooms, roomNames);
        }
    }
}
