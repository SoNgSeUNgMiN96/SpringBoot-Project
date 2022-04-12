import java.util.*;
import java.util.stream.Stream;

/*
    스트림의 중간연산을 배워보는 에제입니다.
    여기선 sorted의 부분을 확인합니다.
 */

public class StreamEx2 {
    public static void main(String[] args) {
        Stream<Student> studentStream = Stream.of(
                new Student("이자바",3,300),
                new Student("김자바",1,200),
                new Student("안자바",2,100),
                new Student("박자바",2,150),
                new Student("소자바",1,200),
                new Student("나자바",3,290),
                new Student("감자바",3,180)
        );

        /*
        studentStream.sorted(Comparator.comparing(Student::getBan)
                .thenComparing(Comparator.naturalOrder()))
                .forEach(System.out::println);
        */

        studentStream.sorted(Comparator.comparing((Student s)->s.getBan())
                .thenComparing(Comparator.naturalOrder()).reversed())
                .forEach(System.out::println);


    }
}

class Student implements Comparable<Student>{
    String name;
    int ban;
    int totalScore;

    Student(String name, int ban, int totalScore){
        this.name = name;
        this.ban = ban;
        this.totalScore = totalScore;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", ban=" + ban +
                ", totalScore=" + totalScore +
                '}';
    }

    public String getName() {
        return name;
    }

    public int getBan() {
        return ban;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public int compareTo(Student s){
        return s.totalScore - this.totalScore;
    }
}
