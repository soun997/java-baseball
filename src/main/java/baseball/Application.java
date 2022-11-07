package baseball;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.Randoms;

public class Application {

    /*
     * 컴퓨터가 세자리의 랜덤 숫자를 선택, 각 자리값을 리스트에 담아 반환
     */
    public static List<Integer> computersChoice() {

        List<Integer> computer = new ArrayList<>();

        while (computer.size() < 3){

            int randomNumber = Randoms.pickNumberInRange(1, 9);
            if (!computer.contains(randomNumber)){
                computer.add(randomNumber);
            }
        }

        return computer;
    }

    /*
     * 사용자가 세자리의 숫자를 입력, 각 자리값을 리스트에 담아 반환
     */
    public static List<Integer> usersChoice(){

        String input = Console.readLine();
        inputExceptionHandler(input);   // 입력값 예외처리

        List<Integer> user = new ArrayList<>();

        for (int i = 0; i < 3; i++){
            user.add(Character.getNumericValue(input.charAt(i)));
        }

        return user;
    }

    /*
     * 사용자 입력 예외를 처리함
     */
    public static void inputExceptionHandler(String input){
        // TODO: 사용자 입력 예외를 처리함
    }

    /*
     * 컴퓨터가 고른 숫자와 사용자가 입력한 숫자를 비교하여 결과를 반환
     */
    public static String getGameResult(List<Integer> computer, List<Integer> user){

        int ball = 0;
        int strike = 0;
        boolean isBall = false;

        for(int i = 0; i < 3; i++){

            // 볼 혹은 스트라이크일 경우
            if (computer.contains(user.get(i))){
                isBall = true;
                ball++;
            }

            // 스트라이크일 경우
            if (isBall && computer.get(i).equals(user.get(i))){
                ball--;
                strike++;
            }

            isBall = false;
        }

        String result = "";
        
        if (ball == 0 && strike == 0){
            result += "낫싱";
            return result;
        }

        if (ball > 0){
            result += ball + "볼 ";
        }

        if (strike > 0){
            result += strike + "스트라이크";
        }

        return result.trim();
    }

    /*
     * 게임 종료 후 숫자 야구 게임 재시작 여부 반환
     */
    public static boolean restartGame(){

        String input = Console.readLine();

        if (Integer.valueOf(input).equals(1)){
            return true;
        }
        else if (Integer.valueOf(input).equals(2)){
            return false;
        }
        else{
            throw new IllegalArgumentException("you must choose between 1 and 2");
        }
    }

    public static void main(String[] args) {

        List<Integer> computer = new ArrayList<>();
        List<String> results = new ArrayList<>();
        List<Integer> user = new ArrayList<>();
        boolean restart = false;

        computer = computersChoice();

        while(true){
            user = usersChoice();
            results.add(getGameResult(computer, user));

            // 3스트라이크라면 게임 종료
            if (getGameResult(computer, user).equals("3스트라이크")){
                restart = restartGame();    // 게임을 재시작할지 종료할지 여부 결정
            }

            // 게임을 재시작하기로 했다면 컴퓨터는 다시 랜덤 숫자를 선택
            if (restart){
                computer = computersChoice();
                restart = false;    // 초기화
            }
            else if (getGameResult(computer, user).equals("3스트라이크")){
                results.add("게임 종료");
                System.out.println(results);
                break;
            }
        }
    }
}
