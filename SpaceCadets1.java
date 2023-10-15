import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class SpaceCadet1 {
    public SpaceCadet1() {
    }

    public static void main(String[] args) throws IOException {
        System.out.print("Enter your email id here:");
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String email = "https://www.ecs.soton.ac.uk/people/" + input.readLine();
        new SpaceCadet1();
        URL url = new URL(email);
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

        String web;
        while((web = reader.readLine()) != null) {
            try {
                if (web.substring(19, 24).equals("title")) {
                    System.out.println(web.substring(34, web.indexOf("/>")));
                    break;
                }
            } catch (Exception var8) {
            }
        }

    }
}