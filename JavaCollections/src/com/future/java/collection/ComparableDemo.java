package com.future.java.collection;

public class ComparableDemo {
    public static void main(String[] args) {
        Integer i1 = 45;
        Integer i2 = 99;

        int comparisonA = i1.compareTo(i2);
        int comparisonB = i2.compareTo(i1);
        System.out.println(comparisonA);
        System.out.println(comparisonB);
    }


    class Spaceship implements Comparable<Spaceship> {

        private String spaceshipClass = null;
        private String registrationNo = null;

        public Spaceship(String spaceshipClass, String registrationNo) {
            this.spaceshipClass = spaceshipClass;
            this.registrationNo = registrationNo;
        }

        @Override
        public int compareTo(Spaceship other) {
            int spaceshipClassComparison =
                    this.spaceshipClass.compareTo(other.spaceshipClass);

            if (spaceshipClassComparison != 0) {
                return spaceshipClassComparison;
            }

            return this.registrationNo.compareTo(other.registrationNo);
        }
    }
}
