import java.util.Optional;
import java.util.OptionalInt;

public class OptionalEx {
    public static void main(String[] args) {
        int [] arr = new int[0];
        System.out.println("arr.length = "+arr.length);

        //Optional<String> opt = null;
        Optional<String> opt = Optional.empty();
        Optional<String> opt2 = Optional.of("abc");     //value가 abc인 Optional객체 생성.

        System.out.println("opt2 = "+opt2);
        String str = "";

        //str = opt2.orElse("EMPTY");  //Optional에 저장된 값이 null이면 EMPTY로 반환
        str = opt.orElseGet(()->new String());      //Optianal에 저장된 값이 없다면 Supplier 람다식 사용가능
        System.out.println("str = "+str);
        //intellij surround with option + command + Ta
//        try {
//            str = opt.get();
//        } catch (Exception e) {
//            str = "";
//        }
        System.out.println(str);


        Optional<String> optStr = Optional.of("abcde");
        Optional<Integer> optInt = optStr.map(String::length);  // s->s.length

        System.out.println("optStr="+optStr.get());
        System.out.println("optInt="+optInt.get());

        int result1 = Optional.of("123")
                .filter(x->x.length()>0)
                .map(Integer::parseInt).get();

        int result2 = Optional.of("")
                .filter(x->x.length()>0)
                .map(Integer::parseInt).orElse(-1);

        System.out.println("result1="+result1);
        System.out.println("result2="+result2);

        Optional.of("456").map(Integer::parseInt)
                .ifPresent(x->System.out.println("result="+x));


        OptionalInt optInt1 = OptionalInt.of(0);    //0을 넣어줌
        OptionalInt optInt2 = OptionalInt.empty();    //아무것도 저장 X  (0이 들어감)

        System.out.println(optInt1.isPresent());        //같은 0이지만 트로
        System.out.println(optInt2.isPresent());        //false가 리턴된다.

        System.out.println(optInt1.getAsInt());
        //System.out.println(optInt2.getAsInt()); //NoSuchElementException
        System.out.println("optional1="+optInt1);
        System.out.println("optional2="+optInt2);
        System.out.println("optionalInt1.equals(optionalInt2)?"+optInt1.equals(optInt2));
    }
}
