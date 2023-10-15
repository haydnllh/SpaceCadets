import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
class Methods {
    static void iden(HashMap map, String[] ar, int start, int end) {
        for (int i = start + 1; i < end ; i++) {
            if (!ar[i].equals("end")) {
                switch (ar[i].charAt(0)) {
                    case 'c':
                        map.put(ar[i].charAt(5), 0);
                        break;
                    case 'i':
                        int val = (int) map.get(ar[i].charAt(4));
                        val++;
                        map.put(ar[i].charAt(4), val);
                        break;
                    case 'd':
                        val = (int) map.get(ar[i].charAt(4));
                        val--;
                        map.put(ar[i].charAt(4), val);
                        break;
                    case 'w':
                        int count = 1;
                        int star = i;
                        int en = i;
                        char var = ar[i].charAt(5);
                        while (count >= 1) {
                            i++;
                            switch (ar[i].charAt(0)) {
                                case 'w':
                                    count++;
                                    break;
                                case 'e':
                                    count--;
                                    break;
                            }
                            en += 1;
                        }
                        while (!map.get(var).equals(0)) {
                            iden(map, ar, star, en);
                        }
                        break;
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        String allcode = new String(Files.readAllBytes(Paths.get("C:\\Space Cadets\\SpaceCadets2\\barebone.txt")));
        String[] ar = allcode.split("\\s*;\\s*");
        for (int i = 0; i < ar.length; i++) {
            ar[i] = ar[i].replace(" ","");
        }
        HashMap<Character, Integer> map = new HashMap<Character, Integer>();
        Methods.iden(map, ar, -1, ar.length);
        System.out.print(map);
    }
}