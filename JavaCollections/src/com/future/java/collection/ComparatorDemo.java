package com.future.java.collection;

import java.util.Comparator;

public class ComparatorDemo {

    public static void main(String[] args) {

    }

    class Spaceship implements Comparable<Spaceship> {

        private String spaceshipClass = null;
        private String registrationNo = null;

        public Spaceship(String spaceshipClass, String registrationNo) {
            this.spaceshipClass = spaceshipClass;
            this.registrationNo = registrationNo;
        }

        public String getSpaceshipClass() {
            return spaceshipClass;
        }

        public String getRegistrationNo() {
            return registrationNo;
        }

        @Override
        public int compareTo(Spaceship other) {
            int spaceshipClassComparison =
                    this.spaceshipClass.compareTo(other.spaceshipClass);

            if(spaceshipClassComparison != 0) {
                return spaceshipClassComparison;
            }

            return this.registrationNo.compareTo(other.registrationNo);
        }
    }


    class SpaceshipComparator implements Comparator<Spaceship> {

        @Override
        public int compare(Spaceship o1, Spaceship o2) {
            return o1.getRegistrationNo().compareTo(o2.getRegistrationNo());
        }
    }

//    class SpaceshipComparatorB implements Comparator<Spaceship> {
//
//        @Override
//        public int compare(Spaceship o1, Spaceship o2) {
//            return o1.getRegistrationNo() - o2.getRegistrationNo();
//        }
//    }
}
