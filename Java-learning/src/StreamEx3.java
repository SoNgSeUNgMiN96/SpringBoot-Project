import java.io.File;
import java.util.stream.Stream;

public class StreamEx3 {
    public static void main(String[] args) {
        File[] fileArr = {new File("StreamEx.java"), new File("streamEx2.java")};

        Stream<File> fileStream = Stream.of(fileArr);

        //map()으로 stream<File>을 Stream<Strieng>으로 변환
        Stream<String> filenameStream = fileStream.map(File::getName);
        filenameStream.forEach(System.out::println);

        fileStream = Stream.of(fileArr);

        fileStream.map(File::getName)
                .filter(s->s.indexOf('.')!=-1)  //확장자가 없는 것은 제외
                .peek(s->System.out.printf("filename = %s\n",s))    //파일명 추출
                .map(s->s.substring(s.indexOf('.')+1))  //확장자만 추출
                .map(String::toUpperCase)   //모두 대문자로 변환
                .distinct()     //중복 제거
                .forEach(System.out::print);

    }
}
