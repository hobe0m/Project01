package org.RentalProject.RentalService.commons.validators;

// 유연성을 부여하기 위해, Interface로 생성
public interface PasswordValidator {

    /**
     * 비밀번호에 알파벳 포함 여부
     *
     * @param password
     * @param caseIncensitive false : 대문자 1개 이상, 소문자 1개 이상 포함
     *                        true : 대소문자 구분 없이 1개 이상 포함
     * @return
     */

    // caseIncensitive : 대소문자 구분하지 않고 1개(true), 구분해서 1개씩(false)의 의미를 담는 변수
    // 해당 메소드를 사용하려면 매개변수로 String password 값과 Boolean caseIncensitive 값을 꼭 넣어주어야 한다.
    default boolean alphaCheck(String password, Boolean caseIncensitive) {
        if (caseIncensitive) { // true이므로 대소문자 구분 없이 체크
            /*
              정규표현식
               - .* : 0개 이상의 문자열
               - [] : 문자 클래스, 해당 위치(범위)에 허용되는 문자의 집합
                       - 예를 들어 [A-Z]면 대문자 중 하나
               - + : 앞의 패턴이 최소 한번 이상은 나타나야 한다는 것을 의미

            */
            String pattern = ".*[a-zA-Z]+.*";

            return password.matches(pattern);
        } else { // 대문자 1개, 소문자 1개 포함
            //
            String pattern1 = ".*[a-z]+.*";
            String pattern2 = ".*[A-Z]+.*";

            // 둘 다 해당하면 반환
            return password.matches(pattern1) && password.matches(pattern2);
        }
    }
    /**
     * 비밀번호에 숫자 포함 여부
     * @param password
     * @return
     */
    // 해당 메소드를 사용하려면 매개변수로 String password 값을 꼭 넣어주어야 한다.
    default
    boolean numberCheck (String password){

        // [0-9]는 //d로 치환 가능, 따라서 ".*//d+.*"로 사용 가능
        return password.matches(".*[0-9]+.*");
    }

    /**
     * 비밀번호에 특수문자 포함 여부
     */
    // 해당 메소드를 사용하려면 매개변수로 String password 값을 꼭 넣어주어야 한다.
    default
    boolean specialCharsCheck (String password){
        String pattern = ".*[`~!@#$%^&*()-_+=]+.*";

        return password.matches(pattern);

    }
}