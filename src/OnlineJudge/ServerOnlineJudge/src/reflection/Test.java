package reflection;

import java.net.MalformedURLException;
public class Test {

    public static void main(String[] args) throws MalformedURLException {
        //
        
        String path = "G:\\ltm\\Desktop\\ServerOnlineJudge\\build\\classes";// dường dẫn đến package, ví dụ: target\\classes\\demojudge
        SourceParser s= new SourceParser(path);
        System.out.println(s.parserAllClassToJson());
    }
}
