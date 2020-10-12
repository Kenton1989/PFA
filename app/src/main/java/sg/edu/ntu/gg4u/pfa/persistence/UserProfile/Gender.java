package sg.edu.ntu.gg4u.pfa.persistence.UserProfile;

public enum Gender {
    UNKNOWN(0), MALE(1), FEMALE(2), SPECIAL(3);

    private final int value;

    Gender(int val) {
        value = val;
    }

//    public int toInt() {
//        switch (this) {
//            case UNKNOWN:
//                return 0;
//            case MALE:
//                return 1;
//            case FEMALE:
//                return 2;
//            case SPECIAL:
//                return 3;
//            default:
//                return -1;
//        }
//    }
//
//    public static int toInt(Gender g) {
//        return g.toInt();
//    }
//
//    public static Gender fromInt(int val) {
//        switch (val) {
//            case 1:
//                return MALE;
//            case 2:
//                return FEMALE;
//            case 3:
//                return SPECIAL;
//            default:
//                return UNKNOWN;
//        }
//    }

}
