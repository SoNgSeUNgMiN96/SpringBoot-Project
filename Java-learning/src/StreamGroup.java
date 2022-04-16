import static java.util.Comparator.*;

import java.util.*;

import static java.util.stream.Collectors.*;

import java.util.concurrent.BlockingDeque;
import java.util.stream.Stream;


class Student2{
    String name;
    boolean isMale;
    int hak,ban,score; //학년, 반, 점수

    public Student2(String name, boolean isMale, int hak, int ban, int score) {
        this.name = name;
        this.isMale = isMale;
        this.hak = hak;
        this.ban = ban;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public boolean isMale() {
        return isMale;
    }

    public int getHak() {
        return hak;
    }

    public int getBan() {
        return ban;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "Student2{" +
                "name='" + name + '\'' +
                ", isMale=" + isMale +
                ", hak=" + hak +
                ", ban=" + ban +
                ", score=" + score +
                '}';
    }

    //groupingBy() 에서 사용
    enum Level { HIGH, MID, LOW }       //성적을 상 중 하로 분류
}


public class StreamGroup {

    public static void main(String[] args) {
        Student2[] stuArr ={
                new Student2("나자바",true, 1,1,300),
                new Student2("김지미",false, 1,1,250),
                new Student2("김자바",true, 1,1,200),
                new Student2("이지미",false, 1,2,150),
                new Student2("남자바",true, 1,2,100),
                new Student2("안지미",false, 1,2,50),
                new Student2("황지미",false, 1,3,100),
                new Student2("강지미",false, 1,3,100),
                new Student2("이자마",true, 1,3,150),
                new Student2("나자바",true, 1,1,200),
                new Student2("김지미",false, 2,1,300),
                new Student2("김자바",true, 2,1,250),
                new Student2("이지미",false, 2,2,200),
                new Student2("남자바",true, 2,2,150),
                new Student2("안지미",false, 2,2,50),
                new Student2("황지미",false, 2,3,100),
                new Student2("강지미",false, 2,3,150),
                new Student2("이자바",true, 2,3,200)
        };
        Student2[] stuArr2 = {};

        System.out.println("1. 단순불할(성별로 분할) \n");
        //Collectors.partitioning By를 바로 사용하기 위해, import static java.util.stream.Collectors.*; 로 사용한다.
        Map<Boolean, List<Student2>> stuBySex = Stream.of(stuArr)
                .collect(partitioningBy(Student2::isMale));

        List<Student2> maleStudent = stuBySex.get(true);
        List<Student2> femaleStudent = stuBySex.get(false);

        for(Student2 s: maleStudent) System.out.println(s);
        System.out.println("__________");
        for(Student2 s: femaleStudent) System.out.println(s);
        System.out.println("__________");

        System.out.println("2. 단순불할 + 통계 (학생수) \n");
        Map<Boolean, Long> stuNumBySex = Stream.of(stuArr)
                .collect(partitioningBy(Student2::isMale,counting()));

        System.out.println("남학생 수 :"+stuNumBySex.get(true));
        System.out.println("여학생 수 :"+stuNumBySex.get(false));
        System.out.println("__________");

        System.out.println("\n 3. 단순불할 + 통계 (설별 1등) \n");

        Map<Boolean, Optional<Student2>> topScoreBySex = Stream.of(stuArr)
                .collect(partitioningBy(Student2::isMale,maxBy(comparing(Student2::getScore))));

        //빈 Student2 배열을 만들어서, Optioanal에 빈 값을 넣어본다.
        Map<Boolean, Optional<Student2>> topScoreBySexEmpty = Stream.of(stuArr2)
                .collect(partitioningBy(Student2::isMale,maxBy(comparing(Student2::getScore))));


        System.out.println("남학생 일등 :"+topScoreBySex.get(true));
        //빈 값이 Optional에 들어가 있게 때문에 op
        System.out.println("남학생 일등 :"+topScoreBySexEmpty.get(true).orElse(new Student2("none",true,0,0,0)));
        System.out.println("여학생 일등 :"+topScoreBySex.get(false));
        System.out.println("__________\n");


        Map<Boolean, Student2> topScoreBySex2 = Stream.of(stuArr)
                .collect(partitioningBy(Student2::isMale,
                        collectingAndThen(  //위에서 collect만 쓴 상태에서, Optional을 꺼내라는 것을 추가하기 위해 넣어줌.
                                maxBy(comparingInt(Student2::getScore)), o -> o.orElse(new Student2("none",true,0,0,0))
                        )));

        //빈 Optional객체도 테스트
        Map<Boolean, Student2> topScoreBySex3 = Stream.of(stuArr2)
                .collect(partitioningBy(Student2::isMale,
                        collectingAndThen(  //위에서 collect만 쓴 상태에서, Optional을 꺼내라는 것을 추가하기 위해 넣어줌.
                                maxBy(comparingInt(Student2::getScore)), o -> o.orElse(new Student2("none",true,0,0,0))
                        )));

        System.out.println("남학생 1등 : "+topScoreBySex2.get(true));
        //빈 Optioanal 객체 테스트
        System.out.println("남학생 1등 : "+topScoreBySex3.get(true));
        System.out.println("여학생 1등 : "+topScoreBySex2.get(false));
        System.out.println("__________\n");

        System.out.println("\n 3. 다중분할(성별 불합격자, 100점 이하)\n");
        Map<Boolean, Map<Boolean,List<Student2>>> failedStuBySex =
                Stream.of(stuArr).collect(partitioningBy(Student2::isMale,partitioningBy(s->s.getScore() <=100 ))
                );



        List<Student2> failedMailStu = failedStuBySex.get(true).get(true);
        List<Student2> failedFemailStu = failedStuBySex.get(false).get(true);

        for(Student2 s : failedMailStu) System.out.println(s);
        System.out.println("__________\n");
        for(Student2 s : failedFemailStu) System.out.println(s);
        System.out.println("__________\n");


        System.out.println("\n1. 단순그룹화(반별로 그룹화)\n");

        Map<Integer , List<Student2>> stuBan = Stream.of(stuArr)
                .collect(groupingBy(Student2::getBan));


        //위와 아래가 같음. toList() 생략 가능
        Map<Integer , List<Student2>> stuBan2 = Stream.of(stuArr)
                .collect(groupingBy(Student2::getBan,toList()));


        System.out.println("1반인 모두를 출력");
        for (Student2 s : stuBan.get(1)) System.out.println(s);
        System.out.println("__________\n");

        System.out.println("2반인 모두를 출력");
        for (Student2 s : stuBan.get(2)) System.out.println(s);
        System.out.println("__________\n");

        System.out.println("3반인 모두를 출력");
        for (Student2 s : stuBan.get(3)) System.out.println(s);
        System.out.println("__________\n");


        System.out.println("for 문 활용__________\n");
        for(List<Student2> ban : stuBan2.values()){
            System.out.println(ban.get(0).ban+"반인 모두를 출력");
            for(Student2 s : ban) System.out.println(s);
            System.out.println("__________\n");
        }



        System.out.println("\n2. 단순그룹화(학년반별로 그룹화)\n");


        Map<Integer , Map<Integer,List<Student2>>> stuHakAndBan = Stream.of(stuArr)
                .collect(groupingBy(Student2::getHak,groupingBy(Student2::getBan)));

        //for문으로 활용해볼 변수
        Map<Integer , Map<Integer,List<Student2>>> stuHakAndBan2 = Stream.of(stuArr)
                .collect(groupingBy(Student2::getHak,groupingBy(Student2::getBan)));


        System.out.println("1학년 1반 출력");
        for (Student2 s : stuHakAndBan.getOrDefault(1,new HashMap<Integer,List<Student2>>()).getOrDefault(1, new ArrayList<Student2>())) System.out.println(s);
        System.out.println("__________\n");

        System.out.println("1학년 2반 출력");
        for (Student2 s : stuHakAndBan.getOrDefault(1,new HashMap<Integer,List<Student2>>()).getOrDefault(2, new ArrayList<Student2>())) System.out.println(s);
        System.out.println("__________\n");


        System.out.println("1학년 3반 출력");
        for (Student2 s : stuHakAndBan.getOrDefault(1,new HashMap<Integer,List<Student2>>()).getOrDefault(3, new ArrayList<Student2>())) System.out.println(s);
        System.out.println("__________\n");

        System.out.println("2학년 1반 출력");
        for (Student2 s : stuHakAndBan.getOrDefault(2,new HashMap<Integer,List<Student2>>()).getOrDefault(1, new ArrayList<Student2>())) System.out.println(s);
        System.out.println("__________\n");

        System.out.println("2학년 2반 출력");
        for (Student2 s : stuHakAndBan.getOrDefault(2,new HashMap<Integer,List<Student2>>()).getOrDefault(2, new ArrayList<Student2>())) System.out.println(s);
        System.out.println("__________\n");


        System.out.println("2학년 3반 출력");
        for (Student2 s : stuHakAndBan.getOrDefault(2,new HashMap<Integer,List<Student2>>()).getOrDefault(3, new ArrayList<Student2>())) System.out.println(s);
        System.out.println("__________\n");


        //getOrDefault로 값이 아예 없어서 null이 되는 경우를 방지 (또는 Optional으로 순차적으로 받는것을 해보기)
        System.out.println("4학년 4반 출력");
        for (Student2 s : stuHakAndBan.getOrDefault(4,new HashMap<Integer,List<Student2>>()).getOrDefault(4, new ArrayList<Student2>())) System.out.println(s);
        System.out.println("__________\n");

        System.out.println("for문 활용");
        for( Map<Integer,List<Student2>> hack : stuHakAndBan2.values() ){
            for(List<Student2> ban : hack.values()){
                System.out.println(ban.get(0).hak+"학년 "+ban.get(0).ban+"반 정보를 출력합니다. \n");
                for (Student2 s : ban) System.out.println(s);
                System.out.println("__________\n");
            }
        }

        System.out.println("\n3. 단순그룹화(성적별로 그룹화)\n");

        Map<Student2.Level, List<Student2>> stuByLevel = Stream.of(stuArr)
                .collect(groupingBy( s -> {
                    if(s.getScore()>=200) return Student2.Level.HIGH;
                    else if (s.getScore()>=100) return Student2.Level.MID;
                    else return Student2.Level.LOW;
                }));

        TreeSet<Student2.Level> keySet = new TreeSet<>(stuByLevel.keySet());

        for (Student2.Level key : stuByLevel.keySet()){
            System.out.println("["+key+"]");
            for(Student2 s : stuByLevel.get(key)) System.out.println(s);
            System.out.println("__________\n");
        }

        System.out.println("TreeSet 활용한 순차출력");
        for (Student2.Level key : keySet){
            System.out.println("["+key+"]");
            for(Student2 s : stuByLevel.get(key)) System.out.println(s);
            System.out.println("__________\n");
        }


        System.out.println("\n4. 단순그룹화 + 통계(성적별 학생수)\n");
        Map<Student2.Level, Long> stuCntByLevel = Stream.of(stuArr)
                .collect(groupingBy(s ->{
                    if(s.getScore()>=200) return Student2.Level.HIGH;
                    else if (s.getScore()>=100) return Student2.Level.MID;
                    else return Student2.Level.LOW;
                },counting()));
        for (Student2.Level key : keySet){
            System.out.println(key+" = "+stuCntByLevel.get(key)+"명");
        }
        System.out.println("__________\n");

        System.out.println("다중 그룹화, 반별 일등 출력");
        Map<Integer , Map<Integer,Student2>> stuHakAndBanBest = Stream.of(stuArr)
                .collect(groupingBy(Student2::getHak,groupingBy(Student2::getBan,
                        collectingAndThen(maxBy(comparingInt(Student2::getScore)),Optional::get)
                )));

        for(Map<Integer,Student2> hak : stuHakAndBanBest.values()){
            for (Student2 s : hak.values()){
                System.out.println(s.getHak()+"학년 "+s.getBan()+"반 1등 = "+s.getName());
            }
        }
        System.out.println("__________\n");

        System.out.println("다중 그룹화, 학년 반별 통계 그룹분류");

        Map<String,Set<Student2.Level>> stuHakAndBanLevel = Stream.of(stuArr)
                .collect(groupingBy(s-> s.getHak()+"-"+s.getBan(),mapping(s->{
                    if(s.getScore()>=200) return Student2.Level.HIGH;
                    else if(s.getScore()>=100) return Student2.Level.MID;
                    else return Student2.Level.LOW;
                }, toSet())));

        TreeSet<String> keySet2 = new TreeSet<>(stuHakAndBanLevel.keySet());

        for (String key : keySet2){
            System.out.println("["+key+"] "+stuHakAndBanLevel.get(key).toString());
        }



    }
}
