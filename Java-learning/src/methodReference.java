import java.util.function.Function;
import java.util.function.Supplier;

class MyClass{
    int iv;
    MyClass(){
        iv=0;
    }

    MyClass(int iv){
        this.iv = iv;
    }
}

public class methodReference {

    public static void main(String[] args) {
        Function<String, Integer> f = s->Integer.parseInt(s);
        System.out.println(f.apply("32")*3);    //숫자로 바뀐것을 확인할 수 있다.

        Function<String, Integer> fMethod =Integer::parseInt;       //메서드 참조로 변경
        System.out.println(fMethod.apply("32")*3);

        Supplier<MyClass> s = ()-> new MyClass();      //기존 람다식
        Supplier<MyClass> sMethod = MyClass::new;       //메서드 참조

        MyClass mc1 = s.get();
        System.out.println(mc1.iv);

        MyClass mc2 = sMethod.get();
        System.out.println(mc2.iv);

        Function<Integer,MyClass> sF = i -> new MyClass(i); //매개변수가 있는경우의 람다식
        Function<Integer,MyClass> sFMethod = MyClass::new;  //매개변수가 있는 경우의 메서드 참조


        System.out.println(sF.apply(100).iv);
        System.out.println(sFMethod.apply(200).iv);

        //배열과 메서드 참조
        Function<Integer, int[]> fArray = x -> new int[x];  //람다식으로 배열 반환
        Function<Integer,int[]> fArrayMethod = int[]::new;  //메서드 참조로 배열 반환환

        int [] arrayTest1 = fArray.apply(3);
        System.out.println("array1 length = "+arrayTest1.length);

        int [] arrayTest2 = fArrayMethod.apply(5);
        System.out.println("array2 length = "+arrayTest2.length);
   }
}
