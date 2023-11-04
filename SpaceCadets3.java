import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

class Methods {
    public static String classname = "";
    public static boolean isMain = true;
    static void iden(HashMap map, String[] ar, int start, int end, HashMap methodsmap) {
        for (int i = start + 1; i < end; i++) {
            String[] code = ar[i].split("\s+|\\(|\\)|,");
            switch (code[0]) {
                case "//":
                    i++;
                    while (!code[0].equals("//")) {
                        i++;
                    }
                    break;
                case "Math":
                    float result = 0;
                    try {
                        result = Float.valueOf(code[1]);
                    } catch (NumberFormatException nfe) {
                        result = (float) map.get(code[1]);
                    }
                    try {
                        result += Float.valueOf(code[3]);
                    } catch (NumberFormatException nfe) {
                        result = (float) map.get(code[3]);
                    }
                    for (int j = 2; j < code.length / 2; j++) {
                        float number = 0;
                        try {
                            number = Float.valueOf(code[2 * j + 1]);
                        } catch (NumberFormatException nfe) {
                            number = (float) map.get(code[2 * j + 1]);
                        }
                        switch (code[2 * j]) {
                            case "+":
                                result += number;
                                break;
                            case "-":
                                result -= number;
                                break;
                            case "*":
                                result *= number;
                                break;
                            case "/":
                                result /= number;
                                break;
                        }
                    }
                    map.put(code[1], result);
                    break;
                case "clear":
                    if (isMain) {
                        map.put(code[1], 0f);
                    } else if (!isMain) {
                        map.put(classname + "." + code[1], 0f);
                    }
                    break;
                case "incr":
                    float val = (float) map.get(code[1]);
                    val++;
                    map.put(code[1], val);
                    break;
                case "decr":
                    val = (float) map.get(code[1]);
                    val--;
                    map.put(code[1], val);
                    break;
                case "while":
                    int count = 1;
                    int star = i;
                    int en = i;
                    String var = code[1];
                    while (count >= 1) {
                        i++;
                        code = ar[i].split("\s+|\\(|\\)|\\,");
                        if (code[0].equals("while")) {
                            count++;
                        } else if (code[0].isEmpty()) {
                            count--;
                        }
                        en++;
                    }
                    while (!map.get(var).equals(0f)) {
                        iden(map, ar, star, en, methodsmap);
                    }
                    break;
                case "if":
                    float verifier = Float.valueOf(code[3]);
                    count = 1;
                    star = i;
                    en = i;
                    String[] firstifcode = code;
                    while (count >= 1) {
                        i++;
                        code = ar[i].split("\s+");
                        if (code[0].equals("if")) {
                            count++;
                        } else if (code[0].isEmpty()) {
                            count--;
                        }
                        en++;
                    }
                    if (firstifcode[2].equals("==") && map.get(firstifcode[1]).equals(verifier)) {
                        iden(map, ar, star, en, methodsmap);
                    } else if (firstifcode[2].equals("!=") && !map.get(firstifcode[1]).equals(verifier)) {
                        iden(map, ar, star, en, methodsmap);
                    }
                    break;
                case "def":
                    count = 1;
                    star = i;
                    en = i;
                    String[] firstline = code;
                    while (count >= 1) {
                        i++;
                        code = ar[i].split("\s+");
                        if (code[0].equals("if") || code[0].equals("while")) {
                            count++;
                        } else if (code[0].isEmpty()) {
                            count--;
                        }
                        en++;
                    }
                    int paranum = 0;
                    for (int k = 2; k < firstline.length; k++) {
                        map.put(firstline[k], 0f);
                        paranum++;
                    }
                    int[] startend = new int[]{star, en, paranum};
                    if (isMain) {
                        methodsmap.put(firstline[1], startend);
                    } else if (!isMain) {
                        methodsmap.put(classname + "." + firstline[1], startend);
                    }
                    break;
                case "class":
                    classname = code[1];
                    if (classname.equals("Main")) {
                        isMain = true;
                    } else {
                        isMain = false;
                    }
                    count = 1;
                    star = i;
                    en = i;
                    while (count >= 1) {
                        i++;
                        code = ar[i].split("\s+");
                        if (code[0].equals("if") || code[0].equals("while") || code[0].equals("def")) {
                            count++;
                        } else if (code[0].isEmpty()) {
                            count--;
                        }
                        en++;
                    }
                    iden(map, ar, star, en, methodsmap);
                default:
                    if (methodsmap.containsKey(code[0])) {
                        int[] intar = (int[]) methodsmap.get(code[0]);
                        int methodstart = intar[0];
                        int methodend = intar[1];
                        int numpara = intar[2];
                        String[] methodline = ar[methodstart].split("\s+|\\(|\\)|\\,");
                        String[] callmethodline = code;
                        for (int k = 0; k < numpara; k++) {
                            map.put(methodline[k + 2], map.get(code[k + 1]));
                        }
                        iden(map, ar, methodstart, methodend, methodsmap);
                        String[] returnline = ar[methodend - 1].split("\s+");
                        map.put(callmethodline[numpara + 3], map.get(returnline[1]));
                    }
            }
        }
    }
}
class Main {
    public static void main(String[] args) throws IOException {
        String allcode = new String(Files.readAllBytes(Paths.get("C:\\Users\\haydn\\SpaceCadets\\sample.txt")));
        String[] ar = allcode.split("\\s*;\\s*|\\{|\\}");
        for (int i = 0; i < ar.length; i++) {
            ar[i] = ar[i].trim();
        }
        HashMap<String, Float> map = new HashMap<String, Float>();
        HashMap<String, int[]> methodsmap = new HashMap<String, int[]>();
        Methods.iden(map, ar, -1, ar.length, methodsmap);
        System.out.print(map);
    }
}