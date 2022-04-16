import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamFinalOperation {

    public static void main(String[] args) {
        String[] strArr = {
                "Inheritance", "Java", "Lamda", "Stream",
                "OptionalDouble", "IntStream","count","sum","seungmin"
        };

        Stream.of(strArr)       //병렬처리라 랜덤한 결과가 나온다.
                .parallel()
                .forEach(System.out::println);

        System.out.println("-----------");

        Stream.of(strArr)       //직렬처리
                .forEach(System.out::println);


        System.out.println("-----------");


        Stream.of(strArr)       //병렬처리지만 Ordered를 사용한다.
                .parallel()
                .forEachOrdered(System.out::println);

        System.out.println("-----------");


        boolean noEmptyStr = Stream.of(strArr).noneMatch((s->s.length()==0));
        System.out.println(noEmptyStr);

        Optional<String> sWord = Stream.of(strArr)
                .filter(s->s.charAt(0)=='s').findFirst();

        Optional<String> sWordParell = Stream.of(strArr)    //이 결과는 항상 바뀜
                .parallel()
                .filter(s->s.charAt(0)=='s').findAny();

        System.out.println(sWord.orElseGet(()->"None"));
        System.out.println(sWordParell.orElseGet(()->"None"));   //

        //Stream<String>을 Stream<Integer>으로 변환
        Stream<Integer> intStream = Stream.of(strArr).map(String::length);

        //Stream<String>을 IntStream으로 변환  mapToInt를 사용해서 IntStream  으로 성능향상
        IntStream intStream1 = Stream.of(strArr).mapToInt(String::length);
        IntStream intStream2 = Stream.of(strArr).mapToInt(String::length);
        IntStream intStream3 = Stream.of(strArr).mapToInt(String::length);
        IntStream intStream4 = Stream.of(strArr).mapToInt(String::length);


        int count = intStream1.reduce(0,(a,b)->++a);
        int sum = intStream2.reduce(0,(a,b)->a+b);

        //int max = intStream3.reduce(Integer.MIN_VALUE,(a,b)->(a>b)?a:b);
        OptionalInt max = intStream3.reduce(Integer::max);
        OptionalInt max2 = IntStream.empty().reduce(Integer::max);      //빈 스트림으로 돌릴경우
        //int min = intStream3.reduce(Integer.MIN_VALUE,(a,b)->(a>b)?b:a);
        OptionalInt min = intStream4.reduce(Integer::min);

        System.out.println("count = "+count);
        System.out.println("sum = "+sum);
        System.out.println("max = "+max);
        System.out.println("max2 = "+max2.orElse(0));
        System.out.println("min = "+min);



    }
}
