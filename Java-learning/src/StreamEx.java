import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamEx {


    public static void main(String[] args) {

        List<Integer> list = Arrays.asList(1,2,3,4,5);
        Stream<Integer> intStream1 = list.stream();
        intStream1.forEach(System.out::println);
        System.out.println("____________");

        Stream<String> strStream1 = Stream.of("a","b","c","d");
        strStream1.forEach(System.out::println);

        System.out.println("____________");
        String[] strArr = new String[]{"a","b","c","d","e","f"};
        Stream<String> strStream2 = Stream.of(strArr);
        strStream2.forEach(System.out::println);
        System.out.println("____________");

        Stream<String> strStream3 = Arrays.stream(strArr);
        strStream3.forEach(System.out::println);
        System.out.println("____________");

        //그냥 기본형 배열일 경우 intStream을 쓰는것이 좋다.
        int [] intArr1 = {6,32,44,512,45};
        IntStream intStream2 = Arrays.stream(intArr1);
        //intStream2.forEach(System.out::println);
        //System.out.println("count= "+intStream2.count());  //요소 수 세기
        //System.out.println("sum= "+intStream2.sum());  //모든 값 합산
        System.out.println("average = "+intStream2.average());  //모든 평균
        //다 최종여산이라 한번만 상용이 가능하다.


        System.out.println("____________");

        Integer [] intArr2 = {6,32,44,512,45};
        Stream<Integer> integerStream3 = Arrays.stream(intArr2);
        integerStream3.forEach(System.out::println);
        System.out.println("____________");

        IntStream randIntStream = new Random().ints();      //무한스트림
        randIntStream
                .limit(5)
                .forEach(System.out::println);
        System.out.println("____________");

        IntStream randIntStream2 = new Random().ints(5);      //애초에 5개로 생성
        randIntStream2
                .forEach(System.out::println);
        System.out.println("____________");

        IntStream randIntStream3 = new Random().ints(5,10);      //무한스트림, 범위는 5~10
        randIntStream3
                .limit(5)
                .forEach(System.out::println);
        System.out.println("____________");

        IntStream randIntStream4 = new Random().ints(5,5,10);      //5개로, 범위는 5~10
        randIntStream4
                .limit(5)
                .forEach(System.out::println);
        System.out.println("____________");

        //특정 범위의 정수를 갖는 스트림 만들기
        IntStream intStream4 = IntStream.range(1,5);  //마지막 수 제외
        intStream4.forEach(System.out::println);
        System.out.println("____________");

        IntStream intStream5 = IntStream.rangeClosed(1,5);  //마지막 수 포함
        intStream5.forEach(System.out::println);
        System.out.println("____________");

        //iterate(T seed, UnaryOperator f) 단항 연산자, 초기값 seed
        Stream<Integer> intStream6 = Stream.iterate(2,n->n+2);
        intStream6.limit(10).forEach(System.out::println);
        System.out.println("____________");

        //generate(Supplier s)  : 주기만하는것 입력 x 출력 O
        Stream<Integer> intStream7= Stream.generate(()->1);
        intStream7.limit(10).forEach(System.out::println);
        System.out.println("____________");

        //파일과 빈 스트림.

        Stream emptyStream = Stream.empty();
        long count = emptyStream.count();
        System.out.println("count = " +count );
    }
}
